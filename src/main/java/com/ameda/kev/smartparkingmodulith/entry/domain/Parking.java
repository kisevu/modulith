package com.ameda.kev.smartparkingmodulith.entry.domain;

import com.ameda.kev.smartparkingmodulith.entry.entity.ParkingEntity;
import com.ameda.kev.smartparkingmodulith.entry.entity.ParkingEntityBuilder;
import com.ameda.kev.smartparkingmodulith.entry.vo.ParkingStatus;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehicleName;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehiclePublicId;
import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;
import org.jilt.Builder;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */

@Builder
public class Parking {
    private VehicleName name;
    private Instant entryTime;
    private Instant exitTime;
    private ParkingStatus status;
    private VehiclePublicId publicId;
    private String vehicleRegNo;
    private String ownerName;
    private String ownerIdNo;
    private Long dbId;
    private Instant lastSeen;

    public Parking() {
    }

    public void assertMandatoryFields(String vehicleRegNo,
                                      String ownerName,
                                      String ownerIdNo,
                                      Instant lastSeen){
        Assert.notNull("vehicleRegNo",vehicleRegNo);
        Assert.notNull("ownerName",ownerName);
        Assert.notNull("ownerIdNo",ownerIdNo);
        Assert.notNull("lastSeen",lastSeen);
    }

    public Parking(VehicleName name, Instant entryTime, Instant exitTime, ParkingStatus status,
                   VehiclePublicId publicId, String vehicleRegNo, String ownerName,
                   String ownerIdNo, Long dbId, Instant lastSeen) {
        assertMandatoryFields(vehicleRegNo, ownerName,ownerIdNo,lastSeen);
        this.name = name;
        this.entryTime = Instant.now();
        this.exitTime = Instant.now().plus(Duration.ofHours(1));
        this.status = ParkingStatus.PENDING;
        this.publicId = publicId;
        this.vehicleRegNo = vehicleRegNo;
        this.ownerName = ownerName;
        this.ownerIdNo = ownerIdNo;
        this.dbId = dbId;
        this.lastSeen =  lastSeen;
    }

    public void initDefaultFields(){
        this.publicId = new VehiclePublicId(UUID.randomUUID());
    }

    public static Parking fromDomain(ParkingEntity parkingEntity){
        return  ParkingBuilder.parking()
                .name(new VehicleName(parkingEntity.getOwnerName()))
                .entryTime(parkingEntity.getEntryTime())
                .exitTime(parkingEntity.getExitTime())
                .status(parkingEntity.getActive())
                .publicId(new VehiclePublicId(parkingEntity.getPublicId()))
                .vehicleRegNo(parkingEntity.getVehicleRegNo())
                .ownerName(parkingEntity.getOwnerName())
                .ownerIdNo(parkingEntity.getOwnerIdNo())
                .dbId(parkingEntity.getId())
                .lastSeen(parkingEntity.getLastSeen())
                .build();
    }

    public static ParkingEntity toDomain(Parking parking){
        ParkingEntityBuilder parkingEntityBuilder = ParkingEntityBuilder.parkingEntity();
        if(parking.getDbId() != null ){
            parkingEntityBuilder.id(parking.getDbId());
        }
        parkingEntityBuilder.vehicle(parking.getName().name());
        parkingEntityBuilder.entryTime(parking.getEntryTime());
        parkingEntityBuilder.exitTime(parking.getExitTime());
        parkingEntityBuilder.active(parking.getStatus());
        parkingEntityBuilder.publicId(parking.getPublicId().value());
        parkingEntityBuilder.vehicleRegNo(parking.vehicleRegNo);
        parkingEntityBuilder.ownerName(parking.getOwnerName());
        parkingEntityBuilder.ownerIdNo(parking.getOwnerIdNo());
        parkingEntityBuilder.lastSeen(parking.lastSeen);
        return parkingEntityBuilder.build();
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
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

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public void initFieldOnCreation(){
        this.publicId = new VehiclePublicId(UUID.randomUUID());
    }

    public VehicleName getName() {
        return name;
    }

    public void setName(VehicleName name) {
        this.name = name;
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

    public ParkingStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingStatus status) {
        this.status = status;
    }

    public VehiclePublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(VehiclePublicId publicId) {
        this.publicId = publicId;
    }
}
