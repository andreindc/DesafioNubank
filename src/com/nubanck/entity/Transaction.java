package com.nubanck.entity;

import java.util.Date;

/**
 * @desc base entity to store an transaction
 * @author Andreina Díaz andreinadc@gmail.com
 */
public final class Transaction {
    private final String merchant;
    private final Long amount;
    private final Date time;

    public Transaction(String merchant, Long amount, Date time) {
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
    }

    public String getMerchant() {
        return merchant;
    }

    public Long getAmount() {
        return amount;
    }

    public Date getTime() {
        return time;
    }

}
