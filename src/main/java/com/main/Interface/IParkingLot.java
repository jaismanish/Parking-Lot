package com.main.Interface;

import com.main.Car;
import com.main.Color;
import com.main.exception.ParkingLotException;


public interface IParkingLot {
    String park(Car car) throws ParkingLotException;
    Car unpark(String slotNumber, String registrationNumber) throws ParkingLotException;
    boolean isCarParked(Car car);
    int countCarsByColor(Color color);
}
