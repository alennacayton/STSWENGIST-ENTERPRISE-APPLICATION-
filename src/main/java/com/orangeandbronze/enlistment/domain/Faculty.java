package com.orangeandbronze.enlistment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import javax.persistence.Column;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

@Entity
public class Faculty {
    @Id
    private final int facultyNumber;
    @Column(name = "firstname", columnDefinition = "varchar")
    private final String firstname;
    @Column(name = "lastname", columnDefinition = "varchar")
    private final String lastname;


    public Faculty(int facultyNumber, String firstname, String lastname){
        isTrue(facultyNumber >= 0, "facultyNumber must be non-negative, was: " + facultyNumber);
        this.facultyNumber = facultyNumber;

        notNull(firstname);
        notNull(lastname);

        this.firstname = firstname;
        this.lastname = lastname;

    }

    public int getFacultyNumber() {
        return facultyNumber;
    }


    @Override
    public String toString() {
        return "Faculty# " + facultyNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return facultyNumber == faculty.facultyNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyNumber);
    }

    private Faculty() {
        facultyNumber = -1;
        firstname = null;
        lastname = null;


    }
}
