package org.carrefour.timeSlot;


import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.timeSlot.controller.TimeslotController;
import org.carrefour.timeSlot.dto.TimeslotDTO;
import org.carrefour.timeSlot.service.TimeslotService;
import org.carrefour.timeSlot.service.TimeslotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith( MockitoExtension.class )
public class TimeslotControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TimeslotServiceImpl timeslotService;

    @InjectMocks
    private TimeslotController timeslotController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup( timeslotController )
                                 .build();
    }

    @Test
    void testGetAvailableTimeslots_Success() throws Exception {
        // Mock data
        TimeslotDTO timeslotDTO = new TimeslotDTO( 1L, DeliveryMode.DELIVERY, LocalDate.now(), LocalTime.MIDNIGHT, LocalTime.NOON, 5, 2 );
        when( timeslotService.getAvailableTimeslots( DeliveryMode.DELIVERY, LocalDate.parse( "2023-12-01" ) ) ).thenReturn( List.of( timeslotDTO ) );

        // Perform GET request
        mockMvc.perform( get( "/api/timeslot/slots" ).param( "mode", "DELIVERY" )
                                                     .param( "date", "2023-12-01" ) )
               .andExpect( status().isOk() );
    }

    @Test
    void testGetAvailableTimeslots_InvalidRequest() throws Exception {
        // Perform GET request with invalid parameters
        mockMvc.perform( get( "/api/timeslot/slots" ).param( "mode", "INVALID_MODE" )
                                                     .param( "date", "invalid-date" ) )
               .andExpect( status().isBadRequest() );
    }

}
