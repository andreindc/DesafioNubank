package com.nubanck.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nubanck.entity.InputAccount;
import com.nubanck.entity.InputTransaction;
import com.nubanck.entity.OutputAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc Class containing the business rules required to create an account
 * @author Andreina Díaz andreinadc@gmail.com
 */
public class CreateAccount implements BusinessRules{
    private InputAccount inputAccount;

    /**
     * @desc Class constructor
     */
    public CreateAccount() {
        this.inputAccount = null;
    }

    /**
     * @desc Check rule: Once created, the account should not be updated or recreated
     * @param InputAccount inputAccount - input account
     * @return String - Rule violation
     */
    public static String validateAccountInicialized(InputAccount inputAccount){
        String violation = null;
        if(inputAccount != null){
            violation = new String("account-already-initialized");
        }
        return violation;
    }

    /**
     * @desc Validate the set of business rules to Create Account, saving each of the generated violations
     * @param InputAccount inputAccount - input account
     * @return String - Rule violation
     */
    public String validateRule(InputAccount inputAccount){
          return validateAccountInicialized(inputAccount);
    }

    /**
     * @desc Convert the input json to an inputAccount, validate the rule, and generate an output json for the user
     * @param String jsonInputAccount - json of input account
     * @return String - json of output Account
     */
    public String execute(String jsonInputAccount){
        Gson gsonOuput = new GsonBuilder().setPrettyPrinting().create();
        Gson gsonImput = new Gson();
        OutputAccount outputAccount =null;
        List<String> violations = new ArrayList<String>();
        String violation = validateRule(this.inputAccount);

        if(violation == null) {
              this.inputAccount = gsonImput.fromJson(jsonInputAccount, InputAccount.class);
        }
        if(violation!=null) { violations.add(violation); }
        outputAccount = new OutputAccount(this.inputAccount.getAccount(), violations);

        return  gsonOuput.toJson(outputAccount);
    }

    @Override
    public String execute(InputAccount inputAccount, String jsoninputTransaction) {
        return null;
    }

    @Override
    public List<String> validateRule(InputAccount inputAccount, List<InputTransaction> inputTransactions) {
        return null;
    }

    public InputAccount getInputAccount() {
        return inputAccount;
    }
}
