package vos;

public class EstacionProduccionValue 
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------

	public final static String activa = "Activa";

	public final static String inactiva = "Inactiva";

	public final static String cIdEstacionProduccion="idEstacionProduccion";

	public final static String cCapacidadProduccion="capacidadProduccion";

	public final static String cNombre="nombre";

	public final static String cEstado="estado";

	public final static String cIdEmpresa="idEmpresa";

	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------

	private int idEstacionProduccion;

	private int capacidadProduccion;

	private String nombre;

	private String estado;

	private int idEmpresa;

	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------

	public EstacionProduccionValue(){}

	public int getIdEstacionProduccion() {
		return idEstacionProduccion;
	}

	public void setIdEstacionProduccion(int idEstacionProduccion) {
		this.idEstacionProduccion = idEstacionProduccion;
	}

	public int getCapacidadProduccion() {
		return capacidadProduccion;
	}

	public void setCapacidadProduccion(int capacidadProduccion) {
		this.capacidadProduccion = capacidadProduccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getIdEmpresa(){
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
}
