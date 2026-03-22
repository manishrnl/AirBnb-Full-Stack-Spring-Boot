
package org.example.tutorial_2_homework.manish_airbnb_clone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    // --- Basic Info ---
    @Column(nullable = false)
    private String title; // Short catchy name for the room

    @Column(columnDefinition = "TEXT")
    private String description; // Long detailed description

    @Column(nullable = false)
    private String type; // e.g., "Deluxe", "Penthouse", "Studio"

    @Column(nullable = false)
    private String city;

    // --- Capacity & Layout ---
    @Column(nullable = false)
    private Integer capacity; // Max guests

    @Column(nullable = false)
    private Integer totalCount; // Total rooms of this type available in the hotel

    private Integer bedrooms;
    private Integer beds;
    private Integer bathrooms;

    // --- Pricing ---
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomPhotos> photos;

    @ElementCollection
    @CollectionTable(name = "room_amenities", joinColumns = @JoinColumn(name = "room_id"))
       private List<String> amenities;

    // --- Ratings & Metadata ---
    private Double rating;

    private Integer reviewCount;

    @Column(columnDefinition = "TEXT")
    private String houseRules;

    // --- Timestamps ---
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}