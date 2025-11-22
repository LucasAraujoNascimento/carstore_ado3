package br.com.carstore.service;

import br.com.carstore.dao.CourseRepository;
import br.com.carstore.entity.CourseEntity;
import br.com.carstore.model.CourseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(CourseDTO courseDTO) {
        CourseEntity entity = new CourseEntity();
        entity.setTitle(courseDTO.getTitle());
        entity.setDescription(courseDTO.getDescription());
        entity.setDurationHours(courseDTO.getDurationHours());
        courseRepository.save(entity);
        courseDTO.setId(entity.getId() != null ? String.valueOf(entity.getId()) : null);
    }

    @Override
    public void deleteById(String id) {
        Long primaryKey = Long.valueOf(id);
        courseRepository.deleteById(primaryKey);
    }

    @Override
    public void update(String id, CourseDTO courseDTO) {
        Long primaryKey = Long.valueOf(id);
        courseRepository.findById(primaryKey).ifPresent(entity -> {
            entity.setTitle(courseDTO.getTitle());
            entity.setDescription(courseDTO.getDescription());
            entity.setDurationHours(courseDTO.getDurationHours());
            courseRepository.save(entity);
        });
    }

    private CourseDTO mapToDto(CourseEntity entity) {
        CourseDTO dto = new CourseDTO();
        dto.setId(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setDurationHours(entity.getDurationHours());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
