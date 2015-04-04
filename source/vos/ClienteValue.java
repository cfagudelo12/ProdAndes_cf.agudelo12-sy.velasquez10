package vos;

public class ClienteValue extends UsuarioValue
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cIdRepresentanteLegal="idRepresentanteLegal";
	
	public final static String cRegistroSINV="registroSINV";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private int idRepresentanteLegal;
	
	private int registroSINV;

	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public ClienteValue(){}
	
	public int getIdRepresentanteLegal(){
		return idRepresentanteLegal;
	}
	
	public int getRegistroSINV(){
		return registroSINV;
	}
	
	public void setIdRepresentanteLegal(int idRepresentanteLegal){
		this.idRepresentanteLegal=idRepresentanteLegal;
	}
	
	public void setRegistroSINV(int registroSINV){
		this.registroSINV=registroSINV;
	}
}