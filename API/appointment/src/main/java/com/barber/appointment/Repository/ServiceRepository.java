package com.barber.appointment.Repository;

import com.barber.appointment.Model.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServiceRepository extends CrudRepository<Service, Long> {
}
