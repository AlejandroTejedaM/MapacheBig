package com.barber.appointment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
@JsonIgnoreProperties({"listaCitas"})
public class Service {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long servicioId;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Double precio;
    @Column(nullable = false)
    private Integer duracion;
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    private List<Appointment> listaCitas = new ArrayList<>();

    public Service() {
    }

    public Service(String nombre, String descripcion, Double precio, Integer duracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracion = duracion;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public List<Appointment> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Appointment> listaCitas) {
        this.listaCitas = listaCitas;
    }

    @Override
    public String toString() {
        return "Service{" +
                "servicioId=" + servicioId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", duracion=" + duracion +
                '}';
    }
}
