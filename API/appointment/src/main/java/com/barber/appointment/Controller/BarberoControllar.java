package com.barber.appointment.Controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.barber.appointment.Model.Barbero;
import com.barber.appointment.Repository.BarberoRepository;

@RestController
@RequestMapping("/api/barbero")

public class BarberoControllar {

    @Autowired
    private BarberoRepository barberoRepository;

    @GetMapping
    public ResponseEntity<Iterable<Barbero>> findAll() {
        return ResponseEntity.ok(barberoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barbero> findById(@PathVariable String id){
        Long idLong = Long.parseLong(id);
        return barberoRepository.findById(idLong)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Barbero newObject, UriComponentsBuilder ucb) {
        Barbero savedBarbero = barberoRepository.save(newObject);
        URI uri = ucb
                .path("/api/barbero/{id}")
                .buildAndExpand(savedBarbero.getBarberoId())
                .toUri();
        return ResponseEntity.created(uri).body("Barbero created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Barbero newObject, UriComponentsBuilder ucb) {
        Long idLong = Long.parseLong(id);
        Barbero newBarbero = barberoRepository.findById(idLong).get();
        newObject.setBarberoId(newBarbero.getBarberoId());
        Barbero savedBarbero = barberoRepository.save(newObject);
        URI uri = ucb
                .path("/api/barbero/{id}")
                .buildAndExpand(savedBarbero.getBarberoId())
                .toUri();
        return ResponseEntity.created(uri).body("Barbero updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        barberoRepository.deleteById(idLong);
        return ResponseEntity.ok().body("Barbero deleted successfully");
    }

}
