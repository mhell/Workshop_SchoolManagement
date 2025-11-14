package se.lexicon.ui;

import se.lexicon.dao.CourseDao;
import se.lexicon.dao.StudentDao;
import se.lexicon.model.Course;
import se.lexicon.model.Student;

import java.time.LocalDate;
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
                removeStudent();
                break;
            case "0":
                mainMenu();
                return;
        }
        courseRegistration();
    }

    private void registerStudent() {
    }

    private void removeStudent() {
    }

    //endregion

    private void read() {
    }

    private void update() {
    }
}
