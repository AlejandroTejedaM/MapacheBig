package com.barber.appointment.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long citaId;

    @Column(nullable = false)
    private Timestamp fechaHora;

    @ManyToOne
    @JoinColumn(name = "servicioId", nullable = false, referencedColumnName = "servicioId")
    private Service servicio;

    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false, referencedColumnName = "usuarioId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "barberoId", nullable = false, referencedColumnName = "barberoId")
    private Barbero barbero;

    public Appointment() {
    }

    public Appointment(Timestamp fechaHora, Service servicio, User user, Barbero barbero) {
        this.fechaHora = fechaHora;
        this.servicio = servicio;
        this.user = user;
        this.barbero = barbero;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Service getServicio() {
        return servicio;
    }

    public void setServicio(Service servicio) {
        this.servicio = servicio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "citaId=" + citaId +
                ", fechaHora=" + fechaHora +
                ", servicio=" + servicio +
                ", user=" + user +
                ", barbero=" + barbero +
                '}';
    }
}
