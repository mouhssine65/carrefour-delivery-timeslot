package org.carrefour.delivery;

import org.carrefour.delivery.domain.Delivery;
import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.delivery.dto.DeliveryDTO;
import org.carrefour.delivery.dto.DeliveryRequest;
import org.carrefour.delivery.repository.DeliveryRepository;
import org.carrefour.delivery.service.DeliveryService;
import org.carrefour.delivery.service.DeliveryServiceImpl;
import org.carrefour.timeSlot.domain.Timeslot;
import org.carrefour.timeSlot.repository.TimeslotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class DeliveryServiceTest {

    @Mock
    private TimeslotRepository timeslotRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Test
    void testReserveSlot_Success() {
        DeliveryRequest request = new DeliveryRequest(
                123L,
                DeliveryMode.DELIVERY,
                LocalDate.now()
                         .plusDays( 1 ),
                LocalTime.of( 10, 0 ),
                LocalTime.of( 11, 0 )
        );

        Timeslot timeslot = Timeslot.builder()
                                    .date( request.date() )
                                    .startTime( request.startTime() )
                                    .endTime( request.endTime() )
                                    .capacity( 5 )
                                    .reserved( 2 )
                                    .build();

        Delivery delivery = Delivery.builder()
                                    .clientId( request.clientId() )
                                    .timeslot( timeslot )
                                    .build();

        when( timeslotRepository.findByDeliveryModeAndDate( request.mode(), request.date() ) )
                .thenReturn( List.of( timeslot ) );
        when( deliveryRepository.save( any( Delivery.class ) ) ).thenReturn( delivery );

        DeliveryDTO result = deliveryService.reserveSlot( request );

        assertEquals( request.clientId(), result.getClientId() );
        assertEquals( request.date(), result.getTimeslot()
                                            .date() );
        assertEquals( request.startTime(), result.getTimeslot()
                                                 .startTime() );
        assertEquals( request.endTime(), result.getTimeslot()
                                               .endTime() );
        verify( timeslotRepository ).save( timeslot );
    }

    @Test
    void testReserveSlot_SlotFull() {
        DeliveryRequest request = new DeliveryRequest(
                123L,
                DeliveryMode.DELIVERY_TODAY,
                LocalDate.now()
                         .plusDays( 1 ),
                LocalTime.of( 10, 0 ),
                LocalTime.of( 11, 0 )
        );

        Timeslot timeslot = Timeslot.builder()
                                    .date( request.date() )
                                    .startTime( request.startTime() )
                                    .endTime( request.endTime() )
                                    .capacity( 5 )
                                    .reserved( 5 )
                                    .build();

        when( timeslotRepository.findByDeliveryModeAndDate( request.mode(), request.date() ) )
                .thenReturn( List.of( timeslot ) );

        RuntimeException exception = assertThrows( RuntimeException.class, () -> deliveryService.reserveSlot( request ) );
        assertEquals( "Slot full", exception.getMessage() );
        verify( timeslotRepository, never() ).save( any() );
    }

    @Test
    void testReserveSlot_NoMatchingSlot() {
        DeliveryRequest request = new DeliveryRequest(
                123L,
                DeliveryMode.DELIVERY_ASAP,
                LocalDate.now()
                         .plusDays( 1 ),
                LocalTime.of( 10, 0 ),
                LocalTime.of( 11, 0 )
        );

        when( timeslotRepository.findByDeliveryModeAndDate( request.mode(), request.date() ) )
                .thenReturn( List.of() );

        RuntimeException exception = assertThrows( RuntimeException.class, () -> deliveryService.reserveSlot( request ) );
        assertEquals( "Matching timeslot not found", exception.getMessage() );
        verify( timeslotRepository, never() ).save( any() );
    }
}
