package com.main;

import com.main.exception.ParkingLotException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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
    void testACarIsNotPresent() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = mock(Car.class);

        boolean actual = parkingLot.isCarParked(car);

        assertFalse(actual);
    }

    @Test
    void testIsBlackCarPresent() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car1 = new Car("AB12BC1234", Color.BLUE);
        Car car2 = new Car("AB12BC1235", Color.RED);
        Car car3 = new Car("AB12BC1236", Color.BLUE);

        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.park(car3);

        int actual = parkingLot.countCarsByColor(Color.BLACK);

        assertEquals(0, actual);
    }

    @Test
    void testParkingTheSameCarTwiceWithoutUnparkingThrowsException() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot(3);
        Car car = new Car("AB12BC1236", Color.BLUE);
        parkingLot.park(car);

        assertThrows(ParkingLotException.class, () -> parkingLot.park(car));
    }

    @Test
    void testParkingACarInFullCapacityParkingLotThrowsException() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car1 = new Car("AB12BC1236", Color.BLUE);
        Car car2 = new Car("AB12BC1234", Color.BLACK);

        parkingLot.park(car1);

        assertThrows(RuntimeException.class, () -> parkingLot.park(car2));
    }

    @Test
    void testUnparkingACarNotPresentThrowsException() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car1 = new Car("AB12BC1234", Color.RED);
        Car car2 = new Car("AA12BC1234", Color.RED);

        parkingLot.park(car1);
        parkingLot.park(car2);

        assertThrows(ParkingLotException.class, () -> parkingLot.unpark("2", "CB12AA1234"));
    }

    @Test
    void testParkingACarInUnparkedLot() throws ParkingLotException {
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

    @Test
    void testACarIsParked() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = mock(Car.class);
        String actual = parkingLot.park(car);

        assertEquals("1", actual);
    }

    @Test
    void testIsBlueCarPresent() throws ParkingLotException {
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
    void testUnparkACar() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = mock(Car.class);

        when(car.registrationNumber()).thenReturn("AB12BC1234");
        String slotNumber = parkingLot.park(car);
        Car unparkedCar = parkingLot.unpark(slotNumber, "AB12BC1234");

        assertEquals(car, unparkedCar);
    }


}
