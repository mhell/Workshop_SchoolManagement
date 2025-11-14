package se.lexicon.ui;

import se.lexicon.dao.CourseDao;
import se.lexicon.dao.StudentDao;
import se.lexicon.model.Course;
import se.lexicon.model.Student;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public CommandLineInterface(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    public void start() {
        mainMenu();
    }

    public void mainMenu() {
        System.out.print("Main menu: \n" +
                "1. Create new courses and students \n" +
                "2. Register and remove students from courses \n" +
                "3. Search for students and courses \n" +
                "4. Edit existing students and courses \n" +
                "0. Quit \n" +
                "> ");

        switch (scanner.nextLine()) {
            case "1":
                create();
                break;
            case "2":
                courseRegistration();
                break;
            case "3":
                read();
                break;
            case "4":
                update();
                break;
            case "0":
                System.exit(0);
        }
        mainMenu();
    }

    //region <Create>

    private void create() {
        System.out.print("Create course or student: \n" +
                "1. Create new courses \n" +
                "2. Create new student \n" +
                "0. Main menu \n" +
                "> ");

        switch (scanner.nextLine()) {
            case "1":
                newCourse();
                break;
            case "2":
                newStudent();
                break;
            case "0":
                mainMenu();
                return;
        }
        create();
    }

    private void newCourse() {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter start date (e.g. 2007-12-03): ");
        LocalDate startDate;
        try {
            startDate = LocalDate.parse(scanner.nextLine());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            newCourse();
            return;
        }
        System.out.print("Enter duration: ");
        int weekDuration;
        try {
            weekDuration = Integer.parseInt(scanner.nextLine());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            newCourse();
            return;
        }
        Course course = courseDao.save(new Course(courseName, startDate, weekDuration));
        System.out.println("Following course was added: " + course);
    }

    private void newStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        Student student = studentDao.save(new Student(name, email, address));
        System.out.println("Following student was added: " + student);
    }

    //endregion
    //region <Register>

    private void courseRegistration() {
        System.out.print("Register or remove students from courses: \n" +
                "1. Register student to a course \n" +
                "2. Remove student from a course \n" +
                "0. Main menu \n" +
                "> ");

        switch (scanner.nextLine()) {
            case "1":
                registerStudent();
                break;
            case "2":
                unregisterStudent();
                break;
            case "0":
                mainMenu();
                return;
        }
        courseRegistration();
    }

    private void registerStudent() {
        System.out.print("Enter course id: ");
        Course course;
        try {
            course = courseDao.findById(Integer.parseInt(scanner.nextLine()));
            if (course == null) throw new RuntimeException("Course not found");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            courseRegistration();
            return;
        }
        System.out.print("Enter student id to register: ");
        Student student;
        try {
            student = studentDao.findById(Integer.parseInt(scanner.nextLine()));
            if (student == null) throw new RuntimeException("Student not found");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            courseRegistration();
            return;
        }
        try {
            course.register(student);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            courseRegistration();
            return;
        }
        System.out.println("Course '" + course.getCourseName() +
                "' updated with following students " + course.getStudents());
    }

    private void unregisterStudent() {
        System.out.print("Enter course id: ");
        Course course;
        try {
            course = courseDao.findById(Integer.parseInt(scanner.nextLine()));
            if (course == null) throw new RuntimeException("Course not found");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            courseRegistration();
            return;
        }
        System.out.print("Enter student id to remove: ");
        Student student;
        try {
            student = studentDao.findById(Integer.parseInt(scanner.nextLine()));
            if (student == null) throw new RuntimeException("Student not found");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            courseRegistration();
            return;
        }
        try {
            course.unregister(student);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            courseRegistration();
            return;
        }
        System.out.println("Course '" + course.getCourseName() +
                "' updated with following students " + course.getStudents());
    }

    //endregion
    //region <read>

    private void read() {
        System.out.print("Search student and courses: \n" +
                "1. Search for students \n" +
                "2. Search for courses \n" +
                "0. Main menu \n" +
                "> ");

        switch (scanner.nextLine()) {
            case "1":
                searchStudents();
                break;
            case "2":
                searchCourses();
                break;
            case "0":
                mainMenu();
                return;
        }
        read();
    }

    private void searchStudents() {
        System.out.print("Search students: \n" +
                "1. Search by id \n" +
                "2. Search by name \n" +
                "3. Search by email \n" +
                "4. Find all \n" +
                "0. Search menu \n" +
                "> ");
        List<Student> studentsFound = new LinkedList<>();
        switch (scanner.nextLine()) {
            case "1":
                System.out.print("Enter id: ");
                try {
                    Student s = studentDao.findById(Integer.parseInt(scanner.nextLine()));
                    if (s != null) studentsFound.add(s);
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                    searchStudents();
                    return;
                }
                break;
            case "2":
                System.out.print("Enter name: ");
                studentsFound.addAll(studentDao.findByName(scanner.nextLine()));
                break;
            case "3":
                System.out.print("Enter email: ");
                Student s = studentDao.findByEmail(scanner.nextLine());
                if (s != null) studentsFound.add(s);
                break;
            case "4":
                studentsFound.addAll(studentDao.findAll());
                break;
            case "0":
                read();
                return;
        }
        System.out.println("Search result: " + studentsFound);
        searchStudents();
    }

    private void searchCourses() {
        System.out.print("Search courses: \n" +
                "1. Search by id \n" +
                "2. Search by course name \n" +
                "3. Search by start date \n" +
                "4. Find all \n" +
                "0. Search menu \n" +
                "> ");
        List<Course> coursesFound = new LinkedList<>();
        switch (scanner.nextLine()) {
            case "1":
                System.out.print("Enter id: ");
                try {
                    Course c = courseDao.findById(Integer.parseInt(scanner.nextLine()));
                    if (c != null) coursesFound.add(c);
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                    searchCourses();
                    return;
                }
                break;
            case "2":
                System.out.print("Enter course name: ");
                coursesFound.addAll(courseDao.findByName(scanner.nextLine()));
                break;
            case "3":
                System.out.print("Enter start date (e.g. 2007-12-03): ");
                try {
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());
                    coursesFound.addAll(courseDao.findByDate(startDate));
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                    searchCourses();
                    return;
                }
                break;
            case "4":
                coursesFound.addAll(courseDao.findAll());
                break;
            case "0":
                read();
                return;
        }
        System.out.println("Search result: " + coursesFound);
        searchCourses();
    }

    //endregion
    //region <update>

    private void update() {
        System.out.print("Edit students and courses : \n" +
                "1. Edit students \n" +
                "2. Edit courses \n" +
                "0. Main menu \n" +
                "> ");

        switch (scanner.nextLine()) {
            case "1":
                editStudents();
                break;
            case "2":
                editCourses();
                break;
            case "0":
                mainMenu();
                return;
        }
        update();
    }

    private void editStudents() {
        System.out.print("Enter student id to edit: ");
        Student student;
        try {
            student = studentDao.findById(Integer.parseInt(scanner.nextLine()));
            if (student == null) throw new RuntimeException("Student not found");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            update();
            return;
        }
        System.out.print("Edit " + student + ": \n" +
                "1. Change name \n" +
                "2. Change email \n" +
                "3. Change address \n" +
                "0. Edit manu \n" +
                "> ");

        switch (scanner.nextLine()) {
            case "1":
                System.out.print("Enter new name: ");
                student.setName(scanner.nextLine());
                break;
            case "2":
                System.out.print("Enter new email: ");
                student.setEmail(scanner.nextLine());
                break;
            case "3":
                System.out.print("Enter new address: ");
                student.setAddress(scanner.nextLine());
                break;
            case "0":
                update();
                return;
        }
        System.out.println("Student changed to: " + student);
    }

    private void editCourses() {
        System.out.print("Enter course id to edit: ");
        Course course;
        try {
            course = courseDao.findById(Integer.parseInt(scanner.nextLine()));
            if (course == null) throw new RuntimeException("Course not found");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            update();
            return;
        }
        System.out.print("Edit " + course + ": \n" +
                "1. Change course name \n" +
                "2. Change start date \n" +
                "3. Change duration \n" +
                "0. Edit manu \n" +
                "> ");

        switch (scanner.nextLine()) {
            case "1":
                System.out.print("Enter new name: ");
                course.setCourseName(scanner.nextLine());
                break;
            case "2":
                System.out.print("Enter new start date (e.g. 2007-12-03): ");
                try {
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());
                    course.setStartDate(startDate);
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                    update();
                    return;
                }
                break;
            case "3":
                System.out.print("Enter new duration: ");
                try {
                    course.setWeekDuration(Integer.parseInt(scanner.nextLine()));
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                    update();
                    return;
                }
                break;
            case "0":
                update();
                return;
        }
        System.out.println("Course changed to: " + course);
    }

    //endregion
}
