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
@RequestMapping("/api/usuario")
public class UserController{
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            return userRepository.findById(idLong)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
                    .path("api/usuario")
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

    @PutMapping
    public ResponseEntity<String> update(@RequestBody User newObject, UriComponentsBuilder ucb) {
        try {
            User newUsuario = (User) newObject;
            User savedUsuario = userRepository.save(newUsuario);
            URI uri = ucb
                    .path("api/usuario")
                    .buildAndExpand(savedUsuario.getUsuarioId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            userRepository.deleteById(idLong);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}