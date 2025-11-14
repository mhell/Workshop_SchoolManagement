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
        assertEquals(student, studentAdded, "The student wasn't saved");
    }

    @Test
    void save_existing_throwsRuntimeException() {
        studentDao.save(student);
        Executable action = () -> studentDao.save(student);
        assertThrows(RuntimeException.class, action, "Should throw when duplicates");
    }

    @Test
    void save_null_throwsIllegalArgumentException() {
        Executable action = () -> studentDao.save(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }

    @Test
    void findByEmail_existingEmail_returnFirstFound() {
        studentDao.save(student);
        Student found = studentDao.findByEmail(email);
        assertEquals(student, found, "The email wasn't found");
    }

    @Test
    void findByEmail_nonExistingEmail_returnNull() {
        studentDao.save(student);
        Student found = studentDao.findByEmail("nonexistent");
        assertNull(found, "Should return null");
    }

    @Test
    void findByName_existingName_returnAllFound() {
        studentDao.save(student);
        List<Student> found = studentDao.findByName(name);
        assertNotNull(found, "Should not return null");
        assertEquals(1, found.size(), "Should find 1 student");
        assertEquals(student, found.get(0), "Should find the added student");
    }

    @Test
    void findByName_nonExistingName_returnEmpty() {
        studentDao.save(student);
        List<Student> found = studentDao.findByName("nonexistent");
        assertEquals(0, found.size(), "Should find 0 students");
    }

    @Test
    void findById_existingId_returnsStudent() {
        studentDao.save(student);
        Student found = studentDao.findById(student.getId());
        assertEquals(student, found, "The id wasn't found");
    }

    @Test
    void findById_nonExistingId_returnNull() {
        studentDao.save(student);
        Student found = studentDao.findById(999);
        assertNull(found, "Should return null");
    }

    @Test
    void findAll_afterAddingOne_returnNone() {
        studentDao.save(student);
        List<Student> found = studentDao.findAll();
        assertEquals(1, found.size(), "Should find 1 student");
        assertEquals(student, found.get(0), "Should find the added student");
    }

    @Test
    void delete_existingStudent_returnTrue() {
        studentDao.save(student);
        boolean result = studentDao.delete(student);
        assertTrue(result, "Should return true");
    }

    @Test
    void delete_nonExistingStudent_returnFalse() {
        boolean result = studentDao.delete(student);
        assertFalse(result, "Should return false");
    }

    @Test
    void delete_nullStudent_throwIllegalArgumentException() {
        Executable action = () -> studentDao.delete(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }
}