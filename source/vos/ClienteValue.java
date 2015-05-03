package vos;

import java.util.ArrayList;

public class ClienteValue extends UsuarioValue
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cIdRepresentanteLegal="idRepresentanteLegal";
	
	public final static String cRegistroSINV="registroSINV";
	
	public final static String cfIdCliente = "idCliente";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private int idRepresentanteLegal;
	
	private String registroSINV;
	
	private ArrayList<PedidoValue> pedidos;

	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public ClienteValue(){
		pedidos=new ArrayList<PedidoValue>();
	}
	
	public ArrayList<PedidoValue> getPedidos(){
		return pedidos;
	}
	
	public int getIdRepresentanteLegal(){
		return idRepresentanteLegal;
	}
	
	public String getRegistroSINV(){
		return registroSINV;
	}
	
	public void agregarPedido(PedidoValue pedido){
		pedidos.add(pedido);
	}
	
	public void setIdRepresentanteLegal(int idRepresentanteLegal){
		this.idRepresentanteLegal=idRepresentanteLegal;
	}
	
	public void setRegistroSINV(String registroSINV){
		this.registroSINV=registroSINV;
	}
}