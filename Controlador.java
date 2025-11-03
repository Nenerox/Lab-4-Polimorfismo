
import java.util.ArrayList;

public class Controlador {
    private ArrayList <Usuario> usuarios = new ArrayList<>();

    public Controlador() {
        //usuarios por defecto
        usuarios.add(new Usuario("admin", "admin123", "administrador"));
        usuarios.add(new Usuario("editor", "editor123", "editor"));
    }

    public String autenticar(String username, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.verificacion(username, contraseña)) {
                return "Bienvenido " + usuario.getUsername() + ", tu rol es: " + usuario.getRol();
            }
        }
        return "Credenciales incorrectas";
    }


}