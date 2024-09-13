package com.barber.appointment.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
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
    //hoolissss

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

    public void setContrasennia(String contraseña) {
        this.contrasennia = contraseña;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "User{" +
                "usuarioId=" + usuarioId +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contrasennia + '\'' +
                ", tipo=" + tipo +
                '}';
    }

    public boolean isAdmin() {
        return this.tipo == TipoUsuario.ADMINISTRADOR;
    }

    public boolean isClient() {
        return this.tipo == TipoUsuario.CLIENTE;
    }

}
