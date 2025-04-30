package org.carrefour.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.carrefour.timeSlot.dto.TimeslotDTO;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeliveryDTO {
    private Long id;
    private Long clientId;

    private TimeslotDTO timeslot;
}
