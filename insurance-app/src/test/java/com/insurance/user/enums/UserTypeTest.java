package com.insurance.user.enums;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTypeTest {
    
    @Test
    public void userTypeEnumSizeTest() {
        assertEquals(2, UserType.values().length);
    }
}
