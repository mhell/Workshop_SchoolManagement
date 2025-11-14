package se.lexicon.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.lexicon.model.Course;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDaoImplTest {
    CourseDao courseDao;
    String courseName = "Name";
    LocalDate startDate = LocalDate.now();
    int weekDuration = 1;
    Course course = new Course(courseName, startDate, weekDuration);

    @BeforeEach
    void setUp() {
        courseDao = new CourseDaoImpl();
    }

    @Test
    void save_newCourse_returnsCourse() {
        Course saved = courseDao.save(course);
        assertEquals(course, saved, "The course wasn't saved");
    }

    @Test
    void save_existing_throwsRuntimeException() {
        courseDao.save(course);
        Executable action = () -> courseDao.save(course);
        assertThrows(RuntimeException.class, action, "Should throw when duplicates");
    }

    @Test
    void save_null_throwsIllegalArgumentException() {
        Executable action = () -> courseDao.save(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }

    @Test
    void findById_existingId_returnsCourse() {
        courseDao.save(course);
        Course found = courseDao.findById(course.getId());
        assertEquals(course, found, "The id wasn't found");
    }

    @Test
    void findById_nonExistingId_returnsNull() {
        courseDao.save(course);
        Course found = courseDao.findById(999);
        assertNull(found, "Should return null");
    }

    @Test
    void findByName_existingName_returnsListWithCourse() {
        courseDao.save(course);
        List<Course> found = courseDao.findByName(courseName);
        assertNotNull(found, "Should not return null");
        assertEquals(1, found.size(), "Should find 1 course");
        assertEquals(course, found.get(0), "Should find the added course");
    }

    @Test
    void findByName_nonExistingName_returnsNull() {
        courseDao.save(course);
        List<Course> found = courseDao.findByName("nonexistent");
        assertTrue(found.isEmpty(), "Should find 0 courses");
    }

    @Test
    void findByDate_existingDate_returnsListWithCourse() {
        courseDao.save(course);
        List<Course> found = courseDao.findByDate(startDate);
        assertNotNull(found, "Should not return null");
        assertEquals(1, found.size(), "Should find 1 course");
        assertEquals(course, found.get(0), "Should find the added course");
    }

    @Test
    void findByDate_nonExistingDate_returnsNull() {
        courseDao.save(course);
        List<Course> found = courseDao.findByDate(startDate.plusDays(10));
        assertNull(found, "Should return null");
    }

    @Test
    void findAll_afterAddingOne_returnsOneItem() {
        courseDao.save(course);
        List<Course> all = courseDao.findAll();
        assertEquals(1, all.size(), "Should find 1 course");
        assertEquals(course, all.get(0), "Should find the added course");
    }

    @Test
    void delete_existingCourse_returnsTrue() {
        courseDao.save(course);
        boolean result = courseDao.delete(course);
        assertTrue(result, "Should return true");
    }

    @Test
    void delete_nonExistingCourse_returnsFalse() {
        boolean result = courseDao.delete(course);
        assertFalse(result, "Should return false");
    }

    @Test
    void delete_nullCourse_throwsIllegalArgumentException() {
        Executable action = () -> courseDao.delete(null);
        assertThrows(IllegalArgumentException.class, action, "Should throw when null");
    }
}
