package com.paige.service.apigateway.Filter;

import java.util.concurrent.atomic.AtomicLong;

public class TransactionId {

    static class Unique {
        private AtomicLong c = new AtomicLong(System.currentTimeMillis());

        public void increment() {
            c.getAndIncrement();
        }

        public long value() {
            return c.get();
        }
    }
}
