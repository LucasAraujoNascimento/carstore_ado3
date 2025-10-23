package br.com.carstore.controller;

import br.com.carstore.model.CarDTO;
import br.com.carstore.service.CarService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CarService carCatalogService;

    public AdminController(CarService carCatalogService) {
        this.carCatalogService = carCatalogService;
    }

    @GetMapping("/cars")
    public String showAdminCars(Model model) {
        model.addAttribute("cars", carCatalogService.findAll());
        model.addAttribute("form", new CarDTO());
        return "admin/dashboard";
    }

    @PostMapping("/cars")
    public String addCar(@Valid @ModelAttribute("form") CarDTO carForm, BindingResult validation, Model model) {
        if (validation.hasErrors()) {
            model.addAttribute("cars", carCatalogService.findAll());
            return "admin/dashboard";
        }
        carCatalogService.save(carForm);
        return "redirect:/admin/cars";
    }

    @PostMapping("/cars/{id}/delete")
    public String removeCar(@PathVariable String id) {
        carCatalogService.deleteById(id);
        return "redirect:/admin/cars";
    }

    @PostMapping("/cars/{id}/update")
    public String updateCarDetails(@PathVariable String id,
                                   @RequestParam("name") String modelName,
                                   @RequestParam("color") String paintColor,
                                   Model model) {
        CarDTO carData = new CarDTO();
        carData.setName(modelName);
        carData.setColor(paintColor);
        carCatalogService.update(id, carData);
        return "redirect:/admin/cars";
    }
}