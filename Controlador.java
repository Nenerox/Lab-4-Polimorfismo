
import java.util.ArrayList;

public class Controlador {
    private ArrayList <Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;

    public Controlador() {
        //usuarios por defecto
        usuarios.add(new Usuario("admin", "admin123", "administrador"));
        usuarios.add(new Usuario("editor", "editor123", "editor"));
    }

    public String autenticar(String username, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.verificacion(username, contraseña)) {
                this.usuarioActual = usuario;
                return "Bienvenido " + usuario.getUsername() + ", tu rol es: " + usuario.getRol();
            }
        }
        return "Credenciales incorrectas";
    }

    public String registrarUsuario(String username, String contraseña, String rol) {
        usuarios.add(new Usuario(username, contraseña, rol));
        return "Usuario " + username + " registrado con rol: " + rol;
    }

    public String eliminarUsuario(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                usuarios.remove(usuario);
                return "Usuario " + username + " eliminado.";
            }
        }
        return "Usuario no encontrado.";
    }

    public boolean esAdministrador() {
        return usuarioActual != null && usuarioActual.getRol().equals("administrador");
    }


}