package br.edu.faifaculdades.mapasustentavel.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Guilherme Schenckel on 15/11/2016.
 */

public class Marcacao {
    private double Latitude;
    private double Longitude;
    private String UserID;
    private String Categoria;
    private String Titulo;

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    private String Descricao;
    private String Imagens;

    public Marcacao() {
        //NECESSÁRIO PARA O MÉTODO dataSnapShot.getValue(Marcacao.class);
    }

    public Marcacao(String userID, double latitude, double longitude) {
        this.UserID = userID;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public String getCategoria() {
        return Categoria;
    }

    public String getDescricao() {
        return Descricao;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getUserID() {
        return UserID;
    }

    public String getImagens() {
        return Imagens;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public void setImagens(String imagens) {
        Imagens = imagens;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("latitude", Latitude);
        result.put("longitude", Longitude);
        result.put("categoria", Categoria);
        result.put("descricao", Descricao);
        result.put("titulo", Titulo);
        result.put("imagens", Imagens);
        return result;
    }
}
