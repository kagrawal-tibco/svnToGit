package com.tibco.cep.query.stream._join2_.example.domain;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 6:39:13 PM
*/
public class Person {
    protected Number id;

    protected String firstName;

    protected String lastName;

    protected int age;

    protected String gender;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":: " + getId() + ", " + getFirstName() + ", " +
                getLastName() + ", " + getAge() + ", " + getGender();
    }
}
