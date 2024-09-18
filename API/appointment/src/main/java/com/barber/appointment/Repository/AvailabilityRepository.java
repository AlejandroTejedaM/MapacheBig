package com.barber.appointment.Repository;

import com.barber.appointment.Model.Availability;
import com.barber.appointment.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AvailabilityRepository extends CrudRepository<Availability, Long> {
    Optional<Iterable<Availability>> findByUser_UsuarioId(Long usuarioId);
}
