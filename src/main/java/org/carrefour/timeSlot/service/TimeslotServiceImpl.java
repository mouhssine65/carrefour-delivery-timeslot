package org.carrefour.timeSlot.service;


import lombok.RequiredArgsConstructor;
import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.timeSlot.domain.Timeslot;
import org.carrefour.timeSlot.dto.TimeslotDTO;
import org.carrefour.timeSlot.repository.TimeslotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;

    @Override
    public List<TimeslotDTO> getAvailableTimeslots( DeliveryMode mode, LocalDate date ) {
        return timeslotRepository.findByDeliveryModeAndDate( mode, date )
                                 .stream()
                                 .filter( t -> t.getReserved() < t.getCapacity() )
                                 .map( Timeslot::mapToDTO )
                                 .toList();
    }

}

