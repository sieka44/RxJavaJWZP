package com.virtuslab.rx_intro.task;

import java.math.BigDecimal;

public class TransactionAndConfirmation {

    final String transactionId;
    final boolean isConfirmed;
    final BigDecimal value;

    public TransactionAndConfirmation(String transactionId, boolean isConfirmed, BigDecimal value) {
        this.transactionId = transactionId;
        this.isConfirmed = isConfirmed;
        this.value = value;
    }
}
