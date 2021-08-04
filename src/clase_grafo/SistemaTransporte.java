package clase_grafo;

import java.awt.Color;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

import clases.Estacion;
import clases.Linea;
import clases.Ruta;
import postgre_db.PostgreDB;

public class SistemaTransporte {

	private Vector<Estacion> estaciones;
	private Vector<Ruta> rutas;
	private Vector<Linea> lineas;
	private static SistemaTransporte instancia;
	
	public static SistemaTransporte getInstancia() {
		if(instancia == null) {
			instancia = new SistemaTransporte();
		}
		return instancia;
	}
	
	private SistemaTransporte() {
		estaciones = new Vector<Estacion>();
		rutas = new Vector<Ruta>();
		lineas = new Vector<Linea>();
	}
	
	public Vector<Estacion> getEstaciones() {
		return estaciones;
	}

	public Vector<Ruta> getRutas() {
		return rutas;
	}
	
	public Vector<Linea> getLineas(){
		return lineas;
	}
	
	public Optional<Estacion> getEstacion(Integer id) {	
		for(Estacion e : estaciones) {
			if(e.getId() == id) {
				return Optional.of(e);
			}
		}
		
		return Optional.ofNullable(null);
	}
	
	public Optional<Linea> getLinea(Integer id){				
		for(Linea l : lineas) {
			if(l.getId() == id) {
				return Optional.of(l);
			}
		}
		
		return Optional.ofNullable(null);
	}
	
	public Optional<Ruta> getRuta(Integer id){		
		for(Ruta r : rutas) {
			if(r.getId() == id) {
				return Optional.of(r);
			}
		}
		
		return Optional.ofNullable(null);
	}
	
	public Vector<Ruta> rutasEntre(Estacion origen, Estacion destino){
		Vector<Ruta> salida = new Vector<Ruta>();
		
		return rutas.stream().filter(r -> r.getOrigen().equals(origen) && r.getDestino().equals(destino)).collect(Collectors.toCollection(Vector::new));
	}
	
	public void recuperarEstaciones() {
		estaciones = PostgreDB.recuperarEstaciones(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
	}

	public void addEstacion(Integer id, String nombre, LocalTime apertura, LocalTime cierre, boolean operativa) {
		estaciones.add(new Estacion(id,nombre,apertura,cierre,operativa));
	}
	
	public void addAllEstaciones(List<Estacion> lista) {
		estaciones.addAll(lista);
	}
	
	public void recuperarRutas() {
		rutas = PostgreDB.recuperarRutas(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
	}
	
	public void addRuta(Integer id, Integer nombreLinea, Integer origen, Integer destino, Double distancia, Integer duracion, Integer capacidad, boolean activa, double costo) {
		Estacion eOrigen = getEstacion(origen).get();
		Estacion eDestino = getEstacion(destino).get();
		Linea linea = getLinea(nombreLinea).get();
		rutas.add(new Ruta(id,eOrigen,eDestino,linea,distancia,duracion,capacidad,activa,costo));
	}
	
	public void addAllRutas(List<Ruta> lista) {
		rutas.addAll(lista);
	}
	
	public void recuperarLineas() {
		lineas = PostgreDB.recuperarLineas(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
	}
	
	public void addLinea(Integer id, String nombre, Color color, Boolean activa) {
		lineas.add(new Linea(id,nombre,color,activa));
	}
	
	public void addAllLineas(List<Linea> lista) {
		lineas.addAll(lista);
	}
	
	public Integer gradoEntrada(Estacion e) {
		Integer grado = 0;
		
		for(Ruta r : rutas) if(r.getDestino().equals(e)) grado++;
		
		return grado;
	}
	
	public Integer gradoSalida(Estacion e) {
		Integer grado = 0;
		
		for(Ruta r : rutas) if(r.getOrigen().equals(e)) grado++;
		
		return grado;
	}

	public Vector<Estacion> getAdyacentesEntrantes(Estacion destino){
		return rutas.stream().filter(r -> r.getDestino().equals(destino)).map(r -> r.getOrigen()).collect(Collectors.toCollection(Vector::new));
	}
	
	public Vector<Estacion> getAdyacentesSalientes(Estacion origen) {
		return rutas.stream().filter(r -> r.getOrigen().equals(origen)).map(r -> r.getDestino()).collect(Collectors.toCollection(Vector::new));
	}
	
}
