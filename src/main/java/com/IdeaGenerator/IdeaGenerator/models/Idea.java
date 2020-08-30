package com.IdeaGenerator.IdeaGenerator.models;

import javax.persistence.*;

@Entity
@Table(name = "idea")
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "priceRating")
    @Enumerated(EnumType.STRING)
    private PriceRating priceRating;

    @Column(name = "ideaType")
    @Enumerated(EnumType.STRING)
    private IdeaType ideaType;

    @Column(name = "address")
    private String address;

    @Column(name = "locationType")
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Column(name = "parkingAvailability")
    @Enumerated(EnumType.STRING)
    private ParkingAvailability parkingAvailability;

    public Idea(){}

    public Idea(String name, PriceRating priceRating, IdeaType ideaType, String address,
                LocationType locationType, ParkingAvailability parkingAvailability){
        this.name = name;
        this.priceRating = priceRating;
        this.ideaType = ideaType;
        this.address = address;
        this.locationType = locationType;
        this.parkingAvailability = parkingAvailability;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceRating getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(PriceRating priceRating) {
        this.priceRating = priceRating;
    }

    public IdeaType getIdeaType() {
        return ideaType;
    }

    public void setIdeaType(IdeaType ideaType) {
        this.ideaType = ideaType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public ParkingAvailability getParkingAvailability() {
        return parkingAvailability;
    }

    public void setParkingAvailability(ParkingAvailability parkingAvailability) {
        this.parkingAvailability = parkingAvailability;
    }
}

