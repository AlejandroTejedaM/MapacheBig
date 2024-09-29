package com.barber.appointment.Controller;

import com.barber.appointment.Model.Sucursal;
import com.barber.appointment.Repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/sucursal")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;

    @GetMapping
    public ResponseEntity<Iterable<Sucursal>> findAll() {
        return ResponseEntity.ok(sucursalRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> findById(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        return sucursalRepository.findById(idLong)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Sucursal newObject, UriComponentsBuilder ucb) {
        Sucursal savedSucursal = sucursalRepository.save(newObject);
        URI uri = ucb
                .path("/api/sucursal/{id}")
                .buildAndExpand(savedSucursal.getSucursalId())
                .toUri();
        return ResponseEntity.created(uri).body("Sucursal created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Sucursal newObject, UriComponentsBuilder ucb) {
        Long idLong = Long.parseLong(id);
        Sucursal newSucursal = sucursalRepository.findById(idLong).get();
        newObject.setSucursalId(newSucursal.getSucursalId());
        Sucursal savedSucursal = sucursalRepository.save(newObject);
        URI uri = ucb
                .path("/api/sucursal/{id}")
                .buildAndExpand(savedSucursal.getSucursalId())
                .toUri();
        return ResponseEntity.created(uri).body("Sucursal updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        sucursalRepository.deleteById(idLong);
        return ResponseEntity.ok().body("Sucursal deleted successfully");
    }
}
