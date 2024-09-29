package com.barber.appointment.Controller;

import com.barber.appointment.Model.Service;
import com.barber.appointment.Model.User;
import com.barber.appointment.Repository.ServiceRepository;
import com.barber.appointment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicio")
public class ServicioController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Iterable<Service>> findAll() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> findById(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        Optional<Service> serviceOptional = serviceRepository.findById(idLong);
        return ResponseEntity.ok(serviceOptional.get());
    }

    @GetMapping("/nombre/{name}")
    public ResponseEntity<Iterable<Service>> findAllByName(@PathVariable String name) {
        Optional<Iterable<Service>> serviceOptional = serviceRepository.findAllByNombre(name);
        return ResponseEntity.ok(serviceOptional.get());
    }

    @GetMapping("/precio/{price}")
    public ResponseEntity<Iterable<Service>> findAllByPrice(@PathVariable String price) {
        Double priceDouble = Double.parseDouble(price);
        Optional<Iterable<Service>> serviceOptional = serviceRepository.findAllByPrecio(priceDouble);
        return ResponseEntity.ok(serviceOptional.get());
    }

    @GetMapping("/duracion/{duration}")
    public ResponseEntity<Iterable<Service>> findAllByDuration(@PathVariable String duration) {
        Integer durationInt = Integer.parseInt(duration);
        Optional<Iterable<Service>> serviceOptional = serviceRepository.findAllByDuracion(durationInt);
        return ResponseEntity.ok(serviceOptional.get());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Service newObject, UriComponentsBuilder ucb) {
        Service savedService = serviceRepository.save(newObject);
        URI uri = ucb
                .path("api/servicio")
                .buildAndExpand(savedService.getServicioId())
                .toUri();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Service newObject, UriComponentsBuilder ucb) {
        Long idLong = Long.parseLong(id);
        Service newService = serviceRepository.findById(idLong).get();
        newObject.setServicioId(idLong);
        Service savedService = serviceRepository.save(newObject);
        URI uri = ucb
                .path("api/servicio")
                .buildAndExpand(savedService.getServicioId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        serviceRepository.deleteById(idLong);
        return ResponseEntity.ok().build();
    }


}
