package vos;

public class EjecucionValue 
{	
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cFechaEjecucion="fechaEjecucion";
	
	public final static String cTiempoEjecucion="tiempoEjecucion";

	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private String fechaEjecucion;
	
	private int tiempoEjecucion;
	
	private EmpleadoValue operario;
	
	private PedidoValue pedido;
	
	private EstacionProduccionValue estacionProduccion;
	
	private EtapaProduccionValue etapaProduccion;
	
	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public EjecucionValue(){}
	
	public String getFechaEjecucion(){
		return fechaEjecucion;
	}
	
	public int getTiempoEjecucion(){
		return tiempoEjecucion;
	}
	
	public EmpleadoValue getOperario(){
		return operario;
	}
	
	public PedidoValue getPedido(){
		return pedido;
	}
	
	public EstacionProduccionValue getEstacionProduccion(){
		return estacionProduccion;
	}
	
	public EtapaProduccionValue getEtapaProduccion(){
		return etapaProduccion;
	}
	
	public void setFechaEjecucion(String fechaEjecucion){
		this.fechaEjecucion=fechaEjecucion;
	}
	
	public void setTiempoEjecucion(int tiempoEjecucion){
		this.tiempoEjecucion=tiempoEjecucion;
	}
	
	public void setOperario(EmpleadoValue operario){
		this.operario=operario;
	}
	
	public void setPedido(PedidoValue pedido){
		this.pedido=pedido;
	}
	
	public void setEstacionProduccion(EstacionProduccionValue estacionProduccion){
		this.estacionProduccion=estacionProduccion;
	}
	
	public void setEtapaProduccion(EtapaProduccionValue etapaProduccion){
		this.etapaProduccion=etapaProduccion;
	}
}