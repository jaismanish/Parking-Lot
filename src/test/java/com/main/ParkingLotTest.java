package com.main;


import com.main.Car;
import com.main.Color;
import com.main.ParkingLot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ParkingLotTest {
    @Test
    void testParkingLotIsCreated() {
        ParkingLot parkingLot = new ParkingLot(12);
        assertNotNull(parkingLot);
    }

    @Test
    void testParkingLotWithZeroLotsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0));
    }

    @Test
    void testParkingLotWithNegative5LotsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-5));
    }

    @Test
    void testParkingLotWithNegative1LotsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-1));
    }

    @Test
    void testACarIsParkedInTheParkingLot() {
        ParkingLot parkingLot = new ParkingLot(10);
        Car car1 = mock(Car.class);
        String actual = parkingLot.park(car1);

        assertEquals("1", actual);
    }

    @Test
    void testACarIsNotPresentInTheParkingLot() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = mock(Car.class);

        boolean actual = parkingLot.isCarParked(car);

        assertFalse(actual);
    }

    @Test
    void test2BlueCarsPresentInTheParkingLot() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car3 = mock(Car.class);

        when(car1.color()).thenReturn(Color.BLUE);
        when(car2.color()).thenReturn(Color.BLUE);
        when(car3.color()).thenReturn(Color.BLACK);
        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.park(car3);

        int actual = parkingLot.countCarsByColor(Color.BLUE);

        assertEquals(2, actual);
    }

    @Test
    void test0BlackCarsPresentInTheParkingLot() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car3 = mock(Car.class);

        when(car1.color()).thenReturn(Color.BLUE);
        when(car2.color()).thenReturn(Color.BLUE);
        when(car3.color()).thenReturn(Color.BLUE);
        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.park(car3);

        int actual = parkingLot.countCarsByColor(Color.BLACK);

        assertEquals(0, actual);
    }

    @Test
    void testParkingTheSameCarTwiceWithoutUnparkingThrowsException() {
        ParkingLot parkingLot = new ParkingLot(3);
        Car car = mock(Car.class);

        parkingLot.park(car);

        assertThrows(IllegalArgumentException.class, () -> parkingLot.park(car));
    }

    @Test
    void testParkingACarInFullCapacityParkingLotThrowsException() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);

        parkingLot.park(car1);

        assertThrows(RuntimeException.class, () -> parkingLot.park(car2));
    }

    @Test
    void testUnparkingACar() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = mock(Car.class);

        when(car.registrationNumber()).thenReturn("AB12BC1234");
        String slotNumber = parkingLot.park(car);
        Car unparkedCar = parkingLot.unpark(slotNumber,"AB12BC1234");

        assertEquals(car, unparkedCar);
    }

    @Test
    void testUnparkingACarNotPresentThrowsException() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car1 = new Car("AB12BC1234", Color.RED);
        Car car2 = new Car("AA12BC1234", Color.RED);


        parkingLot.park(car1);
        parkingLot.park(car2);

        assertThrows(IllegalArgumentException.class, () -> parkingLot.unpark("2","CB12AA1234"));
    }

    @Test
    void testParkingACarInUnparkedLot() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car = mock(Car.class);

        when(car1.registrationNumber()).thenReturn("AB12BC1234");
        String unparkedLot = parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.unpark(unparkedLot, "AB12BC1234");
        String actual = parkingLot.park(car);

        assertEquals(unparkedLot, actual);
    }
}
