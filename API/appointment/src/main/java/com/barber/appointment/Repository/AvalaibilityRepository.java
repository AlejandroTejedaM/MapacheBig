package com.barber.appointment.Repository;

import com.barber.appointment.Model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvalaibilityRepository extends JpaRepository<Availability, Long> {
}
