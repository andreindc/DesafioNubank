package test.application;

import com.google.gson.Gson;
import com.nubanck.application.AuthorizeTransaction;
import com.nubanck.entity.InputAccount;
import com.nubanck.entity.InputTransaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestAuthorizeTransaction {

    @Test
    public void account_not_initialized_when_account_is_null(){
        InputAccount inputAccount = null;
        Gson gsonImput = new Gson();
        String jsonInputAccount ="{\"account\": {\"active-card\": true, \"available-limit\": 100}}";
        jsonInputAccount = jsonInputAccount.replace("active-card", "activeCard")
                .replace("available-limit", "availableLimit");
        inputAccount = gsonImput.fromJson(jsonInputAccount, InputAccount.class);
        Assert.assertEquals("account-not-initialized", AuthorizeTransaction.validateAccountNotInitialized(null));
        Assert.assertEquals(null, AuthorizeTransaction.validateAccountNotInitialized(inputAccount));
    }

    @Test
    public void card_not_active_when_account_is_not_active(){
        InputAccount inputAccount = null;
        Gson gsonImput = new Gson();
        String jsonInputAccount ="{\"account\": {\"active-card\": false, \"available-limit\": 100}}";
        jsonInputAccount = jsonInputAccount.replace("active-card", "activeCard")
                .replace("available-limit", "availableLimit");
        inputAccount = gsonImput.fromJson(jsonInputAccount, InputAccount.class);
        Assert.assertEquals("card-not-active", AuthorizeTransaction.validateCardNotActive(inputAccount));
    }

    @Test
    public void insufficient_limit_when_amount_transaction_greater_available_limit_account(){
        InputAccount inputAccount = null;
        Gson gsonImput = new Gson();
        String jsonInputAccount ="{\"account\": {\"active-card\": true, \"available-limit\": 100}}";
        jsonInputAccount =  jsonInputAccount.replace("active-card", "activeCard")
                .replace("available-limit", "availableLimit");
        inputAccount = gsonImput.fromJson(jsonInputAccount, InputAccount.class);

        List<InputTransaction> inputTransactions = new ArrayList<InputTransaction>();
        String jsonInputTransaction = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 120, \"time\":\"2019-02-13T10:00:00.000Z\"}}";
        InputTransaction inputTransaction= gsonImput.fromJson(jsonInputTransaction, InputTransaction.class);
        inputTransactions.add(inputTransaction);
        Assert.assertEquals("insufficient-limit", AuthorizeTransaction.validateInsufficientLimit(inputAccount,inputTransactions.get(inputTransactions.size()-1)));
    }

    @Test
    public void high_frequency_small_interval_when_3_transaction_occurred_in_2_minutes(){
        InputAccount inputAccount = null;
        Gson gsonImput = new Gson();
        String jsonInputAccount ="{\"account\": {\"active-card\": true, \"available-limit\": 100}}";
        jsonInputAccount =  jsonInputAccount.replace("active-card", "activeCard")
                .replace("available-limit", "availableLimit");
        inputAccount = gsonImput.fromJson(jsonInputAccount, InputAccount.class);

        List<InputTransaction> inputTransactions = new ArrayList<InputTransaction>();
        String jsonInputTransaction = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 120, \"time\":\"2019-02-13T10:00:00.000Z\"}}";
        InputTransaction inputTransaction= gsonImput.fromJson(jsonInputTransaction, InputTransaction.class);
        inputTransactions.add(inputTransaction);
        Assert.assertEquals(null, AuthorizeTransaction.validateHighFrequencySmallInterval(inputTransactions));

        jsonInputTransaction= "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 390, \"time\":\"2019-02-13T10:01:00.000Z\"}}";
        inputTransaction= gsonImput.fromJson(jsonInputTransaction, InputTransaction.class);
        inputTransactions.add(inputTransaction);
        Assert.assertEquals(null, AuthorizeTransaction.validateHighFrequencySmallInterval(inputTransactions));

        jsonInputTransaction= "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 390, \"time\":\"2019-02-13T10:01:10.000Z\"}}";
        inputTransaction= gsonImput.fromJson(jsonInputTransaction, InputTransaction.class);
        inputTransactions.add(inputTransaction);
        Assert.assertEquals("high-frequency-small-interval", AuthorizeTransaction.validateHighFrequencySmallInterval(inputTransactions));
    }

    @Test
    public void doubled_transaction_when_1_similar_transactions_in_2_minutes(){
        InputAccount inputAccount = null;
        Gson gsonImput = new Gson();
        String jsonInputAccount ="{\"account\": {\"active-card\": true, \"available-limit\": 100}}";
        jsonInputAccount =  jsonInputAccount.replace("active-card", "activeCard")
                .replace("available-limit", "availableLimit");
        inputAccount = gsonImput.fromJson(jsonInputAccount, InputAccount.class);

        List<InputTransaction> inputTransactions = new ArrayList<InputTransaction>();
        String jsonInputTransaction = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 120, \"time\":\"2019-02-13T10:00:00.000Z\"}}";
        InputTransaction inputTransaction= gsonImput.fromJson(jsonInputTransaction, InputTransaction.class);
        inputTransactions.add(inputTransaction);
        Assert.assertEquals(null, AuthorizeTransaction.validateHighFrequencySmallInterval(inputTransactions));

        jsonInputTransaction = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 120, \"time\":\"2019-02-13T10:00:00.000Z\"}}";
        inputTransaction= gsonImput.fromJson(jsonInputTransaction, InputTransaction.class);
        inputTransactions.add(inputTransaction);
        Assert.assertEquals("doubled-transaction", AuthorizeTransaction.validateDoubledTransaction(inputTransactions));
    }

}
