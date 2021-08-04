package clase_grafo;

public class Vertex<T> {

	private T value;
	
	public Vertex(){	}
	 
	public Vertex(T v){
		this.value = v;
	}
	
	public void setValue(T v){
		this.value = v;
	}
	
	public T getValue(){
		return this.value;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Vertex other = (Vertex) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
	
}
