public class Articulo extends Contenido implements IPublicable{
    private String cuerpoTexto;

    // Constructor: inicializa un artículo con sus propiedades básicas y contenido de texto
    public Articulo(int id, String titulo, Usuario creador, String cuerpoTexto, String resumen, String categoria)
    {
        super(id, titulo, creador, categoria);
        this.cuerpoTexto = cuerpoTexto;
    }
    
    @Override
    public String publicar()
    {
        return "Se ha publicado tu Artículo con título " + titulo + ", y ID: " + id;
    }
    
    // Devuelve el contenido completo del artículo para visualización
    public String visualizar()
    {
        return cuerpoTexto;
    }
    
    // Permite editar el contenido textual del artículo
    public void editar(String nuevoContenido)
    {
        cuerpoTexto = nuevoContenido;
    }
}