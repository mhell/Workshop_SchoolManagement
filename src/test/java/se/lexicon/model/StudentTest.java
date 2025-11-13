package se.lexicon.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    int id = 0;
    String name = "name";
    String email = "name@gmail.com";
    String address = "Street 2, City";
    Student student;

    @BeforeEach
    void setUp() {
        student = new Student(0, name, email, address);
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
        assertEquals(this.id, id, "id incorrect");
        assertEquals(this.name, name, "name incorrect");
        assertEquals(this.email, email, "email incorrect");
        assertEquals(this.address, address, "address incorrect");
    }

}