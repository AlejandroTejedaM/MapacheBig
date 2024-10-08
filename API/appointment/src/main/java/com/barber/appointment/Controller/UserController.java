package com.barber.appointment.Controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.barber.appointment.Model.User;
import com.barber.appointment.Repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        return userRepository.findById(idLong).map(ResponseEntity::ok).get();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody User newObject, UriComponentsBuilder ucb) {
        User savedUsuario = userRepository.save(newObject);
        URI uri = ucb
                .path("api/usuario/{id}")
                .buildAndExpand(savedUsuario.getUsuarioId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id,@RequestBody User newObject, UriComponentsBuilder ucb) {
        Long idFloat = Long.parseLong(id);
        User findUser = userRepository.findById(idFloat).get();
        newObject.setUsuarioId(findUser.getUsuarioId());
        User savedUsuario = userRepository.save(newObject);
        URI uri = ucb
                .path("api/usuario/{id}")
                .buildAndExpand(savedUsuario.getUsuarioId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        userRepository.deleteById(idLong);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        Optional<User> userOptional = userRepository.findByCorreo(loginUser.getCorreo());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getContrasennia().equals(loginUser.getContrasennia())) {
                // Authentication successful
                return ResponseEntity.ok("Login successful");
            }
        }
        return ResponseEntity.badRequest().body("Login failed");
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<User> findByCorreo(@PathVariable String correo) {
        return userRepository.findByCorreo(correo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}