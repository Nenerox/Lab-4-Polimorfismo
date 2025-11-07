public class Imagen extends Contenido implements IPublicable{
    private String URLimagen;
    
    // Constructor: inicializa una imagen con URL y propiedades heredadas
    public Imagen(int id, String titulo, Usuario creador, String URLimagen, String resumen, String categoria)
    {
        super(id, titulo, creador, categoria);
        this.URLimagen = URLimagen;
    }

    @Override
    public String publicar()
    {
        return "Se ha publicado tu imagen con su título " + titulo + ", y ID: " + id;
    }
    
    // Devuelve la URL para visualizar la imagen
    public String visualizar()
    {
        return "Para visualizar, utilizar este URL: " + URLimagen;
    }
    
    // Permite editar la URL de la imagen
    public void editar(String nuevoURL)
    {
        URLimagen = nuevoURL;
    }
}