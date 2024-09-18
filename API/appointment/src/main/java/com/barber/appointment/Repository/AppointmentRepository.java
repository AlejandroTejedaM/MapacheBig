package com.barber.appointment.Repository;

import com.barber.appointment.Model.Appointment;
import com.barber.appointment.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    Optional<Appointment> findByCitaId(Long citaId);

    Iterable<Appointment> findByUser(User user);
}
