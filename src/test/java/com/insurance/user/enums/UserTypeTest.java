package com.insurance.user.enums;

import org.junit.Assert;
import org.junit.Test;

public class UserTypeTest {
    
    @Test
    public void userTypeEnumSizeTest() {
        Assert.assertEquals(2, UserType.values().length);
    }
}
