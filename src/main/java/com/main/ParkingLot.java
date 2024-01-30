package com.main;

import com.main.Interface.IParkingLot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot implements IParkingLot {
    private final Map<Integer, Car> parkingSlots;
    private final int capacity;
    private int nextSlotAvailable;

    public ParkingLot(int capacity) {
        if(capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        this.parkingSlots = new HashMap<>();
        this.nextSlotAvailable = 1;
    }

    public String park(Car car) {
        checkForSameCarParked(car);

        Integer slot = getEmptySlot().getOrDefault(true, null);

        if (slot != null) {
            parkingSlots.put(slot, car);
            return slot.toString();
        }

        if (isAtFullCapacity()) {
            throw new RuntimeException();
        }

        parkingSlots.put(this.nextSlotAvailable, car);
        return Integer.toString(this.nextSlotAvailable++);
    }

    public boolean isCarParked(Car carToBeChecked) {
        for (Car car : parkingSlots.values()) {
            if (car != null && car.equals(carToBeChecked)) {
                return true;
            }
        }

        return false;
    }

    public int countCarsByColor(Color color) {
        int count = 0;
        for (Car car : parkingSlots.values()) {
            if (car != null && car.color().equals(color)) {
                ++count;
            }
        }

        return count;
    }

    public Car unpark(String slotNumber, String registrationNumber) {
        int slot = Integer.parseInt(slotNumber);
        Car car = parkingSlots.get(slot);
        if (car != null && car.registrationNumber().equals(registrationNumber)) {
            parkingSlots.put(slot, null);
            return car;
        }

        throw new IllegalArgumentException("Car not found. Thus, cannot be unparked");
    }

    public boolean isAtFullCapacity() {
        return this.nextSlotAvailable > this.capacity;
    }

    public Map<Boolean, Integer> getEmptySlot() {
        for (Map.Entry<Integer, Car> entry : parkingSlots.entrySet()) {
            if (entry.getValue() == null) {
                return Map.of(true, entry.getKey());
            }
        }

        return Map.of(false, -1);
    }

    private void checkForSameCarParked(Car car) {
        if (isCarParked(car)) {
            throw new IllegalArgumentException("Same car cannot be parked again");
        }
    }
}
