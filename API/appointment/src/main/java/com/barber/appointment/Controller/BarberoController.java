package com.barber.appointment.Controller;

import com.barber.appointment.Model.Barbero;
import com.barber.appointment.Model.Sucursal;
import com.barber.appointment.Repository.BarberoRepository;
import com.barber.appointment.Repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/barbero")
@CrossOrigin(origins = "*")
public class BarberoController {

    @Autowired
    private BarberoRepository barberoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

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

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<Iterable<Barbero>> findBySucursalId(@PathVariable String sucursalId) {
        Long idSucursalParsed = Long.parseLong(sucursalId);
        Sucursal sucursal = sucursalRepository.findById(idSucursalParsed).get();
        return ResponseEntity.ok(barberoRepository.findAllBySucursal(sucursal).get());
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
