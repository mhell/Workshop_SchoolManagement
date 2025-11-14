package se.lexicon;

import se.lexicon.dao.CourseDao;
import se.lexicon.dao.CourseDaoImpl;
import se.lexicon.dao.StudentDao;
import se.lexicon.dao.StudentDaoImpl;
import se.lexicon.ui.CommandLineInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentDao studentDao = new StudentDaoImpl();
        CourseDao courseDao = new CourseDaoImpl();
        CommandLineInterface cli = new CommandLineInterface(studentDao, courseDao);
        cli.start();
    }
}