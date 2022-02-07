package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.orangeandbronze.enlistment.domain.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SectionsControllerTest {

    @Test
    void createSection_save_new_section_to_repository() {
        String sectionId = "X";
        String roomName = "X";
        String subjectId = "X";
        Days days = Days.MTH;
        String startTime = "14:30";
        String endTime = "16:00";
        Room room = new Room(roomName, 40);

        // When create section (post) method is called, use non-production repo to see if controller actually calls it
        AdminRepository adminRepository = mock(AdminRepository.class);
        SectionRepository sectionRepository = mock(SectionRepository.class);
        SubjectRepository subjectRepository = mock(SubjectRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        FacultyRepository facultyRepository = mock(FacultyRepository.class);

        when(facultyRepository.findById(DEFAULT_FACULTY_NUMBER)).thenReturn(Optional.of(DEFAULT_FACULTY));

        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Manually set the repos
        SectionsController controller = new SectionsController();
        controller.setAdminRepo(adminRepository);
        controller.setSectionRepo(sectionRepository);
        controller.setSubjectRepo(subjectRepository);
        controller.setRoomRepo(roomRepository);
        controller.setFacultyRepo(facultyRepository);

        EntityManager entityManager = mock(EntityManager.class);
        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);
        controller.setEntityManager(entityManager);

        controller.createSection(DEFAULT_SECTION_ID, DEFAULT_SUBJECT_ID, Days.MTH, "08:30", "10:00", roomName,
                DEFAULT_FACULTY_NUMBER, redirectAttributes);

        ArgumentCaptor<Section> sectionArgumentCaptor = ArgumentCaptor.forClass(Section.class);
        assertAll(
                () -> verify(subjectRepository).findById(DEFAULT_SUBJECT_ID),
                () -> verify(roomRepository).findById(roomName),
                () -> verify(sectionRepository).save(sectionArgumentCaptor.capture()),
                () -> assertEquals(DEFAULT_SECTION_ID, sectionArgumentCaptor.getValue().getSectionId())
        );

    }
}
