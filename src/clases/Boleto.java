package clases;

import java.time.LocalDateTime;
import java.util.List;

public class Boleto {

	private Integer nro;
	private Estacion origen;
	private Estacion destino;
	private String camino;
	private String emailCliente;
	private String nombreCliente;
	private LocalDateTime fechaVenta;
	private Double costo;
	
	public Boleto(Integer nro, Estacion origen, Estacion destino, String camino, String emailCliente, String nombreCliente, LocalDateTime fechaVenta, Double costo) {
		this.nro = nro;
		this.origen = origen;
		this.destino = destino;
		this.camino = camino;
		this.emailCliente = emailCliente;
		this.nombreCliente = nombreCliente;
		this.fechaVenta = fechaVenta;
		this.costo = costo;
	}

	public Integer getNro() {
		return nro;
	}

	public Estacion getOrigen() {
		return origen;
	}

	public Estacion getDestino() {
		return destino;
	}

	public String getCamino() {
		return camino;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public LocalDateTime getFechaVenta() {
		return fechaVenta;
	}

	public Double getCosto() {
		return costo;
	}
	
	
	
}
