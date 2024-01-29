package com.insurance.xml.xmladapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    public LocalDateTime unmarshal(final String v) {
        return LocalDateTime.parse(v);
    }

    public String marshal(final LocalDateTime v) {
        return v.toString();
    }
}
