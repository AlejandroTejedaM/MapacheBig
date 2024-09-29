package com.barber.appointment.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "availability")
public class Availability {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disponibilidadId;

    @Enumerated(EnumType.STRING)
    private DiasSemana diaSemana;

    @Column(nullable = false)
    private Time horaInicio;

    @Column(nullable = false)
    private Time horaFin;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, referencedColumnName = "usuarioId")
    private User user;

    public Availability() {
    }

    public Availability(DiasSemana diaSemana, Time horaInicio, Time horaFin, User user) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.user = user;
    }

    public Long getDisponibilidadId() {
        return disponibilidadId;
    }

    public void setDisponibilidadId(Long disponibilidadId) {
        this.disponibilidadId = disponibilidadId;
    }

    public DiasSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiasSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "disponibilidadId=" + disponibilidadId +
                ", diaSemana=" + diaSemana +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", user=" + user +
                '}';
    }
}