package com.derocode.EcommApp;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;


class ModularityTests {

    @Test
    void verifiesModularStructures() {
        ApplicationModules.of(EcommAppApplication.class).verify();
    }


}
