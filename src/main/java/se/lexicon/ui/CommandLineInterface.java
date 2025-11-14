package se.lexicon.ui;

import se.lexicon.dao.CourseDao;
import se.lexicon.dao.StudentDao;
import se.lexicon.model.Student;

import java.util.Scanner;

public class CommandLineInterface {
    private final Scanner scanner = new Scanner(System.in);
    private StudentDao studentDao;
    private CourseDao courseDao;

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

        switch (scanner.next()) {
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
            default:
                mainMenu();
        }
    }

    private void create() {
        System.out.print("Create course or student: \n" +
                "1. Create new courses \n" +
                "2. Create new student \n" +
                "0. Main menu \n" +
                "> ");

        switch (scanner.next()) {
            case "1":
                newCourse();
            case "2":
                newStudent();
            case "0":
                mainMenu();
            default:
                create();
        }
        create();
    }

    private void newCourse() {
    }

    private void newStudent() {
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        Student student = studentDao.save(new Student(name, email, address));
        System.out.println("Following student was added: " + student);
    }

    private void courseRegistration() {
    }

    private void read() {
    }

    private void update() {
    }
}
