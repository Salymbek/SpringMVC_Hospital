package peaksoft.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Doctor;
import peaksoft.service.DoctorService;
import peaksoft.service.HospitalService;

@Controller
@RequestMapping("/{id}/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    private final HospitalService hospitalService;

    public DoctorController(DoctorService doctorService, HospitalService hospitalService) {
        this.doctorService = doctorService;
        this.hospitalService = hospitalService;
    }
    @GetMapping()
    public String getAll(@PathVariable("id") Long id, Model model){
        try {
            model.addAttribute("hospital", hospitalService.getById(id));
            model.addAttribute("doctors", doctorService.getAll(id));
            return "doctor/doctors";
        }catch (NotFoundException e){
            return e.getMessage();
        }
    }

    @GetMapping("/new")
    public String createNewDoctor(@PathVariable("id") Long id, Model model){
        model.addAttribute("doctor", new Doctor());
        return "doctor/newPage";
    }
    @PostMapping("/save")
    public String save(@PathVariable("id") Long id,
                       @ModelAttribute("doctor")@Valid Doctor doctor,
                       BindingResult bindingResult,
                       Model model){
        if (bindingResult.hasErrors()) {
            return "doctor/newPage";
        }
        try {
            doctorService.save(id, doctor);
            return "redirect:/{id}/doctors";
        }catch (DataIntegrityViolationException e){
            model.addAttribute("Email", "This email already exists in the database");
            return "doctor/newPage";
        }
    }
    @GetMapping("/{doctorId}/edit")
    public String getUpdateFrom(@PathVariable("id") Long id,
                                @PathVariable("doctorId") Long doctorId,
                                Model model){
        try {
            model.addAttribute("doctor", doctorService.findById(doctorId));
            return "doctor/update";
        } catch (NotFoundException e){
            return e.getMessage();
        }
    }
    @PatchMapping("/{doctorId}/update")
    public String update(@PathVariable("id") Long id,
                         @PathVariable("doctorId") Long doctorId,
                         @ModelAttribute("doctor")@Valid Doctor doctor,
                         BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "doctor/update";
        }
        try {
            doctorService.update(doctorId, doctor);
            return "redirect:/{id}/doctors";
        } catch (DataIntegrityViolationException e){
            model.addAttribute("Email", "This email already exists in the database");
            return "doctor/update";
        }
    }
    @DeleteMapping("/{doctorId}/delete")
    public String delete(@PathVariable("id") Long id,
                         @PathVariable("doctorId") Long doctorId){
        doctorService.delete(doctorId);
        return "redirect:/{id}/doctors";
    }
    @GetMapping("/{doctorId}/departments")
    public String getDepartments(@PathVariable("id") Long id,
                                 @PathVariable("doctorId") Long doctorId,
                                 Model model){
        try {
            model.addAttribute("doctor", doctorService.findById(doctorId));
            model.addAttribute("departments", doctorService.getDepartments(doctorId));
            return "doctor/departments";
        } catch (NotFoundException e){
            return e.getMessage();
        }
    }
    @GetMapping("/{doctorId}/newDepartment")
    public String assignToDepartment(@PathVariable("id") Long id,
                                     @PathVariable("doctorId") Long doctorId,
                                     Model model){
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", doctorService.getCanBeAssignDepartments(doctorId));
        return "doctor/assignToDepartment";
    }
    @PatchMapping("/{doctorId}/saveDepartment")
    public String saveDepartment(@PathVariable("id") Long id,
                                 @PathVariable("doctorId") Long doctorId,
                                 @ModelAttribute("doctor") Doctor doctor){
        doctorService.assignToDepartment(doctorId, doctor);
        return "redirect:/{id}/doctors/{doctorId}/departments";
    }
    @DeleteMapping("/{doctorId}/{departmentId}/delete")
    public String deleteDepartment(@PathVariable("id") Long id,
                                   @PathVariable("doctorId") Long doctorId,
                                   @PathVariable("departmentId") Long departmentId){
        doctorService.deleteDepartment(doctorId, departmentId);
        return "redirect:/{id}/doctors/{doctorId}/departments";
    }
    @GetMapping("/{doctorId}/appointments")
    public String getAppointments(@PathVariable("id") Long id,
                                  @PathVariable("doctorId") Long doctorId,
                                  Model model){
        try {
            model.addAttribute("doctor", doctorService.findById(doctorId));
            model.addAttribute("appointments", doctorService.getAppointments(doctorId));
            return "doctor/appointments";
        } catch (NotFoundException e){
            return e.getMessage();
        }
    }
}
