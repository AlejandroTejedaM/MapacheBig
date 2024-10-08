package com.barber.appointment.Controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.barber.appointment.Model.Service;
import com.barber.appointment.Model.Sucursal;
import com.barber.appointment.Repository.ServiceRepository;
import com.barber.appointment.Repository.SucursalRepository;

@RestController
@RequestMapping("/api/servicio")
@CrossOrigin(origins = "*")
public class ServicioController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

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

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<Iterable<Service>> findBySucursalId(@PathVariable String sucursalId) {
        Long idSucursalParsed = Long.parseLong(sucursalId);
        Sucursal sucursal = sucursalRepository.findById(idSucursalParsed).get();
        return ResponseEntity.ok(serviceRepository.findAllBySucursal(sucursal).get());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Service newObject, UriComponentsBuilder ucb) {
        Sucursal sucursal = sucursalRepository.findById(newObject.getSucursal().getSucursalId()).get();
        newObject.setSucursal(sucursal);
        Service savedService = serviceRepository.save(newObject);
        URI uri = ucb
                .path("api/servicio/{id}")
                .buildAndExpand(savedService.getServicioId())
                .toUri();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Service newObject, UriComponentsBuilder ucb) {
        Long idLong = Long.parseLong(id);
        Service newService = serviceRepository.findById(idLong).get();
        newObject.setServicioId(newService.getServicioId());
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
