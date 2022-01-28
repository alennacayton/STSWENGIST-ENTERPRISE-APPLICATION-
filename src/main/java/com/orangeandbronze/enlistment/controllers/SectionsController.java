package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import javax.websocket.Session;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping("sections")
@SessionAttributes("admin")
class SectionsController {

    @Autowired
    private SubjectRepository subjectRepo;
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private SectionRepository sectionRepo;

    @ModelAttribute("admin")
    public Admin admin(Integer id) {
        return adminRepo.findById(id).orElseThrow(() -> new NoSuchElementException("no admin found for adminId " + id));
    }

    @GetMapping
    public String showPage(Model model, Integer id) {
        Admin admin = id == null ? (Admin) model.getAttribute("admin") :
                adminRepo.findById(id).orElseThrow(() -> new NoSuchElementException("no admin found for adminId " + id));
        model.addAttribute("admin", admin);
        model.addAttribute("subjects", subjectRepo.findAll());
        model.addAttribute("rooms", roomRepo.findAll());
        model.addAttribute("sections", sectionRepo.findAll());
        return "sections";
    }

    @PostMapping public String createSection(@RequestParam String sectionId, @RequestParam String subjectId, @RequestParam Days days, @RequestParam String start, @RequestParam String end, @RequestParam String roomName, RedirectAttributes redirectAttrs) {

        // Retrieve a Subject from the DB
        Subject subject = subjectRepo.findById(subjectId).orElseThrow(() -> new NoSuchElementException( "no subject found with subjectId " + subjectId));

        // Retrieve a Room from the DB

        Room room = roomRepo.findById(roomName).orElseThrow(() -> new NoSuchElementException( "no room found with roomName " + roomName));
        // Format date for the Period constructor
        DateTimeFormatter dtfPeriod; dtfPeriod = DateTimeFormatter.ofPattern("H:mm");

        // Create Period and Schedule parameters for Section
        Period period = new Period(LocalTime.parse(start, dtfPeriod), LocalTime.parse(end, dtfPeriod));
        Schedule schedule = new Schedule(days, period);

        // Create the Section object and save to DB
        Section section = new Section(sectionId, subject, schedule, room); sectionRepo.save(section);
        return "redirect:sections";

        // return "";
    }

    @ExceptionHandler(EnlistmentException.class)
    public String handleException(RedirectAttributes redirectAttrs, EnlistmentException e) {
        redirectAttrs.addFlashAttribute("sectionExceptionMessage", e.getMessage());
        return "redirect:sections";
    }

    void setSubjectRepo(SubjectRepository subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    void setSectionRepo(SectionRepository sectionRepo) {
        this.sectionRepo = sectionRepo;
    }

    void setRoomRepo(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    void setAdminRepo(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

}
