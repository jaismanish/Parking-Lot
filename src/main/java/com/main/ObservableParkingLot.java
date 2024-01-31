package com.main;

import com.main.Interface.ParkingLotListener;
import com.main.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservableParkingLot {
    private final Map<Integer, Car> parkingSlots;
    private final int capacity;
    private int nextSlotAvailable;
    private boolean isLotFull;
    private final List<ParkingLotListener> listeners;

    public ObservableParkingLot(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.parkingSlots = new HashMap<>();
        this.nextSlotAvailable = 1;
        this.listeners = new ArrayList<>();
    }

    public void addListener(ParkingLotListener listener) {
        this.listeners.add(listener);
    }

    void notifyListenersFull() {
        for (ParkingLotListener listener : listeners) {
            listener.notifyFull();
        }
    }

    void notifyListenersAvailable() {
        for (ParkingLotListener listener : listeners) {
            listener.notifyAvailable();
        }
    }

    public String park(Car car) {
        checkForSameCarParked(car);

        if (isLotFull) {
            notifyListenersFull();
        } else {
            notifyListenersAvailable();
        }

        Integer slot = getEmptySlot().getOrDefault(true, null);

        if (slot != null) {
            parkingSlots.put(slot, car);
            notifyIfLotIsFullOrAvailable();
            return slot.toString();
        }

        if (isAtFullCapacity()) {
            isLotFull = true;
            notifyIfLotIsFullOrAvailable();
            throw new ParkingLotException("Parking lot is full");
        }

        parkingSlots.put(this.nextSlotAvailable, car);
        notifyIfLotIsFullOrAvailable();
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
            notifyIfLotIsFullOrAvailable();
            return car;
        }

        throw new ParkingLotException("Car not found. Thus, cannot be unparked");
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
            throw new ParkingLotException("Car is parked already");
        }
    }

    public void addParkingLotListener(ParkingLotListener listener) {
        listeners.add(listener);
    }


    private void notifyIfLotIsFullOrAvailable() {
        if (isLotFull) {
            System.out.println("Parking lot is full!");
        } else {
            System.out.println("Parking lot is available!");
        }
    }


    public Map<Integer, Car> getParkingSlots() {
        return parkingSlots;
    }

    public int getCapacity() {
        return capacity;
    }
}
