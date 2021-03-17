package com.nubanck.entity;

import java.util.List;

/**
 * @desc Entity that stores the data of the output account together with the rule violations.
 * @author Andreina Díaz andreinadc@gmail.com
 */
public final class OutputAccount {
    private final Account account;
    private final List<String> violations;

    public OutputAccount(Account account, List<String> violations) {
        this.account = account;
        this.violations = violations;
    }


    public List<String> getViolations() {
        return violations;
    }


}
