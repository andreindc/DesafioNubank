package com.nubanck.application;

import com.nubanck.entity.InputAccount;
import com.nubanck.entity.InputTransaction;

import java.util.List;

/**
 * @desc Base business interface
 * @author Andreina Díaz andreinadc@gmail.com
 */
public interface BusinessRules {

    List<String> validateRule(InputAccount inputAccount, List<InputTransaction> inputTransactions) ;

    public String validateRule(InputAccount inputAccount);

    public String execute(String jsonInputAccount);

    public String execute(InputAccount inputAccount, String jsoninputTransaction);

}
