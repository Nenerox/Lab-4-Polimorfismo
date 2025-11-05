
import java.util.ArrayList;

public class Controlador {
    private ArrayList <Usuario> usuarios = new ArrayList<>();
    private ArrayList <Contenido> contenidos = new ArrayList<>();
    private ArrayList <Contenido> publicados = new ArrayList<>();
    private Usuario usuarioActual;
    private int IDContenido = 1;

    public Controlador() {
        //usuarios por defecto
        usuarios.add(new Usuario("admin", "admin123", "administrador"));
        usuarios.add(new Usuario("editor", "editor123", "editor"));
    }

//verifica que los datos de login sean correctos
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

//regresa una lista de usernames de los usuarios registrados
    public ArrayList <String> verUsuarios(){
        ArrayList <String> usarnames = new ArrayList<>();
        for(Usuario usuario: usuarios){
            usarnames.add(usuario.getUsername());
        }
        return usarnames;
    }

//verifica si el usuario actual es administrador
    public boolean esAdministrador() {
        return usuarioActual != null && usuarioActual.getRol().equals("administrador");
    }

//crea contenido segun el tipo 1-Articulo, 2-Imagen, 3-Video y lo agrega a la lista de contenidos no publicados
    public String crear(int tipo, String titulo, String contenido, String resumen, String categoria){
        if (tipo == 1) {
            contenidos.add(new Articulo(IDContenido, titulo , usuarioActual, contenido, resumen, categoria));
            return "Contenido con ID " + IDContenido + ", titulo: " + titulo + " creado.";
        } else if (tipo == 2) {
            contenidos.add(new Imagen(IDContenido, titulo , usuarioActual, contenido, resumen, categoria));
            return "Contenido con ID " + IDContenido + ", titulo: " + titulo + " creado.";
        } else if (tipo == 3) {
            contenidos.add(new Video(IDContenido, titulo , usuarioActual, contenido, resumen, categoria));
            return "Contenido con ID " + IDContenido + ", titulo: " + titulo + " creado.";
        } else {
            return "Tipo de contenido no válido.";
        }
    }

//edita contenido por ID si no ha sido publicado
    public String editar(int ID, String nuevoContenido){
        for (Contenido c : contenidos) {
            if (c.getID() ==  ID) {
                ((IPublicable) c).editar(nuevoContenido);
                return "Contenido con ID " + ID + ", titulo: " + c.getTitulo() + " editado.";
            }
        }
        for (Contenido c : publicados) {
            if (c.getID() ==  ID) {
                return "No se puede editar un contenido ya publicado.";
            }
            
        }
        return "Contenido no encontrado.";
    }

//publica contenido por ID y lo mueve a la lista de publicados
    public String publicar(int ID){
        for (Contenido c : contenidos) {
            if (c.getID() ==  ID) {
                publicados.add(c);
                contenidos.remove(c);
                return(((IPublicable) c).publicar());
            }
        }
        return "Contenido no encontrado.";
    }

//elimina contenido por ID ya sea publicado o no
    public String eliminar(int ID){
        for (Contenido c : contenidos) {
            if (c.getID() ==  ID) {
                contenidos.remove(c);
                return "Contenido con ID " + ID + ", titulo: " + c.getTitulo() + " eliminado.";
            }
        }
        for (Contenido c : publicados) {
            if (c.getID() ==  ID) {
                publicados.remove(c);
                return "Contenido con ID " + ID + ", titulo: " + c.getTitulo() + " eliminado.";
            }
            
        }
        return "Contenido no encontrado.";
    }

//Resumenes de los contenidos publicados
    public int CantidadPublicaods(){
        return publicados.size();
    }

    public ArrayList <String> listarPublicados() {
        ArrayList <String> lista = new ArrayList<>();
        for (Contenido c : publicados) {
            lista.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
        }
        return lista;
    }

    public String CantidadPorContenido(){
        int articulos = 0;
        int imagenes = 0;
        int videos = 0;
        for (Contenido c : publicados) {
            if (c instanceof Articulo) {
                articulos++;
            } else if (c instanceof Imagen) {
                imagenes++;
            } else if (c instanceof Video) {
                videos++;
            }
        }
        return "Artículos: " + articulos + ", Imágenes: " + imagenes + ", Videos: " + videos;
    }
//Resumen de los tipos de categorias de los contenidos publicados, regresa una lista de strings de las categorias
    public ArrayList <String> TiposDeCategorias(){
        ArrayList <String> categorias = new ArrayList<>();
        for (Contenido c : publicados) {
            if (!categorias.contains(c.getCategoria())) {
                categorias.add(c.getCategoria());
            }
        }
        return categorias;
    }

//Filtrar por categoria los publicados y no publicados y regresa una lista de strings con la info
    public ArrayList <String> filtroCategoriaPublicados(String categoria) {
        ArrayList <String> filtro = new ArrayList<>();
        for (Contenido c : publicados) {
            if (c.getCategoria().equals(categoria)) {
                filtro.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
            }
        }
        return filtro;
    }

    public ArrayList <String> filtroCategoriaNoPublicados(String categoria) {
        ArrayList <String> filtro = new ArrayList<>();
        for (Contenido c : contenidos) {
            if (c.getCategoria().equals(categoria)) {
                filtro.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
            }
        }
        return filtro;
    }
//Filtrar por tipo los publicados y no publicados y regresa una lista de strings con la info
    public ArrayList <String> filtroTipoPublicados(String tipo) {
        ArrayList <String> filtro = new ArrayList<>();
        for (Contenido c : publicados) {
            if ((tipo.equals("Articulo") && c instanceof Articulo) ||
                (tipo.equals("Imagen") && c instanceof Imagen) ||
                (tipo.equals("Video") && c instanceof Video)) {
                filtro.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
            }
        }
        return filtro;
    }

    public ArrayList <String> filtoTipoNoPublicados(String categoria) {
        ArrayList <String> filtro = new ArrayList<>();
        for (Contenido c : contenidos) {
            if ((categoria.equals("Articulo") && c instanceof Articulo) ||
                (categoria.equals("Imagen") && c instanceof Imagen) ||
                (categoria.equals("Video") && c instanceof Video)) {
                filtro.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
            }
        }
        return filtro;
    }

//visualizar contenido por ID
    public String visualizar(int ID) {
        for (Contenido c : publicados) {
            if (c.getID() ==  ID) {
                return ("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername() + "\nContenido: " + ((IPublicable) c).visualizar());
            }
        }
        return "Contenido no encontrado.";
    }
}