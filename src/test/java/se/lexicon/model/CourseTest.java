package se.lexicon.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    Course course;
    String courseName = "Lexicon";
    LocalDate startDate = LocalDate.now();
    int weekDuration = 50;
    Student student = new Student("Name", "name@mailcom", "Address");

    @BeforeEach
    void setUp() {
        course = new Course(courseName, startDate, weekDuration);
    }

    @Test
    void register_nonexisting_studentAdded() {
        // Arrange
        course.register(student);
        // Act
        Student studentAdded = course.getStudents().get(course.getStudents().size()-1);
        // Assert
        assertEquals(student, studentAdded, "The student wasn't registered");
    }

    @Test
    void register_existing_throwsRuntimeException() {
        course.register(student);
        Executable action = () -> course.register(student);
        assertThrows(RuntimeException.class, action, "The student already registered");
    }

    @Test
    void register_null_throwsIllegalArgumentException() {
        Executable action = () -> course.register(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }

    @Test
    void unregister_existing_studentRemoved() {
        course.register(student);
        course.unregister(student);
        int courseNumElements = course.getStudents().size();
        assertEquals(0, courseNumElements, "Student wasn't removed");
    }

    @Test
    void unregister_nonexisting_throwsRuntimeException() {
        Executable action = () -> course.unregister(student);
        assertThrows(RuntimeException.class, action, "The student wasn't registered");
    }

    @Test
    void unregister_null_throwsRuntimeException() {
        Executable action = () -> course.unregister(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }
}