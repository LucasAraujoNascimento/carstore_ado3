package br.com.carstore.controller;

import br.com.carstore.model.CarDTO;
import br.com.carstore.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PublicController {

    private final CarService carCatalogService;

    public PublicController(CarService carCatalogService) {
        this.carCatalogService = carCatalogService;
    }

    @GetMapping("/")
    public String landingPage(Model model, @RequestParam(name = "q", required = false) String q) {
        List<CarDTO> carList = carCatalogService.findAll();
        if (q != null && !q.isBlank()) {
            String normalizedQuery = q.toLowerCase();
            carList = carList.stream()
                    .filter(item -> (item.getName() + " " + item.getColor()).toLowerCase().contains(normalizedQuery))
                    .toList();
            model.addAttribute("q", q);
        }
        model.addAttribute("cars", carList);
        return "public/index";
    }
}