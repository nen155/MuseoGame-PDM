package modelos;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.Date;

/**
 * Created by Emilio Chica Jiménez on 12/05/2017.
 */

public class Obra implements Parcelable {
    private int id;
    private String titulo;
    private Date fecha;
    private String descripcion;
    private Uri urlImagen;
    private int puntos;
    private String tipo;

    public Obra(int id, String titulo, Date fecha, String descripcion, Uri urlImagen, int puntos, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.puntos = puntos;
        this.tipo = tipo;
    }

    protected Obra(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        fecha = new Date(in.readLong());
        descripcion = in.readString();
        urlImagen = in.readParcelable(Uri.class.getClassLoader());
        puntos = in.readInt();
        tipo = in.readString();
    }

    public static final Creator<Obra> CREATOR = new Creator<Obra>() {
        @Override
        public Obra createFromParcel(Parcel in) {
            return new Obra(in);
        }

        @Override
        public Obra[] newArray(int size) {
            return new Obra[size];
        }
    };

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
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

    public Uri getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(Uri urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeLong(fecha.getTime());
        dest.writeString(descripcion);
        dest.writeParcelable(urlImagen, flags);
        dest.writeInt(puntos);
        dest.writeString(tipo);
    }
}
