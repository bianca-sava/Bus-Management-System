package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.model.Role;
import com.example.busmanagementsystem.service.DutyAssignmentsService;
import com.example.busmanagementsystem.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/duty-assignment")
public class DutyAssignmentController {

    private final DutyAssignmentsService dutyAssignmentService;

    @Autowired
    public DutyAssignmentController(DutyAssignmentsService dutyAssignmentService) {
        this.dutyAssignmentService = dutyAssignmentService;
    }

    @GetMapping
    public String getAllDutyAssignments(Model model) {
        model.addAttribute("dutyAssignments", dutyAssignmentService.getAllAssignments().values());
        return "dutyAssignment/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("dutyAssignment", new DutyAssignment());

        model.addAttribute("roles", Role.values());

        return "dutyAssignment/form";
    }


    @PostMapping
    public String createDutyAssignment(@RequestParam String id,
                                       @RequestParam String tripId,
                                       @RequestParam String staffId,
                                       @RequestParam Role role) {

        dutyAssignmentService.addAssignment(new DutyAssignment(id, tripId, staffId, role));
        return "redirect:/duty-assignment";
    }

    @PostMapping("/{id}/delete")
    public String deleteDutyAssignment(@PathVariable String id) {
        dutyAssignmentService.deleteAssignment(id);
        return "redirect:/duty-assignment";
    }
}