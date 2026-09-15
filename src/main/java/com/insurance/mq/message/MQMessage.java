package com.insurance.mq.message;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class MQMessage implements Serializable {

    private static final long serialVersionUID = 884881484709746537L;

    private UUID id;
    private String message;

    public MQMessage() {
    }

    public MQMessage(final UUID id, final String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MQMessage)) return false;
        MQMessage mqMessage = (MQMessage) o;
        return getId().equals(mqMessage.getId()) &&
                getMessage().equals(mqMessage.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessage());
    }

    @Override
    public String toString() {
        return "MQMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
