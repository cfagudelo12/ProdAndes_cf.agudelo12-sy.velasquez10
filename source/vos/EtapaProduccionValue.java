package vos;

public class EtapaProduccionValue 
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cIdEtapaProduccion = "idEtapaProduccion";
	
	public final static String cNumeroEtapa = "numeroEtapa";
	
	public final static String cDescripcion = "descripcion";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private int idEtapaProduccion;
	
	private int numeroEtapa;
	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}