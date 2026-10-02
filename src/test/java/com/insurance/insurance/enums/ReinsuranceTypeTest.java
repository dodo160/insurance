package com.insurance.insurance.enums;

import org.junit.Assert;
import org.junit.Test;

public class ReinsuranceTypeTest {

    @Test
    public void reinsurenceTypeEnumSizeTest() {
        Assert.assertEquals(2, ReinsuranceType.values().length);
    }
}
