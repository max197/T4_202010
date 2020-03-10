package model.logic;

import java.util.Comparator;
import java.util.Date;


public class Comparendo implements Comparable<Comparendo>
{

	private int id;
	
	private Date fechaHora;
	
	private String medio;
	
	private String claseVehiculo;
	
	private String tipoServicio;
	
	private String codigoInfraccion;
	
	private String descripcion;
	
	private String localidad;
	
	private double latitud;
	
	private double longitud;
	
	//Reportar el caso especial en que no exista el comparendo.
	

	public Comparendo(int pId, Date pFechaHora,String pMedio , String pClaseVehiculo, String pTipoServicio, 
			String pCodigoInfraccion, String pDescripcion, String pLocalidad, double pLatitud, 
			double pLongitud)
	{
		
		id = pId;
		
		fechaHora = pFechaHora;
		
		medio = pMedio;
		
		claseVehiculo = pClaseVehiculo;
		
		tipoServicio = pTipoServicio;
		
		codigoInfraccion = pCodigoInfraccion;
		
		descripcion = pDescripcion;
		
		localidad = pLocalidad;
		
		latitud = pLatitud;
		
		longitud = pLongitud;
				
				
	}
	
	public int darID()
	{
		return id;
	}
	
	public Date darFechaHora()
	{
		return fechaHora;
	}
	
	public String darCodigoInfraccion()
	{
		return codigoInfraccion;
	}
	
	public String darClaseVehiculo()
	{
		return claseVehiculo;
	}
	
	public String darTipoServicio()
	{
		return tipoServicio;
	}
	
	public String darLocalidad()
	{
		return localidad;
	}
	
	public String darDescripcion()
	{
		return descripcion;
	}
	
	public double darLatitud()
	{
		return latitud;
	}
	
	public double darLongitud()
	{
		return longitud;
	}

	public String darMedio()
	{
		return medio;
	}
	
	
	
	@Override
	public int compareTo(Comparendo o)
	{
		return this.codigoInfraccion.compareToIgnoreCase(o.codigoInfraccion);

		
	}
	
	@Override 
	public String toString()
	{
		String mensaje =  "Object ID: " + darID() + " - Fecha: " + darFechaHora().toString() + " - Clase Vehiculo: " + darClaseVehiculo() 
		+ "\nLatitud: " + darLatitud() + "- Longitud: " + darLongitud() + "\n";
		
		return mensaje;
	}

	public static class Comparadores 
	{
		public static Comparator<Comparendo> darComparadorFecha() 
		{
			return new Comparator<Comparendo>() 
			{

				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					// TODO Auto-generated method stub
					return o1.fechaHora.compareTo(o2.fechaHora);
				}
			};
		}
		
		public static Comparator<Comparendo> darComparadorInfraccion()
		{
			return new Comparator<Comparendo>() 
			{

				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					// TODO Auto-generated method stub
					return o1.codigoInfraccion.compareTo(o2.codigoInfraccion);
				}
			};
		}
		
		public static Comparator<Comparendo> darComparadorId()
		{
			return new Comparator<Comparendo>() 
			{

				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					// TODO Auto-generated method stub
					return (new Integer(o1.id)).compareTo(o2.id);
				}
			};
		}
		
		public static Comparator<Comparendo> darComparadorLocalidad()
		{
			return new Comparator<Comparendo>() 
			{

				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					// TODO Auto-generated method stub
					return o1.darLocalidad().compareTo(o2.darLocalidad());
				}
			};
		}
		public static Comparator<Comparendo> darComparadorLatitud()
		{
			return new Comparator<Comparendo>() 
			{

				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					return (new Double(o1.darLatitud()).compareTo(o2.darLatitud()));
				}
			};
		}
		
	}
}

