package com.main.Interface;

import com.main.Car;
import com.main.Color;


public interface IParkingLot {
    String park(Car car);
    Car unpark(String slotNumber, String registrationNumber);
    boolean isCarParked(Car car);
    int countCarsByColor(Color color);
}
