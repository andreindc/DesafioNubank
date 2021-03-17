package com.nubanck.entity;

/**
 * @desc base entity to store an account
 * @author Andreina Díaz andreinadc@gmail.com
 */
public final class Account {
    private final Boolean activeCard;
    private final Long availableLimit;

    public Account(Boolean activeCard, Long availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public long getAvailableLimit() {
        return availableLimit;
    }


}
