package org.carrefour.delivery.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.carrefour.delivery.service.DeliveryService;
import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.delivery.dto.DeliveryRequest;
import org.carrefour.delivery.dto.DeliveryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation( summary = "Get all delivery modes", description = "Retrieve a list of all available delivery modes.")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Successfully retrieved delivery modes",
                          content = @Content( mediaType = "application/json",
                                              schema = @Schema(implementation = DeliveryMode.class)))
    })
    @GetMapping("/modes")
    public List<DeliveryMode> getModes() {
        return deliveryService.getAllModes();
    }

    @Operation(summary = "Reserve a delivery slot", description = "Reserve a delivery slot based on the provided request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully reserved the delivery slot",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = DeliveryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                         content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Matching timeslot not found",
                         content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                         content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/slots/reserve")
    public DeliveryDTO reserveSlot( @RequestBody DeliveryRequest request ) {
        return deliveryService.reserveSlot(request);
    }
}

