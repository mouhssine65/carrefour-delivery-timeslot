package org.carrefour.delivery.repository;

import org.carrefour.delivery.domain.Delivery;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    // Custom query methods can be defined here if needed
    // For example, find deliverys by delivery mode or time slot
    // List<Delivery> findByDeliveryMode(DeliveryMode deliveryMode);
    // List<Delivery> findByTimeslot(Timeslot timeSlot);
}
