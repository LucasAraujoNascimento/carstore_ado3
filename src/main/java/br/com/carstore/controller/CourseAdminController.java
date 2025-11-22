package br.com.carstore.controller;

import br.com.carstore.model.CourseDTO;
import br.com.carstore.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class CourseAdminController {

    private final CourseService courseService;

    public CourseAdminController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String showAdminCourses(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("form", new CourseDTO());
        return "admin/courses";
    }

    @PostMapping("/courses")
    public String addCourse(@Valid @ModelAttribute("form") CourseDTO courseForm, BindingResult validation, Model model) {
        if (validation.hasErrors()) {
            model.addAttribute("courses", courseService.findAll());
            return "admin/courses";
        }
        courseService.save(courseForm);
        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/{id}/delete")
    public String removeCourse(@PathVariable String id) {
        courseService.deleteById(id);
        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/{id}/update")
    public String updateCourseDetails(@PathVariable String id,
                                      @RequestParam("title") String title,
                                      @RequestParam("description") String description,
                                      @RequestParam("durationHours") Integer durationHours,
                                      Model model) {
        CourseDTO courseData = new CourseDTO();
        courseData.setTitle(title);
        courseData.setDescription(description);
        courseData.setDurationHours(durationHours);
        courseService.update(id, courseData);
        return "redirect:/admin/courses";
    }
}
