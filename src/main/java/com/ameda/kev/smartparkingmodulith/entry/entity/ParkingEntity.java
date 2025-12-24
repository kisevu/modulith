package com.ameda.kev.smartparkingmodulith.entry.entity;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.domain.ParkingBuilder;
import com.ameda.kev.smartparkingmodulith.entry.vo.ParkingStatus;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehicleName;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehiclePublicId;
import com.ameda.kev.smartparkingmodulith.shared.entity.AbstractAuditing;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */

@Entity
@Table( name = "tbl_parking")
@Builder
public class ParkingEntity  extends AbstractAuditing<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parkingSequenceGenerator")
    @SequenceGenerator(name = "parkingSequenceGenerator", sequenceName = "parking_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "vehicle", nullable = false)
    private String vehicle;
    @Column(name = "entry_time", nullable = false)
    private Instant entryTime;
    @Column(name = "exit_time", nullable = false)
    private Instant exitTime;
    @Column(name = "active",nullable = false)
    @Enumerated(EnumType.STRING)
    private ParkingStatus active;

    @Column(name = "public_id", nullable = false, unique = true)
    private UUID publicId;
    @Column(name = "vehicle_reg_no", nullable = false, unique = true)
    private String vehicleRegNo;

    @Column(name = "owner_name",nullable = false)

    private String ownerName;

    @Column(name = "owner_id_no",nullable = false,unique = true)
    private String ownerIdNo;

    @Column(name = "last_seen")
    private Instant lastSeen;


    public ParkingEntity() {
    }

    public ParkingEntity(Long id, String vehicle, Instant entryTime, Instant exitTime,
                         ParkingStatus active, UUID publicId,
                         String vehicleRegNo, String ownerName, String ownerIdNo,
                         Instant lastSeen) {
        this.id = id;
        this.vehicle = vehicle;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.active = active;
        this.publicId = publicId;
        this.vehicleRegNo = vehicleRegNo;
        this.ownerName = ownerName;
        this.ownerIdNo = ownerIdNo;
        this.lastSeen = lastSeen;
    }

    public void updateFromParking(Parking parking){
        this.entryTime = parking.getEntryTime();
        this.exitTime = parking.getExitTime();
        this.active = parking.getStatus();
        this.lastSeen = parking.getLastSeen();
    }

    public static ParkingEntity fromDomain(Parking parking){
        ParkingEntityBuilder parkingEntityBuilder = ParkingEntityBuilder.parkingEntity();
        return parkingEntityBuilder
                .vehicle(parking.getName().name())
                .publicId(parking.getPublicId().value())
                .active(parking.getStatus())
                .entryTime(parking.getEntryTime())
                .exitTime(parking.getExitTime())
                .vehicleRegNo(parking.getVehicleRegNo())
                .ownerName(parking.getOwnerName())
                .ownerIdNo(parking.getOwnerIdNo())
                .lastSeen(parking.getLastSeen())
                .build();
    }

    public static Parking toDomain(ParkingEntity parkingEntity){
        return ParkingBuilder.parking()
                .name(new VehicleName(parkingEntity.getVehicle()))
                .publicId(new VehiclePublicId(parkingEntity.getPublicId()))
                .status(parkingEntity.getActive())
                .entryTime(parkingEntity.getEntryTime())
                .exitTime(parkingEntity.getExitTime())
                .vehicleRegNo(parkingEntity.getVehicleRegNo())
                .ownerName(parkingEntity.getOwnerName())
                .ownerIdNo(parkingEntity.getOwnerIdNo())
                .lastSeen(parkingEntity.getLastSeen())
                .build();
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Instant entryTime) {
        this.entryTime = entryTime;
    }

    public Instant getExitTime() {
        return exitTime;
    }

    public void setExitTime(Instant exitTime) {
        this.exitTime = exitTime;
    }

    public ParkingStatus getActive() {
        return active;
    }

    public void setActive(ParkingStatus active) {
        this.active = active;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdNo() {
        return ownerIdNo;
    }

    public void setOwnerIdNo(String ownerIdNo) {
        this.ownerIdNo = ownerIdNo;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ParkingEntity that)) return false;
        return Objects.equals(getPublicId(), that.getPublicId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPublicId());
    }
}
