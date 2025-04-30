package org.carrefour.timeSlot.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.timeSlot.dto.TimeslotDTO;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timeslot{
        @Id
        @GeneratedValue( strategy = GenerationType.IDENTITY )
        private Long         id;
        @Enumerated( EnumType.STRING )
        private DeliveryMode deliveryMode;
        private LocalDate    date;
        private LocalTime    startTime;
        private LocalTime    endTime;
        private int          capacity;
        private int reserved;

        public TimeslotDTO mapToDTO() {
                return new TimeslotDTO(
                        id,
                        deliveryMode,
                        date,
                        startTime,
                        endTime,
                        capacity,
                        reserved
                );
        }
}
