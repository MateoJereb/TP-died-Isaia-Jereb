package clases;

import java.time.LocalTime;

public class Estacion {

	private Integer id;
	private String nombre;
	private LocalTime apertura;
	private LocalTime cierre;
	private Boolean operativa; //true = operativa, false = mantenimiento
	
	public Estacion(Integer id, String nombre, LocalTime apertura, LocalTime cierre, boolean operativa) {
		this.id = id;
		this.nombre = nombre;
		this.apertura = apertura;
		this.cierre = cierre;
		this.operativa = operativa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalTime getApertura() {
		return apertura;
	}

	public void setApertura(LocalTime apertura) {
		this.apertura = apertura;
	}

	public LocalTime getCierre() {
		return cierre;
	}

	public void setCierre(LocalTime cierre) {
		this.cierre = cierre;
	}
	
	public Boolean operativa() {
		return operativa;
	}
	
	public void abrir() {
		operativa = true;
	}
	
	public void mantenimiento() {
		operativa = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apertura == null) ? 0 : apertura.hashCode());
		result = prime * result + ((cierre == null) ? 0 : cierre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estacion other = (Estacion) obj;
		if (apertura == null) {
			if (other.apertura != null)
				return false;
		} else if (!apertura.equals(other.apertura))
			return false;
		if (cierre == null) {
			if (other.cierre != null)
				return false;
		} else if (!cierre.equals(other.cierre))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ("["+id+" "+nombre+" "+apertura+" "+cierre+" "+operativa+"]");
	}
	
}
