package com.sun.booking.bookings;

import com.sun.booking.tours.Tour;
import com.sun.booking.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
