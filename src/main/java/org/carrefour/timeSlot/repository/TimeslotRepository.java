package org.carrefour.timeSlot.repository;

import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.timeSlot.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    List<Timeslot> findByDeliveryModeAndDate( DeliveryMode mode, LocalDate date );
}
