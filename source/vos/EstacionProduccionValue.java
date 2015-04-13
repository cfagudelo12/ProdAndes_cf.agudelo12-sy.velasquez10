package vos;

public class EstacionProduccionValue 
{
		//----------------------------------------------------
		// Constantes
		//----------------------------------------------------
		
		public final static String cIdEstacionProduccion="idEstacionProduccion";
		
		public final static String cCapacidadProduccion="capacidadProduccion";
		
		public final static String cIdEtapaProduccion="idEtapaProduccion";
		
		//----------------------------------------------------
		// Atributos
		//----------------------------------------------------
		
		private int idEstacionProduccion;
		
		private int capacidadProduccion;
		
		private int idEtapaProduccion;
		
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

		public int getIdEtapaProduccion() {
			return idEtapaProduccion;
		}

		public void setIdEtapaProduccion(int idEtapaProduccion) {
			this.idEtapaProduccion = idEtapaProduccion;
		}
}
