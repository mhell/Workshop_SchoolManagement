package se.lexicon.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.lexicon.model.Student;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest {
    String name = "Name";
    String email = "name@mailcom";
    String address = "Address";
    Student student = new Student(name, email, address);
    StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new StudentDaoImpl();
    }

    @Test
    void save_newStudent_returnStudent() {
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
    void findByEmail_emailExists_returnFirstFound() {
        studentDao.save(student);
        Student foundStudent = studentDao.findByEmail(email);
        assertEquals(student, foundStudent, "The email wasn't found");
    }

    @Test
    void findByEmail_emailDontExist_returnNull() {
        studentDao.save(student);
        Student foundStudent = studentDao.findByEmail("nonexistent");
        assertNull(foundStudent, "Should return null");
    }

    @Test
    void findByName_nameExists_returnAllFound() {
        studentDao.save(student);
        List<Student> studentsFound = studentDao.findByName(name);
        assertEquals(1, studentsFound.size(), "Should find 1 student");
        assertEquals(student, studentsFound.get(0), "Should find the added student");
    }

    @Test
    void findByName_nameDontExists_returnNull() {
        studentDao.save(student);
        List<Student> foundStudents = studentDao.findByName("nonexistent");
        assertNull(foundStudents, "Should return null");
    }

    @Test
    void findById_idExists_returnFirstFound() {
        studentDao.save(student);
        Student foundStudent = studentDao.findById(student.getId());
        assertEquals(student, foundStudent, "The id wasn't found");
    }

    @Test
    void findById_idDontExists_returnNull() {
        studentDao.save(student);
        Student foundStudent = studentDao.findById(1);
        assertNull(foundStudent, "Should return null");
    }

    @Test
    void findAll_afterAddOne_returNone() {
        studentDao.save(student);
        List<Student> studentsFound = studentDao.findAll();
        assertEquals(1, studentsFound.size(), "Should find 1 student");
        assertEquals(student, studentsFound.get(0), "Should find the added student");
    }

    @Test
    void delete_studentExist_returnTrue() {
        studentDao.save(student);
        boolean result = studentDao.delete(student);
        assertTrue(result, "Should return true");
    }

    @Test
    void delete_studentDontExist_returnFalse() {
        boolean result = studentDao.delete(student);
        assertFalse(result, "Should return false");
    }

    @Test
    void delete_studentNull_throwIllegalArgumentException() {
        Executable action = () -> studentDao.delete(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }
}