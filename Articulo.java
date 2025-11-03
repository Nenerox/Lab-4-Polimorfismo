public class Articulo extends Contenido implements IPublicable{
    private String cuerpoTexto;

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
    public String visualizar()
    {
        return cuerpoTexto;
    }
}