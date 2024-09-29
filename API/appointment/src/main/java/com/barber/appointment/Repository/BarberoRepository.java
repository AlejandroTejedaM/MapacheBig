package com.barber.appointment.Repository;

import com.barber.appointment.Model.Barbero;
import org.springframework.data.repository.CrudRepository;

public interface BarberoRepository extends CrudRepository<Barbero, Long> {
}
