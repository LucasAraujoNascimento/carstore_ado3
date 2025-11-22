package br.com.carstore.controller;

import br.com.carstore.model.CourseDTO;
import br.com.carstore.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CoursePublicController {

    private final CourseService courseService;

    public CoursePublicController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String listPublicCourses(Model model, @RequestParam(name = "q", required = false) String q) {
        List<CourseDTO> courseList = courseService.findAll();
        if (q != null && !q.isBlank()) {
            String normalizedQuery = q.toLowerCase();
            courseList = courseList.stream()
                    .filter(item -> (item.getTitle() + " " + item.getDescription()).toLowerCase().contains(normalizedQuery))
                    .toList();
            model.addAttribute("q", q);
        }
        model.addAttribute("courses", courseList);
        return "public/courses";
    }
}
