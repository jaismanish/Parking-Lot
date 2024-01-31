package com.main;

import com.main.exception.ParkingAttendantException;
import com.main.strategy.FarthestSlotFirstStrategy;
import com.main.strategy.NearestSlotFirstStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingAttendantTest {

    @Test
    void testUnassignedAttendantCannotParkCar() {
        ParkingAttendant unassignedAttendant = new ParkingAttendant();
        Car car = new Car("AB12BC1234", Color.RED);

        assertThrows(RuntimeException.class, () -> unassignedAttendant.parkCar(car));
    }

    @Test
    void testUnassignedAttendantCannotUnparkCar() {
        ParkingAttendant unassignedAttendant = new ParkingAttendant();

        assertThrows(ParkingAttendantException.class, ()
                -> unassignedAttendant.unparkCar("1", "AB12BC1234"));
    }

    @Test
    void testParkingAttendantCanParkCar() {
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        ParkingLot parkingLot = new ParkingLot(5);
        parkingAttendant.assignParkingLot(parkingLot);

        Car car = new Car("AB12BC1234", Color.RED);

        String slotNumber = parkingAttendant.parkCar(car);
        assertNotNull(slotNumber);
    }

    @Test
    void testParkingAttendantCanUnparkCar() {
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        ParkingLot parkingLot = new ParkingLot(5);
        parkingAttendant.assignParkingLot(parkingLot);

        Car car = mock(Car.class);
        when(car.registrationNumber()).thenReturn("AB12BC1234");
        when(car.color()).thenReturn(Color.RED);

        String slotNumber = parkingAttendant.parkCar(car);
        Car unparkedCar = parkingAttendant.unparkCar(slotNumber, "AB12BC1234");

        assertEquals(car, unparkedCar);
    }

    @Test
    void testParkingAttendantThrowsExceptionWhenAllLotsAreFull() {
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingAttendant.assignParkingLot(parkingLot1);
        parkingAttendant.assignParkingLot(parkingLot2);

        Car car1 = mock(Car.class);
        when(car1.registrationNumber()).thenReturn("AB12BC1234");
        when(car1.color()).thenReturn(Color.RED);

        Car car2 = mock(Car.class);
        when(car2.registrationNumber()).thenReturn("CD34DE5678");
        when(car2.color()).thenReturn(Color.BLUE);

        parkingAttendant.parkCar(car1);

        assertThrows(RuntimeException.class, () -> parkingAttendant.parkCar(car2));
    }

    @Test
    void testParkingAttendantCanParkCarIn2Lots() {
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingAttendant.assignParkingLot(parkingLot1);
        parkingAttendant.assignParkingLot(parkingLot2);

        Car car1 = mock(Car.class);
        when(car1.registrationNumber()).thenReturn("AB12BC1234");
        when(car1.color()).thenReturn(Color.RED);

        Car car2 = mock(Car.class);
        when(car2.registrationNumber()).thenReturn("CD34DE5678");
        when(car2.color()).thenReturn(Color.BLUE);

        String slotNumber1 = parkingAttendant.parkCar(car1);
        String slotNumber2 = parkingAttendant.parkCar(car2);

        assertNotNull(slotNumber1);
        assertNotNull(slotNumber2);
        assertNotEquals(slotNumber1, slotNumber2);
    }

    @Test
    void testParkingAttendantCanUnparkCarIn2Lots() {
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingAttendant.assignParkingLot(parkingLot1);
        parkingAttendant.assignParkingLot(parkingLot2);

        Car car1 = mock(Car.class);
        when(car1.registrationNumber()).thenReturn("AB12BC1234");
        when(car1.color()).thenReturn(Color.RED);

        Car car2 = mock(Car.class);
        when(car2.registrationNumber()).thenReturn("CD34DE5678");
        when(car2.color()).thenReturn(Color.BLUE);

        String slotNumber1 = parkingAttendant.parkCar(car1);
        String slotNumber2 = parkingAttendant.parkCar(car2);

        Car unparkedCar1 = parkingAttendant.unparkCar(slotNumber1, "AB12BC1234");
        Car unparkedCar2 = parkingAttendant.unparkCar(slotNumber2, "CD34DE5678");

        assertEquals(car1, unparkedCar1);
        assertEquals(car2, unparkedCar2);
    }

    @Test
    void test2AttendantsCanParkInSameLot() {
        ParkingLot parkingLot = new ParkingLot(5);
        ParkingAttendant attendant1 = new ParkingAttendant();
        ParkingAttendant attendant2 = new ParkingAttendant();

        attendant1.assignParkingLot(parkingLot);
        attendant2.assignParkingLot(parkingLot);

        Car car1 = mock(Car.class);
        when(car1.registrationNumber()).thenReturn("AB12BC1234");
        when(car1.color()).thenReturn(Color.RED);

        Car car2 = mock(Car.class);
        when(car2.registrationNumber()).thenReturn("CD34DE5678");
        when(car2.color()).thenReturn(Color.BLUE);

        String slotNumber1 = attendant1.parkCar(car1);
        String slotNumber2 = attendant2.parkCar(car2);

        assertNotNull(slotNumber1);
        assertNotNull(slotNumber2);
        assertNotEquals(slotNumber1, slotNumber2);
    }

    @Test
    void testChangeParkingStrategy() {
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        ParkingLot parkingLot = new ParkingLot(5);
        parkingAttendant.assignParkingLot(parkingLot);

        parkingAttendant.parkCar(new Car("AB12BC1232", Color.RED));
        parkingAttendant.parkCar(new Car("AB12BC1233", Color.BLUE));
        parkingAttendant.parkCar(new Car("AB12BC1234", Color.GREEN));

        assertEquals("3", parkingAttendant.parkCar(new Car("CD34DE5678", Color.WHITE)));

        parkingAttendant.switchParkingStrategy(new FarthestSlotFirstStrategy());
        assertEquals("5", parkingAttendant.parkCar(new Car("CD34DE5677", Color.BLACK)));

        parkingAttendant.switchParkingStrategy(new NearestSlotFirstStrategy());
        assertEquals("4", parkingAttendant.parkCar(new Car("CD34DE5676", Color.ORANGE)));
    }
}
