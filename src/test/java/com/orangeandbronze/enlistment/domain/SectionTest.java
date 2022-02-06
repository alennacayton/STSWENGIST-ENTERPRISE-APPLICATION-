package com.orangeandbronze.enlistment.domain;

import org.junit.jupiter.api.*;

import static com.orangeandbronze.enlistment.domain.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class SectionTest {

    @Test
    void newSection_same_room_diff_sked() {
        Room room = new Room("X", 10);
        new Section("A", DEFAULT_SUBJECT, MTH830to10, room, newFaculty(1));
        assertDoesNotThrow(() -> new Section("B", DEFAULT_SUBJECT, TF10to1130, room, newFaculty(2 )));
    }

    @Test
    void newSection_same_room_same_sked() {
        Room room = new Room("X", 10);
        new Section("A", DEFAULT_SUBJECT, MTH830to10, room, newFaculty(1));
        assertThrows(ScheduleConflictException.class, () -> new Section("B", DEFAULT_SUBJECT, MTH830to10, room, newFaculty(2)));
    }


    @Test
    void newSection_same_faculty_diff_sched(){

        Room room = new Room("X", 10);
        new Section("A", DEFAULT_SUBJECT, MTH830to10, room, DEFAULT_FACULTY);
        assertDoesNotThrow(() -> new Section("B", DEFAULT_SUBJECT, TF830to10, room, DEFAULT_FACULTY));
    }

    @Test
    void newSection_same_faculty_conflict_sched(){
        Room room = new Room("X", 10);
        new Section("A", DEFAULT_SUBJECT, MTH830to10, room, DEFAULT_FACULTY);
        assertThrows(ScheduleConflictException.class, () -> new Section("B", DEFAULT_SUBJECT, MTH9to11, room, DEFAULT_FACULTY));
    }


}
