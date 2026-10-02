package com.insurance.insurance.enums;

import org.junit.Assert;
import org.junit.Test;

public class InsuranceTypeTest {

    @Test
    public void insurenceEnumSizeTest() {
        Assert.assertEquals(2, InsuranceType.values().length);
    }
}
