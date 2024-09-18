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
        try {
            Long idLong = Long.parseLong(id);
            Optional<Service> serviceOptional = serviceRepository.findById(idLong);
            if (serviceOptional.isPresent()) {
                return ResponseEntity.ok(serviceOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/nombre/{name}")
    public ResponseEntity<Iterable<Service>> findAllByName(@PathVariable String name) {
        try {
            Optional<Iterable<Service>> serviceOptional = serviceRepository.findAllByNombre(name);
            if (serviceOptional.isPresent()) {
                return ResponseEntity.ok(serviceOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/precio/{price}")
    public ResponseEntity<Iterable<Service>> findAllByPrice(@PathVariable String price) {
        try {
            Double priceDouble = Double.parseDouble(price);
            Optional<Iterable<Service>> serviceOptional = serviceRepository.findAllByPrecio(priceDouble);
            if (serviceOptional.isPresent()) {
                return ResponseEntity.ok(serviceOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/duracion/{duration}")
    public ResponseEntity<Iterable<Service>> findAllByDuration(@PathVariable String duration) {
        try {
            Integer durationInt = Integer.parseInt(duration);
            Optional<Iterable<Service>> serviceOptional = serviceRepository.findAllByDuracion(durationInt);
            if (serviceOptional.isPresent()) {
                return ResponseEntity.ok(serviceOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Service newObject, UriComponentsBuilder ucb) {
        try {
            Service savedService = serviceRepository.save(newObject);
            URI uri = ucb
                    .path("api/servicio")
                    .buildAndExpand(savedService.getServicioId())
                    .toUri();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Service newObject, UriComponentsBuilder ucb) {
        try {
            Long idLong = Long.parseLong(id);
            Service newService = serviceRepository.findById(idLong).get();
            if (newService != null) {
                newObject.setServicioId(idLong);
                Service savedService = serviceRepository.save(newObject);
                URI uri = ucb
                        .path("api/servicio")
                        .buildAndExpand(savedService.getServicioId())
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
            if (!serviceRepository.existsById(idLong)) {
                return ResponseEntity.notFound().build();
            }
            serviceRepository.deleteById(idLong);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
