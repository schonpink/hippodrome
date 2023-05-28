import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    @Test
    public void testTypeAndMessageOfException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n\n\n\n\n\n"})
    public void testBlankParameterException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void testWhenSecondParameterNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Spirit", -1, 1));
    }

    @Test
    public void testMessageOfExceptionSecondNegativeNumber() {
        try {
            new Horse("Spirit", -1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void testWhenThirdParameterNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Spirit", 1, -1));
    }

    @Test
    public void testMessageOfExceptionThirdNegativeNumber() {
        try {
            new Horse("Spirit", 1, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void testGetName() {
        Horse horse = new Horse("Spirit", 1, 1);
        assertEquals("Spirit", horse.getName());
    }

    @Test
    public void testGetSpeed() {
        double expectedSpeed = 250;
        Horse horse = new Horse("Spirit", expectedSpeed, 1);
        double actualSpeed = horse.getSpeed();
        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    public void testGetDistance() {
        double expectedDistance = 180;
        Horse horse = new Horse("Spirit", 1, expectedDistance);
        double actualDistance = horse.getDistance();
        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void testZeroDistanceDefault() {
        Horse horse = new Horse("Spirit", 1);
        double actualDistance = horse.getDistance();
        assertEquals(0, actualDistance);
    }

    @Test
    public void testVerifyMoveGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Spirit", 20, 300).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));

        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 99.99, 0.0})
    public void testParameterizedMove(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Spirit", 20, 300);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse = new Horse("test", 3.0);
            horse.move();

            assertEquals(300 + 20 * random, horse.getDistance());
        }
    }


}