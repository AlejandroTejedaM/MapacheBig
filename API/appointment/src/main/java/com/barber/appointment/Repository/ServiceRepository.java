package com.barber.appointment.Repository;

import com.barber.appointment.Model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
