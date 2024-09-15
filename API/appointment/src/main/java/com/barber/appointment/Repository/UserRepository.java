package com.barber.appointment.Repository;

import com.barber.appointment.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByCorreo(String correo);
    Optional<User> findByCorreoAndContrasennia(String correo, String contrasennia);
}