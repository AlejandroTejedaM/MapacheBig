package com.barber.appointment.Controller;

import com.barber.appointment.Model.Availability;
import com.barber.appointment.Model.User;
import com.barber.appointment.Repository.AvailabilityRepository;
import com.barber.appointment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/disponibilidad")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Availability>> findAll() {
        return ResponseEntity.ok(availabilityRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Availability> findById(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            return availabilityRepository.findById(idLong)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<Optional<Iterable<Availability>>> findByBarberoId(@PathVariable String barberoId) {
        try {
            Long idLong = Long.parseLong(barberoId);
            Optional<User> userOptional = userRepository.findById(idLong);
            if (userOptional.isPresent() && userOptional.get().getTipo().equals("BARBERO")) {
                return ResponseEntity.ok(availabilityRepository.findByUser_UsuarioId(idLong));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Availability newObject, UriComponentsBuilder ucb) {
        try {
            if (newObject.getUser() == null || newObject.getUser().getUsuarioId() == null) {
                return ResponseEntity.badRequest().body("User information is missing");
            }

            Optional<User> userOptional = userRepository.findById(newObject.getUser().getUsuarioId());
            if (userOptional.isPresent()) {
                newObject.setUser(userOptional.get());
                Availability savedAvailability = availabilityRepository.save(newObject);
                URI uri = ucb
                        .path("/api/disponibilidad/{id}")
                        .buildAndExpand(savedAvailability.getDisponibilidadId())
                        .toUri();
                return ResponseEntity.created(uri).body("Availability created successfully");
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Availability newObject, UriComponentsBuilder ucb) {
        try {
            Long idLong = Long.parseLong(id);

            Availability newAvailability = availabilityRepository.findById(idLong).get();
            if (newAvailability != null) {
                newObject.setDisponibilidadId(idLong);
                Availability savedAvailability = availabilityRepository.save(newObject);
                URI uri = ucb
                        .path("api/disponibilidad/{id}")
                        .buildAndExpand(savedAvailability.getDisponibilidadId())
                        .toUri();
                return ResponseEntity.created(uri).build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            availabilityRepository.deleteById(idLong);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
