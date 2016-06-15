/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ifrs.modelo;


/**
 *
 * @author ti
 */

public class Dado {
    private static final long serialVersionUID = 1L;
    private Integer idDado;
    private Integer sensor;
    private Float valor;
    private Leitura idLeitura;
    private String tempo;
    private String hash;

    public Dado() {
    }

    public Dado(Integer sensor, Float valor, Leitura idLeitura, String tempo, String hash) {
        this.sensor = sensor;
        this.valor = valor;
        this.idLeitura = idLeitura;
        this.tempo = tempo;
        this.hash = hash;
    }

    public Dado(Integer idDado, Integer sensor, Float valor, String tempo, String hash) {
        this.idDado = idDado;
        this.sensor = sensor;
        this.valor = valor;
        this.tempo = tempo;
        this.hash = hash;
    }

    public Dado(Integer sensor, Float valor) {
        this.sensor = sensor;
        this.valor = valor;
    }

    public Dado(Integer idDado) {
        this.idDado = idDado;
    }

    public Dado(Integer idDado, String tempo) {
        this.idDado = idDado;
        this.tempo = tempo;
    }

    public Integer getIdDado() {
        return idDado;
    }

    public void setIdDado(Integer idDado) {
        this.idDado = idDado;
    }

    public Integer getSensor() {
        return sensor;
    }

    public void setSensor(Integer sensor) {
        this.sensor = sensor;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDado != null ? idDado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dado)) {
            return false;
        }
        Dado other = (Dado) object;
        if ((this.idDado == null && other.idDado != null) || (this.idDado != null && !this.idDado.equals(other.idDado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Dado[ idDado=" + idDado + " ]";
    }

    public Leitura getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Leitura idLeitura) {
        this.idLeitura = idLeitura;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}