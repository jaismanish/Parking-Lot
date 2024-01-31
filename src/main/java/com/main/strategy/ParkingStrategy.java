package com.main.strategy;

import com.main.ObservableParkingLot;

public interface ParkingStrategy {
    int getNextSlotAvailable(ObservableParkingLot parkingLot);
}
