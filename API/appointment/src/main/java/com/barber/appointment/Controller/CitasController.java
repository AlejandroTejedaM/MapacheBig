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
        try {
            Optional<User> userOptional = userRepository.findById(newObject.getUser().getUsuarioId());
            if (!userOptional.isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Usuario no encontrado");
            }
            Optional<Service> serviceOptional = serviceRepository.findById(newObject.getServicio().getServicioId());
            if (!serviceOptional.isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Servicio no encontrado");
            }
            newObject.setUser(userOptional.get());
            newObject.setServicio(serviceOptional.get());
            Appointment savedUsuario = citasRepository.save(newObject);
            URI uri = ucb
                    .path("api/citas")
                    .buildAndExpand(savedUsuario.getCitaId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "" + newObject);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Appointment newObject, UriComponentsBuilder ucb) {
        try {
            Long idLong = Long.parseLong(id);
            Appointment newAppointment = citasRepository.findById(idLong).get();
            if (newAppointment != null) {
                newObject.setCitaId(idLong);
                Appointment savedCita = citasRepository.save(newObject);
                URI uri = ucb
                        .path("api/citas")
                        .buildAndExpand(savedCita.getCitaId())
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
            if (!citasRepository.existsById(idLong)) {
                return ResponseEntity.notFound().build();
            }
            citasRepository.deleteById(idLong);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            if (!citasRepository.existsById(idLong)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(citasRepository.findById(idLong).get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Iterable<Appointment>> findByUserId(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            User user = userRepository.findById(idLong).get();
            if (user == null) {
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok(citasRepository.findByUser(user));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
