package com.barber.appointment.Repository;

import com.barber.appointment.Model.Service;
import com.barber.appointment.Model.Sucursal;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServiceRepository extends CrudRepository<Service, Long> {
    Optional<Iterable<Service>> findAllByNombre(String nombre);

    Optional<Iterable<Service>> findAllByPrecio(Double precio);

    Optional<Iterable<Service>> findAllByDuracion(Integer durationInt);

    Optional<Iterable<Service>> findAllBySucursal(Sucursal sucursal);
}
