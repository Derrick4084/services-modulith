package com.derocode.EcommApp.email.services;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-for-outlook", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-for-outlook", "Order successfully processed"),
    SHIPMENT_CONFIRMATION("shipment-for-outlook", "Order successfully shipped");

    @Getter
    private final String template;

    @Getter
    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
