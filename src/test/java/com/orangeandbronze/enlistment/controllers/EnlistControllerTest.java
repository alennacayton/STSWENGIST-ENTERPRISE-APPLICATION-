package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.orangeandbronze.enlistment.domain.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnlistControllerTest {

    @Test
    void enlistOrCancel_enlist_student_in_section() {
        Student student = mock(Student.class);
        UserAction userAction = UserAction.ENLIST;

        // when enlist (post) method is called
        SectionRepository sectionRepository = mock(SectionRepository.class);
        Section section = newDefaultSection();
        when(sectionRepository.findById(DEFAULT_SECTION_ID)).thenReturn(Optional.of(section));
        StudentRepository studentRepository = mock(StudentRepository.class);
        EnlistController controller = new EnlistController();
        controller.setSectionRepo(sectionRepository);
        controller.setStudentRepo(studentRepository);

        EntityManager entityManager = mock(EntityManager.class);
        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);
        controller.setEntityManager(entityManager);

        String returnVal = controller.enlistOrCancel(student, DEFAULT_SECTION_ID, userAction);
        // Retrieve the Section object from the DB using sectionId
        verify(sectionRepository).findById(DEFAULT_SECTION_ID);
        // student.enlist method will be called, passing in the section
        verify(student).enlist(section);
        // save student to DB
        verify(studentRepository).save(student);
        // save section to DB
        verify(sectionRepository).save(section);
        assertEquals("redirect:enlist", returnVal);
    }

}
