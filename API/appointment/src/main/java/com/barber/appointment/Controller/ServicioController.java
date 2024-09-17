package com.barber.appointment.Controller;

import com.barber.appointment.Model.Service;
import com.barber.appointment.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service")
public class ServicioController {

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping
    public ResponseEntity<Iterable<Service>> findAll() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }
}
