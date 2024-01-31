package com.main;

import com.main.Interface.ParkingLotListener;
import com.main.exception.ParkingAttendantException;
import com.main.exception.ParkingLotException;
import com.main.strategy.NearestSlotFirstStrategy;
import com.main.strategy.ParkingStrategy;

import java.util.ArrayList;
import java.util.List;

public class ParkingAttendant implements ParkingLotListener {
    private final List<ParkingLot> assignedParkingLots;
    private ParkingStrategy parkingStrategy;

    public ParkingAttendant() {
        this.assignedParkingLots = new ArrayList<>();
        this.parkingStrategy = new NearestSlotFirstStrategy();
    }
    public void assignParkingLot(ParkingLot parkingLot) {
        assignedParkingLots.add(parkingLot);
        parkingLot.addListener(this);
    }
    private void setParkingStrategy(ParkingStrategy strategy) {
        this.parkingStrategy = strategy;
    }
    public void switchParkingStrategy(ParkingStrategy newStrategy) {
        setParkingStrategy(newStrategy);
        System.out.println("Parking strategy switched to: " + newStrategy.getClass().getSimpleName());
    }
    public String parkCar(Car car) {
        for (ParkingLot parkingLot : assignedParkingLots) {
            try {
                int nextSlot = parkingStrategy.getNextSlotAvailable(parkingLot);
                return parkingLot.park(car, nextSlot);
            } catch (ParkingLotException e) {
                throw new RuntimeException(e);
            }
        }
        throw new ParkingAttendantException("All assigned parking lots are full");
    }
    public Car unparkCar(String slotNumber, String registrationNumber) {
        for (ParkingLot parkingLot : assignedParkingLots) {
            try {
                return parkingLot.unpark(slotNumber, registrationNumber);
            } catch (ParkingLotException e) {
                throw new IllegalArgumentException(e);
            }
        }
        throw new ParkingAttendantException("Car not found in any assigned parking lot");
    }

    @Override
    public void notifyFull() {
        System.out.println("Parking lot is full!");
    }

    @Override
    public void notifyAvailable() {
        System.out.println("Parking lot is available now.");
    }
}
