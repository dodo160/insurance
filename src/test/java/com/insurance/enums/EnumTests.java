package com.insurance.enums;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTests {

    @Test
    public void userTypeEnumSizeTest(){
        Assert.assertEquals(2,UserType.values().length);
    }

    @Test
    public void reinsurenceTypeEnumSizeTest(){
        Assert.assertEquals(2,ReinsuranceType.values().length);
    }

    @Test
    public void packetEnumSizeTest(){
        Assert.assertEquals(3, Packet.values().length);
    }

    @Test
    public void insurenceEnumSizeTest(){
        Assert.assertEquals(2, InsuranceType.values().length);
    }
}
