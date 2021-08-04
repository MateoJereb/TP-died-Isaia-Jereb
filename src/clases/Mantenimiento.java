package clases;

import java.time.LocalDateTime;

public class Mantenimiento {

	private Integer id;
	private Estacion estacion;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private String observaciones;
	
	public Mantenimiento(Integer id, Estacion estacion, LocalDateTime fechaInicio, LocalDateTime fechaFin, String observaciones) {
		this.id = id;
		this.estacion = estacion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.observaciones = observaciones;
	}
	
	public Integer getId() {
		return id;
	}

	public Estacion getEstacion() {
		return estacion;
	}
	
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void finalizar(LocalDateTime fin) {
		this.fechaFin = fin;
	}
	
}
