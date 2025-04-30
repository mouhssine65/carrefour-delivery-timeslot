package org.carrefour.timeSlot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.timeSlot.dto.TimeslotDTO;
import org.carrefour.timeSlot.service.TimeslotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping( "/api/timeslot" )
@RequiredArgsConstructor
public class TimeslotController {

    private final TimeslotService timeSlotService;

    @Operation( summary = "Get available timeslots",
                description = "Retrieve a list of available timeslots for a given delivery mode and date." )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200",
                          description = "Successfully retrieved available timeslots",
                          content = @Content( mediaType = "application/json",
                                              schema = @Schema( implementation = TimeslotDTO.class ) ) ),
            @ApiResponse( responseCode = "400",
                          description = "Invalid request parameters",
                          content = @Content( mediaType = "application/json" ) ),
            @ApiResponse( responseCode = "500",
                          description = "Internal server error",
                          content = @Content( mediaType = "application/json" ) )
    } )
    @GetMapping( "/slots" )
    public List<TimeslotDTO> getAvailableTimeslots( @RequestParam DeliveryMode mode, @RequestParam String date ) {
        return timeSlotService.getAvailableTimeslots( mode, LocalDate.parse( date ) );
    }

}

