package se.lexicon.model;

import java.util.Objects;

public class Student {
    private static int sequencer = 0;
    private final int id;
    private String name;
    private String email;
    private String address;

    public Student(String name, String email, String address) {
        this.id = sequencer++;
        setName(name);
        setEmail(email);
        setAddress(address);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getId() == student.getId() && Objects.equals(getName(), student.getName()) && Objects.equals(getEmail(), student.getEmail()) && Objects.equals(getAddress(), student.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getAddress());
    }
}
