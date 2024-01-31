package com.main.strategy;

import com.main.Car;
import com.main.ObservableParkingLot;

import java.util.Map;

public class FarthestSlotFirstStrategy implements ParkingStrategy {

    @Override
    public int getNextSlotAvailable(ObservableParkingLot parkingLot) {
        Map<Integer, Car> parkingSlots = parkingLot.getParkingSlots();

        for (int slot = parkingLot.getCapacity(); slot > 0; slot--) {
            if (parkingSlots.get(slot) == null) {
                return slot;
            }
        }

        return -1;
    }
}

