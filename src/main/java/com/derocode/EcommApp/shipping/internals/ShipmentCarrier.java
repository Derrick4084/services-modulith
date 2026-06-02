package com.derocode.EcommApp.shipping.internals;

public enum ShipmentCarrier {
    FEDEX,
    DHL,
    UPS,
    USPS,

    // E-commerce / logistics
    AMAZON_LOGISTICS,
    ON_TRAC,

    // Same-day / local delivery
    UBER_EATS,
    DOORDASH,
    POST_MATES,

    // Freight / large shipments
    XPO_LOGISTICS,
    OLD_DOMINION,
    ESTES,

    // Generic fallback
    OTHER
}
