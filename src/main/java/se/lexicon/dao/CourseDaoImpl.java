package se.lexicon.dao;

import se.lexicon.model.Course;
import se.lexicon.model.Student;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    private List<Course> courses = new LinkedList<>();

    @Override
    public Course save(Course course) {
        if (courses.contains(course)) {
            throw new RuntimeException("Student already added");
        } else if (course == null) {
            throw new IllegalArgumentException("Student is null");
        }
        courses.add(course);
        return course;
    }

    @Override
    public Course findById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    @Override
    public List<Course> findByName(String name) {
        List<Course> courseFound = new LinkedList<>();
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(name)) {
                courseFound.add(course);
            }
        }
        if (!courseFound.isEmpty()) {
            return courseFound;
        } else {
            return null;
        }
    }

    @Override
    public List<Course> findByDate(LocalDate date) {
        List<Course> courseFound = new LinkedList<>();
        for (Course course : courses) {
            if (course.getStartDate().equals(date)) {
                courseFound.add(course);
            }
        }
        if (!courseFound.isEmpty()) {
            return courseFound;
        } else {
            return null;
        }
    }

    @Override
    public List<Course> findAll() {
        return courses;
    }

    @Override
    public boolean delete(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course is null");
        }
        return courses.remove(course);
    }
}
