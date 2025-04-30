package org.carrefour.timeSlot;


import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.timeSlot.domain.Timeslot;
import org.carrefour.timeSlot.dto.TimeslotDTO;
import org.carrefour.timeSlot.repository.TimeslotRepository;
import org.carrefour.timeSlot.service.TimeslotService;
import org.carrefour.timeSlot.service.TimeslotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class)
class TimeslotServiceTest {

    @Mock
    private TimeslotRepository repository;

    @InjectMocks
    private TimeslotServiceImpl service;

    @Test
    void testGetAvailableTimeslots() {
        LocalDate date = LocalDate.now()
                                  .plusDays( 1 );
        when( repository.findByDeliveryModeAndDate( DeliveryMode.DELIVERY, date ) ).thenReturn( List.of( Timeslot.builder()
                                                                                                                 .date( date )
                                                                                                                 .capacity( 5 )
                                                                                                                 .reserved( 0 )
                                                                                                                 .build() ) );

        List<TimeslotDTO> timeslots = service.getAvailableTimeslots( DeliveryMode.DELIVERY, date );

        assertEquals( 1, timeslots.size() );
        assertEquals( date, timeslots.getFirst()
                                     .date() );
    }
}
