package com.nubanck.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nubanck.entity.Account;
import com.nubanck.entity.InputAccount;
import com.nubanck.entity.InputTransaction;
import com.nubanck.entity.OutputAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @desc Class containing the business rules required to authorize a transaction
 * @author Andreina Díaz andreinadc@gmail.com
 */
public class AuthorizeTransaction implements BusinessRules{
    private final List<InputTransaction> inputTransactions;

    /**
     * @desc Class constructor
     */
    public AuthorizeTransaction() {
        this.inputTransactions = new ArrayList <InputTransaction>();
    }


    /**
     * @desc Check rule: No transaction should be accepted without a properly initialized account
     * @param InputAccount inputAccount - input account
     * @return String - Rule violation
     */
    public static final String validateAccountNotInitialized(InputAccount inputAccount){
        String violation=null;
        if(inputAccount == null){
            violation = new String("account-not-initialized");
        }
        return violation;
    }

    /**
     * @desc Check rule: No transaction should be accepted when the card is not active
     * @param InputAccount inputAccount - input account
     * @return String - Rule violation
     */
    public static final String validateCardNotActive(InputAccount inputAccount){
        String violation=null;
        if(inputAccount != null){
            if (!inputAccount.getAccount().isActiveCard()) {
                violation = new String("card-not-active");
            }
        }
        return violation;
    }

    /**
     * @desc Check rule: The transaction amount should not exceed available limit
     * @param InputAccount inputAccount - input account
     * @param InputTransaction inputTransaction- input transaction
     * @return String - Rule violation
     */
    public static final String validateInsufficientLimit(InputAccount inputAccount, InputTransaction inputTransaction){
        String violation=null;
        if (inputAccount.getAccount().getAvailableLimit() < inputTransaction.getTransaction().getAmount()) {
            violation = new String("insufficient-limit");
        }
        return violation;
    }

    /**
     * @desc Check rule: There should not be more than 3 transactions on a 2 minute interval
     * @param List<InputTransaction> inputTransactions - Transaction list
     * @return String - Rule violation
     */
    public static final String validateHighFrequencySmallInterval(List<InputTransaction> inputTransactions){
        String violation=null;
        int minutes =  -1;
        if(inputTransactions.size()>=3){
            Date dateInitial =inputTransactions.get(inputTransactions.size()-3).getTransaction().getTime();
            Date dateFinal =inputTransactions.get(inputTransactions.size()-1).getTransaction().getTime();
            minutes = (int) ((dateFinal.getTime() - dateInitial.getTime()))/60000;
        }

        if(minutes>=0 && minutes<=2){
            violation = new String("high-frequency-small-interval");
        }
        return violation;
    }

    /**
     * @desc Check rule: There should not be more than 1 similar transactions (same amount and merchant) in a 2 minutes interval
     * @param List<InputTransaction> inputTransactions - Transaction list
     * @return String - Rule violation
     */
    public static final String validateDoubledTransaction(List<InputTransaction> inputTransactions) {
        String violation = null;
        if (inputTransactions.size() >= 2) {
            Date dateInitial = inputTransactions.get(inputTransactions.size() - 2).getTransaction().getTime();
            Date dateFinal = inputTransactions.get(inputTransactions.size() - 1).getTransaction().getTime();
            int minutes = (int) ((dateFinal.getTime() - dateInitial.getTime())) / 60000;
            if (minutes < 0) {
                System.out.println("A Transaction with a date after the one entered is already registered");
            }

            if ((minutes >= 0 && minutes <= 2)
                    && inputTransactions.get(inputTransactions.size() - 1).duplicate(inputTransactions.get(inputTransactions.size() - 2).getTransaction())) {
                violation = new String("doubled-transaction");
            }
        }
        return violation;
    }

    /**
     * @desc Validate the set of business rules to Authorize a transaction, saving each of the generated violations in a list of violations
     * @param InputAccount inputAccount- input account
     * @param List<InputTransaction> inputTransactions- Transaction list
     * @return List<String> - lis violations
     */
    @Override
    public List<String> validateRule(InputAccount inputAccount,List<InputTransaction> inputTransactions){
        List<String> violations = new ArrayList<String>();
        String violation = validateAccountNotInitialized(inputAccount);

        if(violation == null) {
            violation= validateAccountNotInitialized(inputAccount);
            if(violation!=null) { violations.add(violation); }
            violation = validateCardNotActive(inputAccount);
            if(violation!=null) { violations.add(violation); }
            violation = validateInsufficientLimit(inputAccount,inputTransactions.get(inputTransactions.size()-1));
            if(violation!=null) { violations.add(violation); }
            violation= validateHighFrequencySmallInterval(inputTransactions);
            if(violation!=null) { violations.add(violation); }
            violation= validateDoubledTransaction(inputTransactions);
            if(violation!=null) { violations.add(violation); }
        }
        else{
            violations.add(violation);
        }
      return violations;
    }

    /**
     * @desc Convert the input json to an inputTransaction, validate the rules, and generate an output json for the user
     * @param InputAccount inputAccount- input account
     * @param String jsoninputTransaction - json of input transaction
     * @return String - json of output Account
     */
    @Override
    public String execute(InputAccount inputAccount, String jsoninputTransaction) {
        Gson gsonOuput = new GsonBuilder().setPrettyPrinting().create();
        Gson gsonImput = new Gson();
        OutputAccount outputAccount= null;
        InputTransaction inputTransaction= null;

        inputTransaction = gsonImput.fromJson(jsoninputTransaction, InputTransaction.class);

        this.inputTransactions.add(inputTransaction);
        List<String> violations = validateRule(inputAccount, this.inputTransactions);

        if(inputAccount != null){
            outputAccount = new OutputAccount(inputAccount.getAccount(), violations);
        }
        else{
            outputAccount = new OutputAccount(new Account(null,null), violations);
        }

        return  gsonOuput.toJson(outputAccount);
    }

    @Override
    public String validateRule(InputAccount inputAccount) {
        return null;
    }

    @Override
    public String execute(String jsonInputAccount) {
        return null;
    }

}
