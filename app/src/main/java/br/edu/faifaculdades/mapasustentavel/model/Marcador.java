package br.edu.faifaculdades.mapasustentavel.model;

import java.io.Serializable;

/**
 *
 */
public class Marcador implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private double latitude;
    private double longitude;
    private String titulo;
    private String descricao;
    private String categoria;
    private int contador;
    private String pathImagem;

    public Marcador(){}

    public Marcador(Long id, double latitude, double longitude, String titulo, String descricao, String categoria,
                    int contador, String pathImagem) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.contador = contador;
        this.pathImagem = pathImagem;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getContador() {
        return this.contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getPathImagem() {
        return pathImagem;
    }

    public void setPathImagem(String pathImagem) {
        this.pathImagem = pathImagem;
    }

}
