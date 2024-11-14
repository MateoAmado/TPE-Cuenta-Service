package com.example.Cuenta.DTO;

public class UsuarioDTO {

    private int id;
    private String password;
    private String email;

    private String apellido;

    private String nombre;

    private int nro_celular;
    private String rol;

    public UsuarioDTO(int id, String password, String email, String apellido, String nombre, int nro_celular, String rol) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.apellido = apellido;
        this.nombre = nombre;
        this.nro_celular = nro_celular;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNro_celular() {
        return nro_celular;
    }

    public void setNro_celular(int nro_celular) {
        this.nro_celular = nro_celular;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
