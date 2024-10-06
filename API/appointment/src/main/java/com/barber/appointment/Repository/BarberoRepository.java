package com.barber.appointment.Repository;

import com.barber.appointment.Model.Barbero;
import com.barber.appointment.Model.Service;
import com.barber.appointment.Model.Sucursal;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BarberoRepository extends CrudRepository<Barbero, Long> {
    Optional<Iterable<Barbero>> findAllBySucursal(Sucursal sucursal);
}
