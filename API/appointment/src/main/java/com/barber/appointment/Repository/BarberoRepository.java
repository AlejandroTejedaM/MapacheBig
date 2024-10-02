package com.barber.appointment.Repository;

import com.barber.appointment.Model.Barbero;
import com.barber.appointment.Model.Sucursal;
import org.springframework.data.repository.CrudRepository;

public interface BarberoRepository extends CrudRepository<Barbero, Long> {

    Iterable<Barbero> findAllBySucursal(Sucursal sucursal);
}
