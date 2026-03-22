package org.example.tutorial_2_homework.manish_airbnb_clone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "city","active"}))
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;


    @Column(columnDefinition = "TEXT") // Use TEXT if the description is long
    private String description;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelPhotos> photos; // Ensure this is exactly List<HotelPhotos>

    @ElementCollection
    @CollectionTable(name = "room_amenities", joinColumns = @JoinColumn(name = "room_id"))
    private List<String> amenities;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Embedded
    private HotelContactInfo contactInfo;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity owner;

    @OneToMany(mappedBy = "hotel")
    @JsonIgnore
    private List<Room> rooms;

}