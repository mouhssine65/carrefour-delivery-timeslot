package org.carrefour.timeSlot.dto;

import org.carrefour.delivery.domain.DeliveryMode;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeslotDTO(
        Long id,
        DeliveryMode deliveryMode,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        int capacity,
        int reserved
) {}

