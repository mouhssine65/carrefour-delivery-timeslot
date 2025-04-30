package org.carrefour.delivery.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.carrefour.delivery.dto.DeliveryDTO;
import org.carrefour.timeSlot.domain.Timeslot;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private Long clientId;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "timeslot_id" )
    private Timeslot timeslot;

    public DeliveryDTO mapToDTO() {
        return DeliveryDTO.builder()
                .id( id )
                .clientId( clientId )
                .timeslot( timeslot.mapToDTO() )
                .build();
    }
}
