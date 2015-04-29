package vos;

import java.sql.Date;
import java.util.ArrayList;

public class PedidoValue {
	
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cIdPedido = "idPedido";
	
	public final static String cCantidad= "cantidad";
	
	public final static String cMonto = "monto";
	
	public final static String cFechaPedido = "fechaPedido"; 
	
	public final static String cFechaEsperada = "fechaEsperada";
	
	public final static String cFechaLlegada = "fechaLlegada";
	
	public final static String cEstado = "estado";
	
	public final static String terminado = "Terminado";
	
	public final static String pendiente = "Pendiente";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private int idPedido;
	
	private float monto;
	
	private int cantidad;
	
	private Date fechaPedido;
	
	private Date fechaEsperada;
	
	private Date fechaLlegada;
	
	private String estado;
	
	private ArrayList<ProductoValue> productos;
	
	private ArrayList<RecursoValue> recursosRequeridos;
	
	
	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public PedidoValue(){
		productos = new ArrayList<ProductoValue>();
		recursosRequeridos = new ArrayList<RecursoValue>();
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}
	
	public Date getFechaPedido() {
		return fechaPedido;
	}
	
	public void setFechaPedido(Date fechaPedido){
		this.fechaPedido=fechaPedido;
	}
	
	public Date getFechaEsperada() {
		return fechaEsperada;
	}

	public void setFechaEsperada(Date fechaEsperada) {
		this.fechaEsperada = fechaEsperada;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public ArrayList<ProductoValue> getProductos(){
		return productos;
	}
	
	public void agregarProducto(ProductoValue producto){
		productos.add(producto);
	}
	
	public ArrayList<RecursoValue> getRecursosRequeridos(){
		return recursosRequeridos;
	}
	
	public void agregarRecursoRequerido(RecursoValue recurso){
		recursosRequeridos.add(recurso);
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	
}