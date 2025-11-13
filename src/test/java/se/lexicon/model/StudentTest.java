package se.lexicon.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student;

    @BeforeEach
    void setUp() {
        student = new Student(0, "name", "name@gmail.com", "Street 2, City");
    }

    @Test
    void testConstructor() {
        // Arrange

        // Act
        int id = student.getId();
        String name = student.getName();
        String email = student.getEmail();
        String address = student.getAddress();

        // Assert
        assertEquals(0, id, "id incorrect");
        assertEquals("name", name, "name incorrect");
        assertEquals("name@gmail.com", email, "email incorrect");
        assertEquals("Street 2, City", address, "address incorrect");
    }

}