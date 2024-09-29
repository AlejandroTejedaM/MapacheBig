package com.barber.appointment.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "barberos")
public class Barbero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long barberoId;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false, referencedColumnName = "sucursalId")
    private Sucursal sucursal;

    @OneToMany(mappedBy = "barbero", cascade = CascadeType.ALL)
    private List<Appointment> citas = new ArrayList<>();

    public Barbero() {
    }

    public Barbero(String nombre, Sucursal sucursal) {
        this.nombre = nombre;
        this.sucursal = sucursal;
    }

    public Long getBarberoId() {
        return barberoId;
    }

    public void setBarberoId(Long barberoId) {
        this.barberoId = barberoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public List<Appointment> getCitas() {
        return citas;
    }

    public void setCitas(List<Appointment> citas) {
        this.citas = citas;
    }

    @Override
    public String toString() {
        return "Barbero{" +
                "barberoId=" + barberoId +
                ", nombre='" + nombre + '\'' +
                ", sucursal=" + sucursal +
                ", citas=" + citas +
                '}';
    }

}
