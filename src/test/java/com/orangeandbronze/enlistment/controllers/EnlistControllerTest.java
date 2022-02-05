package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static com.orangeandbronze.enlistment.domain.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnlistControllerTest {

    @Test
    void enlistOrCancel_enlist_student_in_section() {
        Student student = new Student(1, "X", "X");
        String sectionId = "X";
        UserAction userAction = UserAction.ENLIST;

        // when enlist (post) method is called
        SectionRepository sectionRepository = mock(SectionRepository.class);
        Section section = new Section(sectionId, new Subject("X"), MTH830to10, new Room("X", 10), newFaculty(1));
        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(section));
        StudentRepository studentRepository = mock(StudentRepository.class);
        EnlistController controller = new EnlistController();
        controller.setSectionRepo(sectionRepository);
        controller.setStudentRepo(studentRepository);
        String returnVal = controller.enlistOrCancel(student, sectionId, userAction);
        // Retrieve the Section object from the DB using sectionId
        verify(sectionRepository).findById(sectionId);
        // student.enlist method will be called, passing in the section
        verify(student).enlist(section);
        // save student to DB
        verify(studentRepository).save(student);
        // save section to DB
        verify(sectionRepository).save(section);
        assertEquals("redirect:enlist", returnVal);
    }

}
