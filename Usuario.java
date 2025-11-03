public class Usuario {
    private String username;
    private String contraseña;
    private String rol;

    public Usuario(String username, String contraseña, String rol) {
        this.username = username;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public boolean verificacion(String usaname, String contraseña) {
        return this.username.equals(username) && this.contraseña.equals(contraseña);
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }
}