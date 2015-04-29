package vos;

public class EmpleadoValue extends UsuarioValue
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String operario="Operario";
	
	public final static String cSueldo="sueldo";
	
	public final static String cTipoEmpleado="tipoEmpleado";
	
	public final static String cfIdEmpresa="idEmpresa";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private long sueldo;
	
	private String tipoEmpleado;
	
	private int idEmpresa;
	
	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public long getSueldo(){
		return sueldo;
	}
	
	public String getTipoEmpleado(){
		return tipoEmpleado;
	}
	
	public int getIdEmpresa(){
		return idEmpresa;
	}
	
	public void setSueldo(long sueldo){
		this.sueldo=sueldo;
	}
	
	public void setTipoEmpleado(String tipoEmpleado){
		this.tipoEmpleado=tipoEmpleado;
	}
	
	public void setIdEmpresa(int idEmpresa){
		this.idEmpresa=idEmpresa;
	}
}