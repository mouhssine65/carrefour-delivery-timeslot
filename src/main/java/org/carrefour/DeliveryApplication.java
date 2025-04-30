package org.carrefour;

import org.carrefour.delivery.domain.DeliveryMode;
import org.carrefour.delivery.repository.DeliveryRepository;
import org.carrefour.timeSlot.domain.Timeslot;
import org.carrefour.timeSlot.repository.TimeslotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class DeliveryApplication {

    public static void main( String[] args ) {
        SpringApplication.run( DeliveryApplication.class, args );
    }

    @Bean
    CommandLineRunner init( TimeslotRepository repository, DeliveryRepository deliveryRepository ) {
        return args -> {
            deliveryRepository.deleteAll();
            repository.deleteAll();
            repository.save( Timeslot.builder()
                                     .deliveryMode( DeliveryMode.DELIVERY )
                                     .date( LocalDate.now()
                                                     .plusDays( 1 ) )
                                     .startTime( LocalTime.of( 14, 0 ) )
                                     .endTime( LocalTime.of( 15, 0 ) )
                                     .capacity( 5 )
                                     .reserved( 0 )
                                     .build() );

            repository.save( Timeslot.builder()
                                     .deliveryMode( DeliveryMode.DRIVE )
                                     .date( LocalDate.now()
                                                     .plusDays( 2 ) )
                                     .startTime( LocalTime.of( 10, 0 ) )
                                     .endTime( LocalTime.of( 11, 0 ) )
                                     .capacity( 10 )
                                     .reserved( 0 )
                                     .build() );

            System.out.println( "✅ Timeslots initialized" );
        };
    }

}
