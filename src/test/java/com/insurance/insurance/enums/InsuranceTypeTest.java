package com.insurance.insurance.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsuranceTypeTest {

    @Test
    public void insurenceEnumSizeTest() {
        assertEquals(2, InsuranceType.values().length);
    }
}
