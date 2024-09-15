package com.barber.appointment.Controller;

import com.barber.appointment.Exceptions.UserAlreadyExistsException;
import com.barber.appointment.Model.User;
import com.barber.appointment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/user")
public class UserController{
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody User newObject, UriComponentsBuilder ucb) {
        try {
            if (userRepository.findByCorreo(newObject.getCorreo()).isPresent()) {
                throw new UserAlreadyExistsException("User with email " + newObject.getCorreo() + " already exists");
            }
            User newUsuario = (User) newObject;
            User savedUsuario = userRepository.save(newUsuario);
            URI uri = ucb
                    .path("api/user")
                    .buildAndExpand(savedUsuario.getUsuarioId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
