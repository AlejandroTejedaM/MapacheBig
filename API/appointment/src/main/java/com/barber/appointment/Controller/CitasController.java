package com.barber.appointment.Controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.barber.appointment.Model.Citas;
import com.barber.appointment.Repository.CitasRepository;

public class CitasController {
    
     @Autowired
    private CitasRepository citasRepository;

    @GetMapping
    public ResponseEntity<Iterable<Citas>> findAll() {
        return ResponseEntity.ok(citasRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Citas newObject, UriComponentsBuilder ucb) {
        try {
            Citas newUsuario = (Citas) newObject;
            Citas savedUsuario = citasRepository.save(newUsuario);
            URI uri = ucb
                    .path("api/user")
                    .buildAndExpand(savedUsuario.getCitaId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
