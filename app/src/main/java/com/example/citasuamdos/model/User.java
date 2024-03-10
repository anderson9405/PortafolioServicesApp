package com.example.citasuamdos.model;

public class User {
    String name, email, age, program;
    public User(){};

    public User(String name, String email, String age, String program) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.program = program;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
