package com.barber.appointment.Model;

import ch.qos.logback.core.subst.Token;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"listaCitas", "listaDisponibilidad"})
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false, unique = true)
    private String correo;
    @Column(nullable = false)
    private String contrasennia;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Appointment> listaCitas = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Availability> listaDisponibilidad = new ArrayList<>();

    public User() {
    }

    public User(String nombre, String correo, String contrasennia, TipoUsuario tipo) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasennia = contrasennia;
        this.tipo = tipo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasennia() {
        return contrasennia;
    }

    public void setContrasennia(String contrasennia) {
        this.contrasennia = contrasennia;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public List<Appointment> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Appointment> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public List<Availability> getListaDisponibilidad() {
        return listaDisponibilidad;
    }

    public void setListaDisponibilidad(List<Availability> listaDisponibilidad) {
        this.listaDisponibilidad = listaDisponibilidad;
    }

    @Override
    public String toString() {
        return "User{" +
                "usuarioId=" + usuarioId +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasennia='" + contrasennia + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
