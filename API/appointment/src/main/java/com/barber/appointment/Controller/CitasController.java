package com.barber.appointment.Controller;

import java.net.URI;
import java.util.Optional;

import com.barber.appointment.Model.Appointment;
import com.barber.appointment.Model.Service;
import com.barber.appointment.Model.User;
import com.barber.appointment.Repository.AppointmentRepository;
import com.barber.appointment.Repository.ServiceRepository;
import com.barber.appointment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/citas")
public class CitasController {

    @Autowired
    private AppointmentRepository citasRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping
    public ResponseEntity<Iterable<Appointment>> findAll() {
        return ResponseEntity.ok(citasRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Appointment newObject, UriComponentsBuilder ucb) {
        Optional<User> userOptional = userRepository.findById(newObject.getUser().getUsuarioId());
        Optional<Service> serviceOptional = serviceRepository.findById(newObject.getServicio().getServicioId());
        newObject.setUser(userOptional.get());
        newObject.setServicio(serviceOptional.get());
        Appointment savedUsuario = citasRepository.save(newObject);
        URI uri = ucb
                .path("api/citas")
                .buildAndExpand(savedUsuario.getCitaId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Appointment newObject, UriComponentsBuilder ucb) {
        Long idLong = Long.parseLong(id);
        Appointment newAppointment = citasRepository.findById(idLong).get();
        newObject.setCitaId(idLong);
        Appointment savedCita = citasRepository.save(newObject);
        URI uri = ucb
                .path("api/citas")
                .buildAndExpand(savedCita.getCitaId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        if (!citasRepository.existsById(idLong)) {
            return ResponseEntity.notFound().build();
        }
        citasRepository.deleteById(idLong);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        return ResponseEntity.ok(citasRepository.findById(idLong).get());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Iterable<Appointment>> findByUserId(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        User user = userRepository.findById(idLong).get();
        return ResponseEntity.ok(citasRepository.findByUser(user));
    }
}
