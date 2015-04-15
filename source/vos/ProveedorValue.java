package vos;

import java.util.ArrayList;

public class ProveedorValue extends UsuarioValue
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cIdRepresentanteLegal="idRepresentanteLegal";
	
	public final static String cVolumenMaximo="volumenMaximo";

	public final static String cNombreEmpresa="nombreEmpresa";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private String nombreEmpresa;
	
	private int idRepresentanteLegal;
	
	private int volumenMaximo;
	
	private ArrayList<PedidoValue> pedidos;
	
	private ArrayList<RecursoValue> recursos;
	
	private ArrayList<ProductoValue> productosDependen;
	
	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public ProveedorValue(){
		pedidos = new ArrayList<PedidoValue>();
		recursos = new ArrayList<RecursoValue>();
		productosDependen = new ArrayList<ProductoValue>();
	}
	
	public int getIdRepresentanteLegal(){
		return idRepresentanteLegal;
	}
	
	public int getVolumenMaximo(){
		return volumenMaximo;
	}
	
	public String getNombreEmpresa(){
		return nombreEmpresa;
	}
	
	public ArrayList<PedidoValue> getPedidos(){
		return pedidos;
	}
	
	public ArrayList<RecursoValue> getRecursos(){
		return recursos;
	}
	
	public ArrayList<ProductoValue> getProductosDependen(){
		return productosDependen;
	}
	
	public void setNombreEmpresa(String nombreEmpresa){
		this.nombreEmpresa=nombreEmpresa;
	}
	
	public void setIdRepresentanteLegal(int idRepresentanteLegal){
		this.idRepresentanteLegal=idRepresentanteLegal;
	}
	
	public void setVolumenMaximo(int volumenMaximo){
		this.volumenMaximo=volumenMaximo;
	}
	
	public void agregarPedido(PedidoValue pedido){
		pedidos.add(pedido);
	}
	
	public void agregarRecurso(RecursoValue recurso){
		recursos.add(recurso);
	}
	
	public void agregarProducto(ProductoValue producto){
		productosDependen.add(producto);
	}
}