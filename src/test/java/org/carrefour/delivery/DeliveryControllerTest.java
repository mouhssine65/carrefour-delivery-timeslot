package org.carrefour.delivery;

import org.carrefour.delivery.controller.DeliveryController;
import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.delivery.dto.DeliveryRequest;
import org.carrefour.delivery.dto.DeliveryDTO;
import org.carrefour.delivery.service.DeliveryService;
import org.carrefour.delivery.service.DeliveryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DeliveryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DeliveryServiceImpl deliveryService;

    @InjectMocks
    private DeliveryController deliveryController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(deliveryController).build();
    }

    @Test
    void testGetModes() throws Exception {
        List<DeliveryMode> modes = Arrays.asList(DeliveryMode.DELIVERY, DeliveryMode.DELIVERY_ASAP);

        when(deliveryService.getAllModes()).thenReturn(modes);

        mockMvc.perform(get("/api/delivery/modes")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json("[\"DELIVERY\", \"DELIVERY_ASAP\"]"));
    }

    @Test
    void testReserveSlot() throws Exception {
        DeliveryDTO response = new DeliveryDTO();

        when(deliveryService.reserveSlot(any(DeliveryRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/delivery/slots/reserve")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"clientId\":1,\"mode\":\"DELIVERY_ASAP\",\"date\":\"2025-04-29\",\"startTime\":\"10:00\",\"endTime\":\"12:00\"}")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json("{}"));
    }
}
