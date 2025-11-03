public class Articulo extends Contenido implements IPublicable{
    private String cuerpoTexto;
    private String resumen;
    private int tiempoLectura;

    public Articulo(int id, String titulo, Usuario creador, String cuerpoTexto, String resumen)
    {
        super(id, titulo, creador);
        this.cuerpoTexto = cuerpoTexto;
        this.resumen = resumen;
        this.tiempoLectura = calcularTiempoLectura(cuerpoTexto);
    }
}