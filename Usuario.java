public class Usuario {
    private String username;
    private String contraseña;
    private String rol;

    // Constructor: crea un usuario con nombre, contraseña y rol
    public Usuario(String username, String contraseña, String rol) {
        this.username = username;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Verifica si las credenciales coinciden con las del usuario
    public boolean verificacion(String username, String contraseña) {
        return this.username.equals(username) && this.contraseña.equals(contraseña);
    }

    // Getters: proporcionan acceso a las propiedades privadas
    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }
}