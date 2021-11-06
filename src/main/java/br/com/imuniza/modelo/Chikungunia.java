package br.com.imuniza.modelo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="chikungunia", indexes = { @Index(name = "idx_codigo", columnList = "codigo") })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigo")
public class Chikungunia implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "codigo")
	private Integer codigo;
	@Column(name = "sintomas")
	private int sintomas;
	@Column(name = "casos")
	private int casos;
	@Column(name = "incidencia")
	private String incidencia;
	@Column(name = "obitos")
	private int obitos;
	@Column(name = "mortalidade")
	private String mortalidade;
	
	public Chikungunia() {
		
	}
	
	public Chikungunia(Integer codigo, int sintomas, int casos, String incidencia, int obitos, String mortalidade) {
		super();
		this.codigo = codigo;
		this.sintomas = sintomas;
		this.casos = casos;
		this.incidencia = incidencia;
		this.obitos = obitos;
		this.mortalidade = mortalidade;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public int getSintomas() {
		return sintomas;
	}
	public void setSintomas(int sintomas) {
		this.sintomas = sintomas;
	}
	public int getCasos() {
		return casos;
	}
	public void setCasos(int casos) {
		this.casos = casos;
	}
	public String getIncidencia() {
		return incidencia;
	}
	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}
	public int getObitos() {
		return obitos;
	}
	
	public void setObito(int obitos) {
		this.obitos = obitos;
	}
	public String getMortalidade() {
		return mortalidade;
	}
	public void setMortalidade(String mortalidade) {
		this.mortalidade = mortalidade;
	}

	@Override
	public String toString() {
		return "Chikungunya [codigo=" + codigo + ", Sintomas=" + sintomas + "]";
	}

	
}
