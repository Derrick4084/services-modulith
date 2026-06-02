package com.derocode.EcommApp;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTests {

    @Test
    void writeDocumentationSnippets() {

        var modules = ApplicationModules.of(EcommAppApplication.class).verify();
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

    @Test
    void verifiesModularStructures() {
        ApplicationModules.of(EcommAppApplication.class).verify();
    }


}
