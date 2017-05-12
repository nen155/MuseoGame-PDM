package modelos;

import java.util.Date;

/**
 * Created by Emilio Chica Jiménez on 12/05/2017.
 */

public class Obra {
    private int id;
    private String titulo;
    private Date fecha;
    private String descripcion;
    private String urlImagen;

    public Obra(int id, String titulo, Date fecha, String descripcion, String urlImagen) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
