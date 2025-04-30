package org.carrefour.delivery.service;

import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.delivery.dto.DeliveryDTO;
import org.carrefour.delivery.dto.DeliveryRequest;

import java.util.List;

public interface DeliveryService {
    DeliveryDTO reserveSlot( DeliveryRequest request );
    List<DeliveryMode> getAllModes();
}
