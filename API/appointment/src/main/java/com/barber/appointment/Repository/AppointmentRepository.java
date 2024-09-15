package com.barber.appointment.Repository;

import com.barber.appointment.Model.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
