package com.barber.appointment.Repository;

import com.barber.appointment.Model.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServiceRepository extends CrudRepository<Service, Long> {
    Optional<Service> findByNombre(String nombre);

    Optional<Service> findByPrecio(Double precio);

    Optional<Service> findByDuracion(Integer durationInt);
}
