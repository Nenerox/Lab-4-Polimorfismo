public abstract class Contenido{
    protected int id;
    protected String titulo;
    protected String fechaCreacion;
    protected Usuario creador;
    protected String estado;
    
    public Contenido(int id, String titulo, Usuario creador, String fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.estado = "Borrador";       
    }
    public String getTitulo()
    {
        return titulo;
    }
    public void setTitulo(String nuevoTitulo)
    {
        this.titulo = nuevoTitulo;
    }
    
}