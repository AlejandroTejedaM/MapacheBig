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

    @GetMapping()
    public ResponseEntity<Iterable<Availability>> findAll() {
        return ResponseEntity.ok(availabilityRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Availability> findById(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        return availabilityRepository.findById(idLong)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<Optional<Iterable<Availability>>> findByBarberoId(@PathVariable String barberoId) {
        Long idLong = Long.parseLong(barberoId);
        return ResponseEntity.ok(availabilityRepository.findByUser_UsuarioId(idLong));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Availability newObject, UriComponentsBuilder ucb) {
        Availability savedAvailability = availabilityRepository.save(newObject);
        URI uri = ucb
                .path("/api/disponibilidad/{id}")
                .buildAndExpand(savedAvailability.getDisponibilidadId())
                .toUri();
        return ResponseEntity.created(uri).body("Availability created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Availability newObject, UriComponentsBuilder ucb) {
        Long idLong = Long.parseLong(id);
        Availability newAvailability = availabilityRepository.findById(idLong).get();
        newObject.setDisponibilidadId(newAvailability.getDisponibilidadId());
        Availability savedAvailability = availabilityRepository.save(newObject);
        URI uri = ucb
                .path("api/disponibilidad/{id}")
                .buildAndExpand(savedAvailability.getDisponibilidadId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        availabilityRepository.deleteById(idLong);
        return ResponseEntity.ok().build();
    }
}
