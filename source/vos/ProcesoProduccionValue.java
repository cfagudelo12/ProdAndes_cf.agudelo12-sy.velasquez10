package vos;

public class ProcesoProduccionValue 
{
public final static String cIdProcesoProduccion = "idProcesoProduccion";
	
	public final static String cDuracion = "duracion";
	
	public final static String cEstado = "estado";
	
	public final static String cIdProducto = "idProducto";
	
	public final static String cIdEmpresa = "idEmpresa";

	private int duracion;
	
	private int idEtapaAnterior;
	
	private String idProducto;
	
	private String idEmpresa;
	
	public ProcesoProduccionValue() {}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public int getIdEtapaAnterior() {
		return idEtapaAnterior;
	}

	public void setIdEtapaAnterior(int idEtapaAnterior) {
		this.idEtapaAnterior = idEtapaAnterior;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
