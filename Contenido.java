public abstract class Contenido{

    protected int id;
    protected String titulo;
    protected Usuario creador;
    protected String categoria;
    
    // Constructor
    public Contenido(int id, String titulo, Usuario creador, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.creador = creador;
        this.categoria = categoria;
    }
    
    // Getters
    public String getTitulo()
    {
        return titulo;
    }
    public Usuario getCreador()
    {
        return creador;
    }
    public int getID()
    {
        return id;
    }
    public String getCategoria()
    {
        return categoria;
    }
}