package com.barber.appointment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sucursal")
@JsonIgnoreProperties({"barberos", "servicios"})
public class Sucursal {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sucursalId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private List<Barbero> barberos = new ArrayList<>();

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private List<Service> servicios = new ArrayList<>();

    public Sucursal(){}

    public Sucursal(Long sucursalId, String nombre, String direccion, List<Barbero> barberos, List<Service> servicios) {
        this.sucursalId = sucursalId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.barberos = barberos;
        this.servicios = servicios;
    }

    public Long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Barbero> getBarberos() {
        return barberos;
    }

    public void setBarberos(List<Barbero> barberos) {
        this.barberos = barberos;
    }

    public List<Service> getServicios() {
        return servicios;
    }

    public void setServicios(List<Service> servicios) {
        this.servicios = servicios;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "sucursalId=" + sucursalId +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", barberos=" + barberos +
                ", servicios=" + servicios +
                '}';
    }
}
