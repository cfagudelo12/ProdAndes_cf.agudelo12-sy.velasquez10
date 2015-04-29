package vos;

public class EtapaProduccionValue 
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cIdEtapaProduccion = "idEtapaProduccion";
	
	public final static String cNumeroEtapa = "numeroEtapa";
	
	public final static String cIdAnteriorEtapa = "idAnteriorEtapa";
	
	public final static String cIdProcesoProduccion = "idProcesoProduccion";
	
	
	public final static String cDescripcion = "descripcion";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private int idEtapaProduccion;
	
	private int numeroEtapa;
	
	private int idEtapaAnterior;
	
	private int idProcesoProduccion;
	
	private String descripcion;
	
	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public EtapaProduccionValue() {}

	public int getIdEtapaProduccion() {
		return idEtapaProduccion;
	}

	public void setIdEtapaProduccion(int idEtapaProduccion) {
		this.idEtapaProduccion = idEtapaProduccion;
	}

	public int getNumeroEtapa() {
		return numeroEtapa;
	}

	public void setNumeroEtapa(int numeroEtapa) {
		this.numeroEtapa = numeroEtapa;
	}

	public int getIdEtapaAnterior() {
		return idEtapaAnterior;
	}

	public void setIdEtapaAnterior(int idEtapaAnterior) {
		this.idEtapaAnterior = idEtapaAnterior;
	}

	public int getIdProcesoProduccion() {
		return idProcesoProduccion;
	}

	public void setIdProcesoProduccion(int idProcesoProduccion) {
		this.idProcesoProduccion = idProcesoProduccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
