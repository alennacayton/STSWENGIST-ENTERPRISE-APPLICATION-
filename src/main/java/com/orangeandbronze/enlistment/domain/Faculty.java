package com.orangeandbronze.enlistment.domain;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;

public class Faculty {
    private final int facultyNumber;

    public Faculty(int facultyNumber){
        isTrue(facultyNumber >= 0, "facultyNumber must be non-negative, was: " + facultyNumber);
        this.facultyNumber = facultyNumber;
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
    }
}
