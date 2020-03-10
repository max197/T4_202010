package test.data_structures;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.hamcrest.core.IsSame;
import org.junit.Before;
import org.junit.Test;

import model.data_structures.ListaEncadenada;
import model.data_structures.MaxHeapCP;
import model.logic.Comparendo;
import model.logic.Modelo;

public class TestMaxHeapCP 
{
	private MaxHeapCP<Comparendo> colaPrioridad;
	private ListaEncadenada<Comparendo> lista;
	private Modelo modelo;
	public final String RUTA = "./data/comparendos_dei_2018_small.geojson";


	
	/**
	 * Se hace el setUP1 en donde solo se inicializa el MaxHeap
	 */
	@Before
	public void setUp1()
	{
		colaPrioridad = new MaxHeapCP<>(Comparendo.Comparadores.darComparadorLatitud());
	}
	
	/**
	 * 
	 * Se hace el setUp en el cual se cargan los datos en la lista.
	 */
	@Before
	public void setUp2()
	{
		try
		{
			modelo  = new Modelo(true);
			lista = modelo.darListaEncadenadaDatos();
			colaPrioridad = new MaxHeapCP<>(Comparendo.Comparadores.darComparadorLatitud());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	/**
	 * Carga los datos en el max Heap
	 */
	@Before
	public void setUp3()
	{
		setUp2();
		for (Comparendo comparendo : lista) 
		{
			colaPrioridad.agregar(comparendo);
		}
		
	}
	
	

	/**
	 * Prueba que se inicialice bien el max heap.
	 */
	@Test
	public void testConstructorMaxHeap()
	{
		setUp1();
		
		assertEquals("No se inicializo bien el Max heap",0, colaPrioridad.darNumElementos());
		
		try
		{
			colaPrioridad.darMax();
			fail("No debio haber entrado aca sino al catch, se supone que no tiene elementos");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}	
	}

	/**
	 * Testea que dar numero elementos este bien
	 */
	@Test
	public void testDarNumElementos()
	{
		setUp2();
		int i = 0;
		for (Comparendo comparendo : lista) 
		{
			colaPrioridad.agregar(comparendo);
			i++;
			assertEquals(i, colaPrioridad.darNumElementos());
		}
		
	}
	
	/**
	 * Test de agregar
	 */
	@Test
	public void testAgregar()
	{
		setUp2();
		int i = 0;
		for (Comparendo comparendo : lista) 
		{
			colaPrioridad.agregar(comparendo);
			i++;
			assertEquals("No esta agregando bien", i, colaPrioridad.darNumElementos());
			assertTrue("No es un Max Heap", colaPrioridad.isMaxHeap());
		}
		//Para revisar si si lo agrega en el orden que es se hace es con
	}
	
	/**
	 * Prueba que saque el maximo
	 */
	@Test
	public void testSacarMaximo()
	{
		setUp3();
		//Caso en el que no esta vacio y saca el maximo
		while (!colaPrioridad.esVacia())
		{
			try
			{
				Comparendo maximo = colaPrioridad.sacarMax();
				for (Comparendo comparendo : colaPrioridad) 
				{
					if (comparendo.darLatitud() > maximo.darLatitud())
						fail("No saco el maximo. Hay uno mayor en el Heap");
				}
				
			}
			catch (Exception e)
			{
				fail("No deberia entrar a este catch");
			}
		}
		//Caso en el que ya esta vacio
		try
		{
			colaPrioridad.sacarMax();
			fail("Debio entrar al catch porque esta vacio");
		}
		catch (Exception e)
		{
			
		}
		
	}
	/**
	 * Prueba que dar maximo este bien
	 */
	@Test
	public void testDarMaximo()
	{
		setUp3();
		Comparendo maximo;
		try 
		{
			maximo = colaPrioridad.darMax();
			//testea que no lo elimine!
			int i = 0;
			for (Comparendo comparendo : colaPrioridad) 
			{
				if (comparendo.equals(maximo))
					i++;
				//Testea que efectivamente sea el maximo.
				if (comparendo.darLatitud() > maximo.darLatitud())
					fail("No está dando el maximo");
			}
			if (i != 1)
				fail("Elimino el max");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		//En caso de que este vacia:
		while (!colaPrioridad.esVacia())
		{
			try
			{
				colaPrioridad.sacarMax();
			}
			catch(Exception e)
			{
				
			}
		}
		
		try
		{
			maximo = colaPrioridad.sacarMax();
			fail("deberia entrar al catch porque esta vacia");
		}
		catch (Exception e)
		{
			
		}
		
	}
	
	/**
	 * Prueba que el metodo esta vacia esta bien
	 */
	@Test 
	public void testEsVacia() 
	{
		
		
		setUp2();
		assertEquals( "El MaxHeap debería tener cero elementos", true,colaPrioridad.esVacia());
		for (Comparendo comparendo : lista) 
		{
			colaPrioridad.agregar(comparendo);
			assertFalse("La CP no deberia estar vacia, tiene " + colaPrioridad.darNumElementos() ,colaPrioridad.esVacia());
		}

	}
	
	 


	
}
