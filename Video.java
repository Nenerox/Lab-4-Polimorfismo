public class Video extends Contenido implements IPublicable{
    private String URLvideo;

    // Constructor: inicializa un video con URL y propiedades heredadas
    public Video(int id, String titulo, Usuario creador, String URLvideo, String resumen, String categoria)
    {
        super(id, titulo, creador, categoria);
        this.URLvideo = URLvideo;
    }

    @Override
    public String publicar()
    {
        return "Se ha publicado tu video con su título " + titulo + ", y ID: " + id;
    }
    
    // Devuelve la URL para visualizar el video
    public String visualizar()
    {
        return "Para visualizar, utilizar este URL: " + URLvideo;
    }
    
    // Permite editar la URL del video
    public void editar(String nuevoURL)
    {
        URLvideo = nuevoURL;
    }
}