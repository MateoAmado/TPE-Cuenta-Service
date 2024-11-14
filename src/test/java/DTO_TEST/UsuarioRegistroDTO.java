package DTO_TEST;


public class UsuarioRegistroDTO {
    private String password;
    private String email;
    private String rol;
    private String nombre;
    private String apellido;
    private int nro_celular;

    private Long longitud;
    private Long latitud;

    public UsuarioRegistroDTO(){
    }

    public UsuarioRegistroDTO(String password, String email, String rol, String nombre, String apellido, int nro_celular, Long longitud, Long latitud){
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nro_celular = nro_celular;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getNro_celular() {
        return nro_celular;
    }

    public void setNro_celular(int nro_celular) {
        this.nro_celular = nro_celular;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }

    public Long getLatitud() {
        return latitud;
    }

    public void setLatitud(Long latitud) {
        this.latitud = latitud;
    }
}

