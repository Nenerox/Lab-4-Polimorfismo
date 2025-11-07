import java.util.ArrayList;
import java.util.Scanner;

public class Vista {
    private Controlador controlador;
    private Scanner scanner;

    public Vista(Controlador controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarBienvenida() {
        System.out.println("=== SISTEMA DE GESTIÓN DE CONTENIDOS ===");
    }

    public void mostrarDespedida() {
        System.out.println("¡Hasta pronto!");
    }

    public int mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    public String[] solicitarCredenciales() {
        scanner.nextLine(); // Limpiar buffer
        System.out.println("\n--- INICIAR SESIÓN ---");
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        return new String[]{username, contraseña};
    }

    public String[] solicitarDatosRegistro() {
        scanner.nextLine(); // Limpiar buffer
        System.out.println("\n--- REGISTRAR USUARIO ---");
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.print("Rol (administrador/editor): ");
        String rol = scanner.nextLine();
        return new String[]{username, contraseña, rol};
    }

    public int mostrarMenuUsuario(boolean esAdministrador) {
        System.out.println("\n--- MENÚ DE USUARIO ---");
        System.out.println("1. Gestionar contenidos");
        System.out.println("2. Ver reportes");
        if (esAdministrador) {
            System.out.println("3. Gestionar usuarios");
            System.out.println("4. Cerrar sesión");
        } else {
            System.out.println("3. Cerrar sesión");
        }
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    public int mostrarMenuGestionContenidos(boolean esAdministrador) {
        System.out.println("\n--- GESTIÓN DE CONTENIDOS ---");
        System.out.println("1. Crear contenido");
        System.out.println("2. Editar contenido");
        System.out.println("3. Publicar contenido");
        if (esAdministrador) {
            System.out.println("4. Eliminar contenido");
        } else {
            System.out.println("4. Eliminar contenido (solo no publicados)");
        }
        System.out.println("5. Visualizar contenido");
        System.out.println("6. Filtrar contenidos");
        System.out.println("7. Volver");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    public int mostrarMenuGestionUsuarios() {
        System.out.println("\n--- GESTIÓN DE USUARIOS (Admin) ---");
        System.out.println("1. Ver usuarios registrados");
        System.out.println("2. Eliminar usuario");
        System.out.println("3. Volver");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    public Object[] solicitarDatosContenido() {
        scanner.nextLine(); // Limpiar buffer
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

        return new Object[]{tipo, titulo, contenido, resumen, categoria};
    }

    public int solicitarIDContenido(String accion) {
        System.out.print("ID del contenido a " + accion + ": ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    public String solicitarNuevoContenido() {
        System.out.print("Nuevo contenido/URL: ");
        return scanner.nextLine();
    }

    public String solicitarUsernameEliminar() {
        System.out.print("Usuario a eliminar: ");
        return scanner.nextLine();
    }

    public Object[] solicitarFiltro() {
        System.out.println("\n--- FILTRAR CONTENIDOS ---");
        System.out.println("1. Por categoría (publicados)");
        System.out.println("2. Por categoría (no publicados)");
        System.out.println("3. Por tipo (publicados)");
        System.out.println("4. Por tipo (no publicados)");
        System.out.print("Seleccione opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        String valor = "";
        switch (opcion) {
            case 1:
            case 2:
                System.out.print("Categoría: ");
                valor = scanner.nextLine();
                break;
            case 3:
            case 4:
                System.out.print("Tipo (Articulo/Imagen/Video): ");
                valor = scanner.nextLine();
                break;
        }

        return new Object[]{opcion, valor};
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String error) {
        System.out.println("Error: " + error);
    }

    public void mostrarUsuarios(ArrayList<String> usuarios) {
        System.out.println("\n--- USUARIOS REGISTRADOS ---");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados");
        } else {
            for (String usuario : usuarios) {
                System.out.println("- " + usuario);
            }
        }
    }

    public void mostrarResultadosFiltro(ArrayList<String> resultados) {
        System.out.println("\nResultados del filtro:");
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron contenidos");
        } else {
            for (String resultado : resultados) {
                System.out.println(resultado);
            }
        }
    }

    public void mostrarReportes(int totalPublicados, String cantidadPorTipo, 
                               ArrayList<String> publicados, ArrayList<String> categorias) {
        System.out.println("\n--- REPORTES ---");
        System.out.println("Total de contenidos publicados: " + totalPublicados);
        System.out.println(cantidadPorTipo);
        
        System.out.println("\nContenidos publicados:");
        if (publicados.isEmpty()) {
            System.out.println("No hay contenidos publicados");
        } else {
            for (String contenido : publicados) {
                System.out.println(contenido);
            }
        }

        System.out.println("\nCategorías disponibles:");
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías");
        } else {
            for (String categoria : categorias) {
                System.out.println("- " + categoria);
            }
        }
    }

    public void cerrar() {
        scanner.close();
    }
}