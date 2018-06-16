package com.virtuslab.rx_intro.task;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.function.Supplier;

/**
 * ConfirmedTransactionSummarizer is responsible for calculation of total confirmed transactions value.
 * HINT:
 * - Use zip operator to match transactions with confirmations. They will appear in order
 * - Filter only confirmed
 * - Aggregate value of confirmed transactions using reduce operator
 * <p>
 * HINT2:
 * - add error handling which will wrap an error into SummarizationException
 */
class ConfirmedTransactionSummarizer {

    private final static Logger LOG = LoggerFactory.getLogger(ConfirmedTransactionSummarizer.class);

    private final Supplier<Observable<Transaction>> transactions;
    private final Supplier<Observable<Confirmation>> confirmations;

    ConfirmedTransactionSummarizer(Supplier<Observable<Transaction>> transactions,
                                   Supplier<Observable<Confirmation>> confirmations) {
        this.transactions = transactions;
        this.confirmations = confirmations;
    }

    Single<BigDecimal> summarizeConfirmedTransactions() {
        return Observable.zip(transactions.get(), confirmations.get(), (t1, t2) ->
                new TransactionAndConfirmation(t1.transactionId, t2.isConfirmed, t1.value))
                .filter(obj -> obj.isConfirmed)
                .reduce(new BigDecimal(0), (i1, i2) -> i1.add(i2.value))
                .onErrorResumeNext(Single.error(new SummarizationException("Booom")));
    }

    static class SummarizationException extends RuntimeException {

        public SummarizationException(String message) {
            super(message);
        }
    }
}
