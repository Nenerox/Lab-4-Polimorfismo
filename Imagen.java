public class Imagen extends Contenido implements IPublicable{
    private String URLimagen;
    
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
    public String visualizar()
    {
        return "Para visualizar, utilizar este URL: " + URLimagen;
    }
    public void editar(String nuevoURL)
    {
        URLimagen = nuevoURL;
    }
}