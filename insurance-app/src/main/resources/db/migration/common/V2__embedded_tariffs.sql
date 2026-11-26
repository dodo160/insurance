ALTER TABLE INSURANCE
    ADD COLUMN tariff_type VARCHAR(255) AFTER price,
    ADD COLUMN tariff_packet VARCHAR(255) AFTER tariff_type,
    ADD COLUMN tariff_price DECIMAL(10,2) AFTER tariff_packet;

UPDATE INSURANCE i
JOIN TARIFF t ON t.id = i.tariff_id
SET i.tariff_type = t.insuranceType,
    i.tariff_packet = t.packet,
    i.tariff_price = t.price
WHERE EXISTS (SELECT 1 FROM TARIFF);

ALTER TABLE INSURANCE
    MODIFY tariff_type VARCHAR(255) NOT NULL,
    MODIFY tariff_packet VARCHAR(255) NOT NULL,
    MODIFY tariff_price DECIMAL(10,2) NOT NULL;

ALTER TABLE INSURANCE DROP FOREIGN KEY `INSURANCE_ibfk_1`;

ALTER TABLE INSURANCE
    MODIFY COLUMN tariff_id BIGINT NOT NULL AFTER price;

DROP TABLE TARIFF;
