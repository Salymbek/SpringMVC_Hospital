package peaksoft.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Patient;
import peaksoft.service.*;

@Controller
@RequestMapping("/{id}/patients")
public class PatientController {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;

    public PatientController(PatientService patientService, DoctorService doctorService, AppointmentService appointmentService, DepartmentService departmentService, HospitalService hospitalService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.departmentService = departmentService;
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String getAll(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("hospital", hospitalService.getById(id));
            model.addAttribute("patients", patientService.getAll(id));
            return "patient/patients";
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/new")
    public String createNewPatient(@PathVariable("id") Long id, Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/newPage";
    }

    @PostMapping("/save")
    public String savePatient(@PathVariable("id") Long id,
                              @ModelAttribute("patient") @Valid Patient patient,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "patient/newPage";
        }
        try {
            patientService.save(id, patient);
            return "redirect:/{id}/patients";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("Email", "This email already exists in the database");
            return "patient/newPage";
        }
    }

    @GetMapping("/{patientId}/edit")
    private String getUpdateForm(@PathVariable("patientId") Long patientId,
                                 Model model,
                                 @PathVariable("id") Long id) {
        try {
            model.addAttribute("patient", patientService.getById(patientId));
            return "patient/update";
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @PatchMapping("/{patientId}")
    private String updatePatient(@PathVariable("id") Long id,
                                 @PathVariable("patientId") Long patientId,
                                 @ModelAttribute("patient") @Valid Patient patient,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "patient/update";
        }
        try {
            patientService.update(patientId, patient);
            return "redirect:/{id}/patients";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("Email", "This email already exists in the database");
            return "patient/update";
        }
    }

    @DeleteMapping("/{patientId}/delete")
    public String deletePatient(@PathVariable("id") Long id,
                                @PathVariable("patientId") Long patientId) {
        patientService.delete(patientId);
        return "redirect:/{id}/patients";
    }

    @GetMapping("/{patientId}/appointments")
    public String getAppointments(@PathVariable("id") Long id,
                                  @PathVariable("patientId") Long patientId,
                                  Model model) {
        try {
            model.addAttribute("patient", patientService.getById(patientId));
            model.addAttribute("appointments", patientService.getAppointments(id, patientId));
            return "patient/appointments";
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/{patientId}/newApp")
    public String makeApp(@PathVariable("id") Long id,
                          @PathVariable("patientId") Long patientId,
                          Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorService.getAll(id));
//        model.addAttribute("patients", patientService.getAll(id));
        model.addAttribute("departments", departmentService.getAll(id));
        return "patient/newApp";
    }

    @PostMapping("/{patientId}/saveApp")
    public String saveApp(@PathVariable("id") Long id,
                          @PathVariable("patientId") Long patientId,
                          @ModelAttribute("appointment") @Valid Appointment appointment,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getAll(id));
            model.addAttribute("departments", departmentService.getAll(id));
            return "patient/newApp";
        }
        appointmentService.save(id, appointment);
        return "redirect:/{id}/patients/{patientId}/appointments";
    }

    @DeleteMapping("/{patientId}/{appointmentId}/delete")
    public String deleteApp(@PathVariable("id") Long id,
                            @PathVariable("patientId") Long patientId,
                            @PathVariable("appointmentId") Long appointmentId) {
        appointmentService.delete(id, appointmentId);
        return "redirect:/{id}/patients/{patientId}/appointments";
    }
}
