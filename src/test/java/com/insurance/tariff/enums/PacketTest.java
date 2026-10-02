package com.insurance.tariff.enums;

import org.junit.Assert;
import org.junit.Test;

public class PacketTest {

    @Test
    public void packetEnumSizeTest() {
        Assert.assertEquals(3, Packet.values().length);
    }
}
