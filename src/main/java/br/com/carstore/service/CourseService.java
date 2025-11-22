package br.com.carstore.service;

import br.com.carstore.model.CourseDTO;
import java.util.List;

public interface CourseService {
    List<CourseDTO> findAll();
    void save(CourseDTO courseDTO);
    void deleteById(String id);
    void update(String id, CourseDTO courseDTO);
}
