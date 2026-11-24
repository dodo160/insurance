package com.insurance.insurance.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReinsuranceTypeTest {

    @Test
    public void reinsurenceTypeEnumSizeTest() {
        assertEquals(2, ReinsuranceType.values().length);
    }
}
