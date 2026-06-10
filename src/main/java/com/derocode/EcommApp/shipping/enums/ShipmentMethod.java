package com.derocode.EcommApp.shipping.enums;

import lombok.Getter;

public enum ShipmentMethod {
    STANDARD(5),
    EXPRESS(3),
    OVERNIGHT(1);

    @Getter
    private final Integer addedDays;

    ShipmentMethod(Integer addedDays) {
        this.addedDays = addedDays;
    }
}
