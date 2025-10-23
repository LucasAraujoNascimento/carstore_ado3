package br.com.carstore.dao;

import br.com.carstore.entity.CarEntity;
import br.com.carstore.model.CarDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CarJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CarDTO> findAll() {
        List<CarEntity> entityList = entityManager
                .createQuery("SELECT car FROM CarEntity car ORDER BY car.id DESC", CarEntity.class)
                .getResultList();
        return entityList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public void save(CarDTO carDto) {
        CarEntity entity = new CarEntity();
        entity.setName(carDto.getName());
        entity.setColor(carDto.getColor());
        entityManager.persist(entity);
        carDto.setId(entity.getId() != null ? String.valueOf(entity.getId()) : null);
    }

    public void deleteById(String id) {
        Long primaryKey = Long.valueOf(id);
        CarEntity entity = entityManager.find(CarEntity.class, primaryKey);
        if (entity != null) entityManager.remove(entity);
    }

    public void update(String id, CarDTO updatedData) {
        Long primaryKey = Long.valueOf(id);
        CarEntity entity = entityManager.find(CarEntity.class, primaryKey);
        if (entity != null) {
            entity.setName(updatedData.getName());
            entity.setColor(updatedData.getColor());
            entityManager.merge(entity);
        }
    }

    private CarDTO mapToDto(CarEntity entity) {
        CarDTO dto = new CarDTO();
        dto.setId(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        dto.setName(entity.getName());
        dto.setColor(entity.getColor());
        return dto;
    }
}