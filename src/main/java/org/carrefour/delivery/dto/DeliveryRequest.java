package org.carrefour.delivery.dto;

import org.carrefour.delivery.domain.DeliveryMode;

import java.time.LocalDate;
import java.time.LocalTime;

public record DeliveryRequest(
        Long clientId,
        DeliveryMode mode,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime) {
}


