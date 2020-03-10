package test.logic;

import static org.junit.Assert.*;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import model.data_structures.ListaEncadenada;
import model.data_structures.MaxHeapCP;
import model.logic.Comparendo;
import model.logic.Modelo;

import org.junit.Before;
import org.junit.Test;

public class TestModelo 
{
	
	private Modelo modelo;
	
	@Before
	public void setUp1() 
	{
		modelo= new Modelo(true);
	}

	

	@Test
	public void testModelo() 
	{
		setUp1();
		assertTrue(modelo!=null);
		assertEquals(20, modelo.darTamano());  // Modelo con 20 elementos presentes.
	}

	@Test
	public void testDarTamano() 
	{
		setUp1();
		assertEquals("El tamanio deberia ser 20 y es " + modelo.darTamano(), 20, modelo.darTamano());
	}
	
	@Test
	public void testCargarComparendosEnColasDePrioridad()
	{
		setUp1();
		//Caso en el que no se puede!
		try 
		{
			modelo.cargarComparendosMaxHeapCP(30);
			fail("No se puede con un N de 30 porque solo hay 20 elementos");
			//
		} 
		catch (Exception e) 
		{
			
		}
		//Caso en el que si se puede
		try 
		{
			modelo.cargarComparendosMaxHeapCP(20);
			MaxHeapCP<Comparendo> MH = modelo.darMaxHeapCP();
			//tiene 20 elementos?
			assertEquals(20, MH.darNumElementos());
			
			//se mantiene como un max heap?
			assertTrue(MH.isMaxHeap());
			
			Comparendo anterior = null;
			
			for (Comparendo comparendo : MH) 
			{
				if (anterior == null)
					anterior = comparendo;
				else
				{
					if (anterior.equals(comparendo))
						fail ("No pueden haber repetidos");
					anterior = comparendo;
				}
			}	
		} 
		catch (Exception e) 
		{
			fail("No debio haber entrado aca.");
			
		}
		
	}
	@Test
	public void testNComparendosMasAlNorteMaxHeap()
	{
		setUp1();
		try 
		{
			modelo.cargarComparendosMaxHeapCP(20);
			String [] tiposVehiculos = new String [2];
			tiposVehiculos[0] ="AUTOMÓVIL" ;
			tiposVehiculos[1] = "CAMPERO";
			
			
			MaxHeapCP<Comparendo> maxHeapPrueba = modelo.NComparendosMasAlNorteMaxHeap(10, tiposVehiculos);
			//Se esperan 9 porque solo nueve cumplen estas caracteristicas por mas que N sea 10
			assertEquals(9, maxHeapPrueba.darNumElementos());
			assertTrue(maxHeapPrueba.isMaxHeap());
			
			for (Comparendo comparendo : maxHeapPrueba) 
			{
				if (!(comparendo.darClaseVehiculo().equals("AUTOMÓVIL") || comparendo.darClaseVehiculo().equals("CAMPERO")))
				{
					fail("entro un comparendo de otra clase de vehiculo");
				}
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			fail("No debio haber entrado aca");
		}
		
	}

	
}
