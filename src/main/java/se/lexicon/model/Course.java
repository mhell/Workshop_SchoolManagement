package se.lexicon.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Course {

    private static int sequencer = 0;
    private final int id;
    private String courseName;
    private LocalDate startDate;
    private int weekDuration;
    private List<Student> students = new LinkedList<>();

    public Course(String courseName, LocalDate startDate, int weekDuration) {
        this.id = sequencer++;
        setCourseName(courseName);
        setStartDate(startDate);
        setWeekDuration(weekDuration);
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getWeekDuration() {
        return weekDuration;
    }

    public void setWeekDuration(int weekDuration) {
        this.weekDuration = weekDuration;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void register(Student student) {
        if (students.contains(student)) {
            throw new RuntimeException("Student already registered");
        } else if (student == null) {
            throw new IllegalArgumentException("Student is null");
        }
        students.add(student);
    }

    public void unregister(Student student) {
        if (students.contains(student)) {
            students.remove(student);
        } else if (student == null) {
            throw new IllegalArgumentException("Student is null");
        } else {
            throw new RuntimeException("Student wasn't registered");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return getId() == course.getId() && getWeekDuration() == course.getWeekDuration() && Objects.equals(getCourseName(), course.getCourseName()) && Objects.equals(getStartDate(), course.getStartDate()) && Objects.equals(getStudents(), course.getStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCourseName(), getStartDate(), getWeekDuration(), getStudents());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", startDate=" + startDate +
                ", weekDuration=" + weekDuration +
                ", students=" + students +
                '}';
    }
}
