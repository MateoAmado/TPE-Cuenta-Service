package DTO_TEST;

import lombok.Data;


@Data
public class UsuarioLoginDTO {

    private String password;
    private String email;
    private String rol;

    public UsuarioLoginDTO(){
    }

    public UsuarioLoginDTO(String email, String password){
        this.email = email;
        this.password = password;
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
}