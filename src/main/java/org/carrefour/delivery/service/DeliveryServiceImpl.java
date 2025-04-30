package org.carrefour.delivery.service;


import lombok.RequiredArgsConstructor;
import org.carrefour.delivery.domain.Delivery;
import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.delivery.dto.DeliveryDTO;
import org.carrefour.delivery.dto.DeliveryRequest;
import org.carrefour.delivery.repository.DeliveryRepository;
import org.carrefour.timeSlot.domain.Timeslot;
import org.carrefour.timeSlot.repository.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final TimeslotRepository timeslotRepository;
    private final DeliveryRepository deliveryRepository;

    @Override
    public DeliveryDTO reserveSlot( DeliveryRequest request ) {
        List<Timeslot> timeslots = timeslotRepository.findByDeliveryModeAndDate(
                request.mode(), request.date() );

        Timeslot slot = timeslots.stream()
                                 .filter( t -> t.getStartTime()
                                                .equals( request.startTime() ) &&
                                         t.getEndTime()
                                          .equals( request.endTime() ) )
                                 .findFirst()
                                 .orElseThrow( () -> new RuntimeException( "Matching timeslot not found" ) );

        if ( slot.getReserved() >= slot.getCapacity() ) {
            throw new RuntimeException( "Slot full" );
        }

        slot.setReserved( slot.getReserved() + 1 );
        timeslotRepository.save( slot );

        Delivery reservation = Delivery.builder()
                                       .clientId( request.clientId() )
                                       .timeslot( slot )
                                       .build();

        return deliveryRepository.save( reservation )
                                 .mapToDTO();
    }

    @Override
    public List<DeliveryMode> getAllModes() {
        return List.of( DeliveryMode.values() );
    }
}

