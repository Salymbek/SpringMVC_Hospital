package peaksoft.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.service.*;

@Controller
@RequestMapping("/{id}/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;

    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService, DepartmentService departmentService, HospitalService hospitalService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.departmentService = departmentService;
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String getAll(@PathVariable("id")Long id, Model model){
        model.addAttribute("hospital", hospitalService.getById(id));
        model.addAttribute("appointments", appointmentService.getAll(id));
        return "appointment/appointments";
    }

    @GetMapping("/new")
    public String create(@PathVariable("id")Long id, Model model){
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorService.getAll(id));
        model.addAttribute("patients", patientService.getAll(id));
        model.addAttribute("departments", departmentService.getAll(id));
        return "appointment/newPage";
    }

    @PostMapping("/save")
    public String save(@PathVariable("id") Long id,
                       @ModelAttribute("appointment")@Valid Appointment appointment,
                       BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("doctors", doctorService.getAll(id));
            model.addAttribute("patients", patientService.getAll(id));
            model.addAttribute("departments", departmentService.getAll(id));
            return "appointment/newPage";
        }
        appointmentService.save(id, appointment);
        return "redirect:/{id}/appointments";
    }
    @GetMapping("/{appointmentId}/edit")
    public String getUpdateForm(@PathVariable("id") Long id,
                                @PathVariable("appointmentId") Long appointmentId,
                                Model model){
        try {
            model.addAttribute("appointment", appointmentService.findById(appointmentId));
            model.addAttribute("doctors", doctorService.getAll(id));
            model.addAttribute("patients", patientService.getAll(id));
            model.addAttribute("departments", departmentService.getAll(id));
        } catch (NotFoundException e){
            return e.getMessage();
        }
        return "appointment/update";
    }
    @PatchMapping("/{appointmentId}/update")
    public String update(@PathVariable("id") Long id,
                         @PathVariable("appointmentId") Long appointmentId,
                         @ModelAttribute("appointment")@Valid Appointment appointment,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "appointment/update";
        }
        appointmentService.update(appointmentId, appointment);
        return "redirect:/{id}/appointments";
    }

    @DeleteMapping("/{appointmentId}/delete")
    public String delete(@PathVariable("id") Long id,
                         @PathVariable("appointmentId") Long appointmentId){
        appointmentService.delete(id, appointmentId);
        return "redirect:/{id}/appointments";
    }

}
