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
    void findByEmail_emailexists_returnfirstfound() {
        studentDao.save(student);
        Student foundStudent = studentDao.findByEmail(email);
        assertEquals(student, foundStudent);
    }

    @Test
    void findByEmail_emaildontexist_returnnull() {
        studentDao.save(student);
        Student foundStudent = studentDao.findByEmail("nonexistent");
        assertNull(foundStudent);
    }

    @Test
    void findByName_nameexists_returnallfound() {
        studentDao.save(student);
        List<Student> studentsFound = studentDao.findByName(name);
        assertEquals(1, studentsFound.size());
        assertEquals(student, studentsFound.get(0));
    }

    @Test
    void findByName_namedontexists_returnnull() {
        studentDao.save(student);
        List<Student> foundStudents = studentDao.findByName("nonexistent");
        assertNull(foundStudents);
    }

    @Test
    void findById_idexists_returnfirstfound() {
        studentDao.save(student);
        Student foundStudent = studentDao.findById(student.getId());
        assertEquals(student, foundStudent);
    }

    @Test
    void findById_iddontexists_returnnull() {
        studentDao.save(student);
        Student foundStudent = studentDao.findById(1);
        assertNull(foundStudent);
    }

    @Test
    void findAll_afteraddone_returnone() {
        studentDao.save(student);
        List<Student> studentsFound = studentDao.findAll();
        assertEquals(1, studentsFound.size());
        assertEquals(student, studentsFound.get(0));
    }

    @Test
    void delete_studentexist_returntrue() {
        studentDao.save(student);
        boolean result = studentDao.delete(student);
        assertTrue(result);
    }

    @Test
    void delete_studentdontexist_returnfalse() {
        boolean result = studentDao.delete(student);
        assertFalse(result);
    }

    @Test
    void delete_studentnull_throwIllegalArgumentException() {
        Executable action = () -> studentDao.delete(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }
}