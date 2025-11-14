package se.lexicon.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.lexicon.model.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest {
    Student student = new Student("Name", "name@mailcom", "Address");
    StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new StudentDaoImpl();
    }

    @Test
    void save_newstudent_returnstudent() {
        Student studentAdded = studentDao.save(student);
        assertEquals(student, studentAdded);
    }

    @Test
    void save_existing_throwsRuntimeException() {
        studentDao.save(student);
        Executable action = () -> studentDao.save(student);
        assertThrows(RuntimeException.class, action, "The student already added");
    }

    @Test
    void save_null_throwsIllegalArgumentException() {
        Executable action = () -> studentDao.save(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }
}