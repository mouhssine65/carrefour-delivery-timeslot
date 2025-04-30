package org.carrefour.timeSlot.service;

import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.timeSlot.dto.TimeslotDTO;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotService {


     List<TimeslotDTO> getAvailableTimeslots( DeliveryMode mode, LocalDate date ) ;

}

