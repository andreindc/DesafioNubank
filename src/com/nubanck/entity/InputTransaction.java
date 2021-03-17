package com.nubanck.entity;

/**
 * @desc Entity that stores the user's input transaction
 * @author Andreina Díaz andreinadc@gmail.com
 */
public final class InputTransaction {
    private final Transaction transaction;

    public InputTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }


    public boolean duplicate(Transaction t) {
       if(t.getMerchant().equals(this.transaction.getMerchant()) && t.getAmount().equals(this.transaction.getAmount()) && t.getTime().equals(this.transaction.getTime()))
        return true;
       else
        return false;
    }


}
