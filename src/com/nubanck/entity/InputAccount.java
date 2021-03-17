package com.nubanck.entity;

/**
 * @desc Entity that stores the user's input account
 * @author Andreina Díaz andreinadc@gmail.com
 */
public final class InputAccount {
    Account account;

    public InputAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

}
