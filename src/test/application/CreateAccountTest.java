
package test.application;

import com.google.gson.Gson;

import com.nubanck.application.CreateAccount;
import com.nubanck.entity.InputAccount;
import org.junit.Assert;
import org.junit.Test;


public class CreateAccountTest {

    @Test
    public void account_already_initialized_when_account_recreated() {
        InputAccount inputAccount = null;
        Gson gsonImput = new Gson();
        String jsonInputAccount ="{\"account\": {\"active-card\": true, \"available-limit\": 100}}";
        inputAccount = gsonImput.fromJson(jsonInputAccount, InputAccount.class);
        Assert.assertEquals("account-already-initialized", CreateAccount.validateAccountInicialized(inputAccount));
        Assert.assertEquals(null, CreateAccount.validateAccountInicialized(null));
    }

}