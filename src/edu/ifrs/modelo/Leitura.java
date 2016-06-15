/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ifrs.modelo;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author ti
 */

public class Leitura implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer idLeitura;
    //private Date dataLeitura;
    private String dataLeitura;
    private int quantidadeSensores;
    private float intervaloEntreAmostras;
    private String descricao;
    private String ip;

    public Leitura() {
    }

    public Leitura(Integer idLeitura, String dataLeitura, int quantidadeSensores, float intervaloEntreAmostras, String descricao, String ip) {
        this.idLeitura = idLeitura;
        this.dataLeitura = dataLeitura;
        this.quantidadeSensores = quantidadeSensores;
        this.intervaloEntreAmostras = intervaloEntreAmostras;
        this.descricao = descricao;
        this.ip = ip;
    }

    public Leitura(Integer idLeitura) {
        this.idLeitura = idLeitura;
    }

    public Leitura(Integer idLeitura, int quantidadeSensores, float intervaloEntreAmostras) {
        this.idLeitura = idLeitura;
        this.quantidadeSensores = quantidadeSensores;
        this.intervaloEntreAmostras = intervaloEntreAmostras;
    }

    public Leitura(int quantidadeSensores, float intervaloEntreAmostras) {
        this.quantidadeSensores = quantidadeSensores;
        this.intervaloEntreAmostras = intervaloEntreAmostras;
    }

    public Integer getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Integer idLeitura) {
        this.idLeitura = idLeitura;
    }

    public String getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(String dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public int getQuantidadeSensores() {
        return quantidadeSensores;
    }

    public void setQuantidadeSensores(int quantidadeSensores) {
        this.quantidadeSensores = quantidadeSensores;
    }

    public float getIntervaloEntreAmostras() {
        return intervaloEntreAmostras;
    }

    public void setIntervaloEntreAmostras(float intervaloEntreAmostras) {
        this.intervaloEntreAmostras = intervaloEntreAmostras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLeitura != null ? idLeitura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leitura)) {
            return false;
        }
        Leitura other = (Leitura) object;
        if ((this.idLeitura == null && other.idLeitura != null) || (this.idLeitura != null && !this.idLeitura.equals(other.idLeitura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Leitura[ idLeitura=" + idLeitura + " ]";
    }

}