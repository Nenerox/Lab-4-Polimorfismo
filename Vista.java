import java.util.ArrayList;
import java.util.Scanner;

public class Vista {
    private Controlador controlador;
    private Scanner scanner;

    public Vista() {
        this.controlador = new Controlador();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== SISTEMA DE GESTIÓN DE CONTENIDOS ===");
        
        while (true) {
            if (!mostrarMenuPrincipal()) {
                break;
            }
        }
        
        scanner.close();
    }

    private boolean mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        switch (opcion) {
            case 1:
                return iniciarSesion();
            case 2:
                registrarUsuario();
                return true;
            case 3:
                System.out.println("¡Hasta pronto!");
                return false;
            default:
                System.out.println("Opción no válida");
                return true;
        }
    }

    private boolean iniciarSesion() {
        System.out.println("\n--- INICIAR SESIÓN ---");
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();

        String resultado = controlador.autenticar(username, contraseña);
        System.out.println(resultado);

        if (resultado.contains("Bienvenido")) {
            return mostrarMenuUsuario();
        }
        return true;
    }

    private void registrarUsuario() {
        System.out.println("\n--- REGISTRAR USUARIO ---");
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.print("Rol (administrador/editor): ");
        String rol = scanner.nextLine();

        String resultado = controlador.registrarUsuario(username, contraseña, rol);
        System.out.println(resultado);
    }

    private boolean mostrarMenuUsuario() {
        while (true) {
            System.out.println("\n--- MENÚ DE USUARIO ---");
            System.out.println("1. Gestionar contenidos");
            System.out.println("2. Ver reportes");
            System.out.println("3. Ver usuarios (solo admin)");
            System.out.println("4. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    gestionarContenidos();
                    break;
                case 2:
                    verReportes();
                    break;
                case 3:
                    if (controlador.esAdministrador()) {
                        verUsuarios();
                    } else {
                        System.out.println("No tienes permisos de administrador");
                    }
                    break;
                case 4:
                    System.out.println("Sesión cerrada");
                    return true;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void gestionarContenidos() {
        while (true) {
            System.out.println("\n--- GESTIÓN DE CONTENIDOS ---");
            System.out.println("1. Crear contenido");
            System.out.println("2. Editar contenido");
            System.out.println("3. Publicar contenido");
            System.out.println("4. Eliminar contenido");
            System.out.println("5. Visualizar contenido");
            System.out.println("6. Filtrar contenidos");
            System.out.println("7. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

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
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void crearContenido() {
        System.out.println("\n--- CREAR CONTENIDO ---");
        System.out.println("1. Artículo");
        System.out.println("2. Imagen");
        System.out.println("3. Video");
        System.out.print("Seleccione el tipo: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Contenido/URL: ");
        String contenido = scanner.nextLine();
        System.out.print("Resumen: ");
        String resumen = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        String resultado = controlador.crear(tipo, titulo, contenido, resumen, categoria);
        System.out.println(resultado);
    }

    private void editarContenido() {
        System.out.println("\n--- EDITAR CONTENIDO ---");
        System.out.print("ID del contenido a editar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo contenido/URL: ");
        String nuevoContenido = scanner.nextLine();

        String resultado = controlador.editar(id, nuevoContenido);
        System.out.println(resultado);
    }

    private void publicarContenido() {
        System.out.println("\n--- PUBLICAR CONTENIDO ---");
        System.out.print("ID del contenido a publicar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String resultado = controlador.publicar(id);
        System.out.println(resultado);
    }

    private void eliminarContenido() {
        System.out.println("\n--- ELIMINAR CONTENIDO ---");
        System.out.print("ID del contenido a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String resultado = controlador.eliminar(id);
        System.out.println(resultado);
    }

    private void visualizarContenido() {
        System.out.println("\n--- VISUALIZAR CONTENIDO ---");
        System.out.print("ID del contenido a visualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String resultado = controlador.visualizar(id);
        System.out.println(resultado);
    }

    private void filtrarContenidos() {
        System.out.println("\n--- FILTRAR CONTENIDOS ---");
        System.out.println("1. Por categoría (publicados)");
        System.out.println("2. Por categoría (no publicados)");
        System.out.println("3. Por tipo (publicados)");
        System.out.println("4. Por tipo (no publicados)");
        System.out.print("Seleccione opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        ArrayList<String> resultados = new ArrayList<>();
        
        switch (opcion) {
            case 1:
                System.out.print("Categoría: ");
                String categoria = scanner.nextLine();
                resultados = controlador.filtroCategoriaPublicados(categoria);
                break;
            case 2:
                System.out.print("Categoría: ");
                categoria = scanner.nextLine();
                resultados = controlador.filtroCategoriaNoPublicados(categoria);
                break;
            case 3:
                System.out.print("Tipo (Articulo/Imagen/Video): ");
                String tipo = scanner.nextLine();
                resultados = controlador.filtroTipoPublicados(tipo);
                break;
            case 4:
                System.out.print("Tipo (Articulo/Imagen/Video): ");
                tipo = scanner.nextLine();
                resultados = controlador.filtoTipoNoPublicados(tipo);
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }

        System.out.println("\nResultados del filtro:");
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron contenidos");
        } else {
            for (String resultado : resultados) {
                System.out.println(resultado);
            }
        }
    }

    private void verReportes() {
        System.out.println("\n--- REPORTES ---");
        System.out.println("Total de contenidos publicados: " + controlador.CantidadPublicaods());
        System.out.println(controlador.CantidadPorContenido());
        
        System.out.println("\nContenidos publicados:");
        ArrayList<String> publicados = controlador.listarPublicados();
        for (String contenido : publicados) {
            System.out.println(contenido);
        }

        System.out.println("\nCategorías disponibles:");
        ArrayList<String> categorias = controlador.TiposDeCategorias();
        for (String categoria : categorias) {
            System.out.println("- " + categoria);
        }
    }

    private void verUsuarios() {
        System.out.println("\n--- USUARIOS REGISTRADOS ---");
        ArrayList<String> usuarios = controlador.verUsuarios();
        for (String usuario : usuarios) {
            System.out.println("- " + usuario);
        }

        System.out.println("\n1. Eliminar usuario");
        System.out.println("2. Volver");
        System.out.print("Seleccione opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion == 1) {
            System.out.print("Usuario a eliminar: ");
            String username = scanner.nextLine();
            String resultado = controlador.eliminarUsuario(username);
            System.out.println(resultado);
        }
    }
}