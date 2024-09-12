package com.barber.appointment.Repository;

import com.barber.appointment.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}

