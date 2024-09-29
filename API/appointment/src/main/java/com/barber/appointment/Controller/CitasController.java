package com.barber.appointment.Controller;

import java.net.URI;
import java.util.Optional;

import com.barber.appointment.Model.*;
import com.barber.appointment.Repository.AppointmentRepository;
import com.barber.appointment.Repository.BarberoRepository;
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

    @Autowired
    private BarberoRepository barberoRepository;

    @GetMapping
    public ResponseEntity<Iterable<Appointment>> findAll() {
        return ResponseEntity.ok(citasRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Appointment newObject, UriComponentsBuilder ucb) {
        System.out.println(newObject.toString());
        Service servicio = serviceRepository.findById(newObject.getServicio().getServicioId()).get();
        User usuario = userRepository.findById(newObject.getUser().getUsuarioId()).get();
        Barbero barbero = barberoRepository.findById(newObject.getBarbero().getBarberoId()).get();
        newObject.setServicio(servicio);
        newObject.setUser(usuario);
        newObject.setBarbero(barbero);
        Appointment savedCita = citasRepository.save(newObject);
        URI uri = ucb
                .path("api/citas/{id}")
                .buildAndExpand(savedCita.getCitaId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Appointment newObject, UriComponentsBuilder ucb) {
        Long idLong = Long.parseLong(id);
        Appointment newAppointment = citasRepository.findById(idLong).get();
        newObject.setCitaId(newAppointment.getCitaId());
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
