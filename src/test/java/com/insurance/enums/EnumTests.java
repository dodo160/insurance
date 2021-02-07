package com.insurance.enums;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTests {

    @Test
    public void userTypeEnumSizeTest(){
        Assert.assertTrue(UserType.values().length == 2);
    }

    @Test
    public void reinsurenceTypeEnumSizeTest(){
        Assert.assertTrue(ReinsuranceType.values().length == 2);
    }

    @Test
    public void packetEnumSizeTest(){
        Assert.assertTrue(Packet.values().length == 3);
    }

    @Test
    public void insurenceEnumSizeTest(){
        Assert.assertTrue(InsuranceType.values().length == 2);
    }
}
