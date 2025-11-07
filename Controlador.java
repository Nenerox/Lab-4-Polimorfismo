import java.util.ArrayList;
import java.util.Iterator;

public class Controlador {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Contenido> contenidos = new ArrayList<>();
    private ArrayList<Contenido> publicados = new ArrayList<>();
    private Usuario usuarioActual;
    private int IDContenido = 1;
    private Vista vista;

    public Controlador() {
        this.vista = new Vista(this);
        // Usuarios por defecto del sistema
        usuarios.add(new Usuario("admin", "admin123", "administrador"));
        usuarios.add(new Usuario("editor", "editor123", "editor"));
    }

    // Punto de entrada principal de la aplicación
    public void iniciarAplicacion() {
        vista.mostrarBienvenida();
        boolean ejecutando = true;
        
        while (ejecutando) {
            ejecutando = procesarMenuPrincipal();
        }
        
        vista.mostrarDespedida();
    }

    // Maneja las opciones del menú principal
    private boolean procesarMenuPrincipal() {
        int opcion = vista.mostrarMenuPrincipal();
        
        switch (opcion) {
            case 1:
                return procesarLogin();
            case 2:
                procesarRegistro();
                return true;
            case 3:
                return false;
            default:
                vista.mostrarError("Opción no válida");
                return true;
        }
    }

    // Procesa el inicio de sesión de usuarios
    private boolean procesarLogin() {
        String[] credenciales = vista.solicitarCredenciales();
        String resultado = autenticar(credenciales[0], credenciales[1]);
        vista.mostrarMensaje(resultado);

        if (resultado.contains("Bienvenido")) {
            return procesarMenuUsuario();
        }
        return true;
    }

    // Procesa el registro de nuevos usuarios
    private void procesarRegistro() {
        String[] datosRegistro = vista.solicitarDatosRegistro();
        
        // Validar rol
        if (!datosRegistro[2].equals("administrador") && !datosRegistro[2].equals("editor")) {
            vista.mostrarError("Rol debe ser 'administrador' o 'editor'");
            return;
        }
        
        String resultado = registrarUsuario(datosRegistro[0], datosRegistro[1], datosRegistro[2]);
        vista.mostrarMensaje(resultado);
    }

    // Maneja el menú después del login
    private boolean procesarMenuUsuario() {
        boolean enSesion = true;
        
        while (enSesion) {
            int opcion = vista.mostrarMenuUsuario(esAdministrador());
            
            switch (opcion) {
                case 1:
                    procesarGestionContenidos();
                    break;
                case 2:
                    generarReportes();
                    break;
                case 3:
                    if (esAdministrador()) {
                        procesarGestionUsuarios();
                    } else {
                        vista.mostrarError("Opción no válida");
                    }
                    break;
                case 4:
                    vista.mostrarMensaje("Sesión cerrada");
                    usuarioActual = null;
                    enSesion = false;
                    break;
                default:
                    vista.mostrarError("Opción no válida");
            }
        }
        return true;
    }

    // Gestiona todas las operaciones de contenido
    private void procesarGestionContenidos() {
        boolean enGestion = true;
        
        while (enGestion) {
            int opcion = vista.mostrarMenuGestionContenidos(esAdministrador());
            
            switch (opcion) {
                case 1:
                    crearContenido();
                    break;
                case 2:
                    editarContenido();
                    break;
                case 3:
                    publicarContenido();
                    break;
                case 4:
                    eliminarContenido();
                    break;
                case 5:
                    visualizarContenido();
                    break;
                case 6:
                    filtrarContenidos();
                    break;
                case 7:
                    enGestion = false;
                    break;
                default:
                    vista.mostrarError("Opción no válida");
            }
        }
    }

    // Gestiona usuarios (solo administradores)
    private void procesarGestionUsuarios() {
        if (!esAdministrador()) return;
        
        boolean enGestion = true;
        
        while (enGestion) {
            int opcion = vista.mostrarMenuGestionUsuarios();
            
            switch (opcion) {
                case 1:
                    vista.mostrarUsuarios(verUsuarios());
                    break;
                case 2:
                    eliminarUsuarioInterfaz();
                    break;
                case 3:
                    enGestion = false;
                    break;
                default:
                    vista.mostrarError("Opción no válida");
            }
        }
    }

    // Autentica usuario con credenciales
    public String autenticar(String username, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.verificacion(username, contraseña)) {
                this.usuarioActual = usuario;
                return "Bienvenido " + usuario.getUsername() + ", tu rol es: " + usuario.getRol();
            }
        }
        return "Credenciales incorrectas";
    }

    // Registra un nuevo usuario en el sistema
    public String registrarUsuario(String username, String contraseña, String rol) {
        usuarios.add(new Usuario(username, contraseña, rol));
        return "Usuario " + username + " registrado con rol: " + rol;
    }

    // Elimina un usuario del sistema
    public String eliminarUsuario(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                return "Usuario " + username + " eliminado.";
            }
        }
        return "Usuario no encontrado.";
    }

    // Interfaz para eliminar usuario
    private void eliminarUsuarioInterfaz() {
        String username = vista.solicitarUsernameEliminar();
        String resultado = eliminarUsuario(username);
        vista.mostrarMensaje(resultado);
    }

    // Obtiene lista de usuarios registrados
    public ArrayList<String> verUsuarios() {
        ArrayList<String> usuariosInfo = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuariosInfo.add(usuario.getUsername() + " - " + usuario.getRol());
        }
        return usuariosInfo;
    }

    // Verifica si el usuario actual es administrador
    public boolean esAdministrador() {
        return usuarioActual != null && usuarioActual.getRol().equals("administrador");
    }

    // Crea nuevo contenido según el tipo especificado
    public String crear(int tipo, String titulo, String contenido, String resumen, String categoria) {
        if (tipo == 1) {
            contenidos.add(new Articulo(IDContenido, titulo, usuarioActual, contenido, resumen, categoria));
            IDContenido++;
            return "Contenido con ID " + (IDContenido - 1) + ", titulo: " + titulo + " creado.";
        } else if (tipo == 2) {
            contenidos.add(new Imagen(IDContenido, titulo, usuarioActual, contenido, resumen, categoria));
            IDContenido++;
            return "Contenido con ID " + (IDContenido - 1) + ", titulo: " + titulo + " creado.";
        } else if (tipo == 3) {
            contenidos.add(new Video(IDContenido, titulo, usuarioActual, contenido, resumen, categoria));
            IDContenido++;
            return "Contenido con ID " + (IDContenido - 1) + ", titulo: " + titulo + " creado.";
        } else {
            return "Tipo de contenido no válido.";
        }
    }

    // Interfaz para crear contenido
    private void crearContenido() {
        int tipo = vista.solicitarTipoContenido();
        String titulo = vista.solicitarTituloContenido();
        String contenido = vista.solicitarContenido();
        String resumen = vista.solicitarResumen();
        String categoria = vista.solicitarCategoria();
        String resultado = crear(tipo, titulo, contenido, resumen, categoria);
        vista.mostrarMensaje(resultado);
    }

    // Filtra contenidos según criterios especificados
    private void filtrarContenidos() {
        int tipoFiltro = vista.solicitarTipoFiltro();
        String valor = vista.solicitarValorFiltro(tipoFiltro);
        ArrayList<String> resultados = new ArrayList<>();
        switch (tipoFiltro) {
            case 1:
                resultados = filtroCategoriaPublicados(valor);
                break;
            case 2:
                resultados = filtroCategoriaNoPublicados(valor);
                break;
            case 3:
                resultados = filtroTipoPublicados(valor);
                break;
            case 4:
                resultados = filtoTipoNoPublicados(valor);
                break;
            }
        vista.mostrarResultadosFiltro(resultados);
    }

    // Edita contenido existente
    public String editar(int ID, String nuevoContenido) {
        for (Contenido c : contenidos) {
            if (c.getID() == ID) {
                ((IPublicable) c).editar(nuevoContenido);
                return "Contenido con ID " + ID + ", titulo: " + c.getTitulo() + " editado.";
            }
        }
        for (Contenido c : publicados) {
            if (c.getID() == ID) {
                return "No se puede editar un contenido ya publicado.";
            }
        }
        return "Contenido no encontrado.";
    }

    // Interfaz para editar contenido
    private void editarContenido() {
        int id = vista.solicitarIDContenido("editar");
        String nuevoContenido = vista.solicitarNuevoContenido();
        String resultado = editar(id, nuevoContenido);
        vista.mostrarMensaje(resultado);
    }

    // Publica contenido moviéndolo a la lista de publicados
    public String publicar(int ID) {
        for (Contenido c : contenidos) {
            if (c.getID() == ID) {
                publicados.add(c);
                contenidos.remove(c);
                return ((IPublicable) c).publicar();
            }
        }
        return "Contenido no encontrado.";
    }

    // Interfaz para publicar contenido
    private void publicarContenido() {
        int id = vista.solicitarIDContenido("publicar");
        String resultado = publicar(id);
        vista.mostrarMensaje(resultado);
    }

    // Elimina contenido del sistema
    public String eliminar(int ID) {
        for (Contenido c : contenidos) {
            if (c.getID() == ID) {
                contenidos.remove(c);
                return "Contenido con ID " + ID + ", titulo: " + c.getTitulo() + " eliminado.";
            }
        }
        for (Contenido c : publicados) {
            if (c.getID() == ID) {
                publicados.remove(c);
                return "Contenido con ID " + ID + ", titulo: " + c.getTitulo() + " eliminado.";
            }
        }
        return "Contenido no encontrado.";
    }

    // Interfaz para eliminar contenido
    private void eliminarContenido() {
        int id = vista.solicitarIDContenido("eliminar");
        if (!esAdministrador()) {
            vista.mostrarMensaje("Nota: Como editor, solo puedes eliminar contenidos no publicados");
        }
        String resultado = eliminar(id);
        vista.mostrarMensaje(resultado);
    }

    // Interfaz para visualizar contenido
    private void visualizarContenido() {
        int id = vista.solicitarIDContenido("visualizar");
        String resultado = visualizar(id);
        vista.mostrarMensaje(resultado);
    }

    // Visualiza contenido publicado
    public String visualizar(int ID) {
        for (Contenido c : publicados) {
            if (c.getID() == ID) {
                return "ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername() + "\nContenido: " + ((IPublicable) c).visualizar();
            }
        }
        return "Contenido no encontrado.";
    }

    // Métodos de reportes y estadísticas
    public int CantidadPublicados() {
        return publicados.size();
    }

    public ArrayList<String> listarPublicados() {
        ArrayList<String> lista = new ArrayList<>();
        for (Contenido c : publicados) {
            lista.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
        }
        return lista;
    }

    public String CantidadPorContenido() {
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

    public ArrayList<String> TiposDeCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        for (Contenido c : publicados) {
            if (!categorias.contains(c.getCategoria())) {
                categorias.add(c.getCategoria());
            }
        }
        return categorias;
    }

    // Métodos de filtrado
    public ArrayList<String> filtroCategoriaPublicados(String categoria) {
        ArrayList<String> filtro = new ArrayList<>();
        for (Contenido c : publicados) {
            if (c.getCategoria().equals(categoria)) {
                filtro.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
            }
        }
        return filtro;
    }

    public ArrayList<String> filtroCategoriaNoPublicados(String categoria) {
        ArrayList<String> filtro = new ArrayList<>();
        for (Contenido c : contenidos) {
            if (c.getCategoria().equals(categoria)) {
                filtro.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
            }
        }
        return filtro;
    }

    public ArrayList<String> filtroTipoPublicados(String tipo) {
        ArrayList<String> filtro = new ArrayList<>();
        for (Contenido c : publicados) {
            if ((tipo.equals("Articulo") && c instanceof Articulo) ||
                (tipo.equals("Imagen") && c instanceof Imagen) ||
                (tipo.equals("Video") && c instanceof Video)) {
                filtro.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
            }
        }
        return filtro;
    }

    public ArrayList<String> filtoTipoNoPublicados(String tipo) {
        ArrayList<String> filtro = new ArrayList<>();
        for (Contenido c : contenidos) {
            if ((tipo.equals("Articulo") && c instanceof Articulo) ||
                (tipo.equals("Imagen") && c instanceof Imagen) ||
                (tipo.equals("Video") && c instanceof Video)) {
                filtro.add("ID: " + c.getID() + ", Título: " + c.getTitulo() + ", Creador: " + c.getCreador().getUsername());
            }
        }
        return filtro;
    }

    // Genera y muestra reportes del sistema
    private void generarReportes() {
        vista.mostrarReportes(CantidadPublicados(), CantidadPorContenido(), listarPublicados(), TiposDeCategorias());
    }
}