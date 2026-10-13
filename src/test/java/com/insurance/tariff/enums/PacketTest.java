package com.insurance.tariff.enums;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacketTest {

    @Test
    public void packetEnumSizeTest() {
        assertEquals(3, Packet.values().length);
    }
}
