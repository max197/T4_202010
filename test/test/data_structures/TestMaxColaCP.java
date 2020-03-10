package test.data_structures;


import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


import model.data_structures.ListaEncadenada;
import model.data_structures.MaxColaCP;
import model.logic.Comparendo;
import model.logic.Modelo;



public class TestMaxColaCP 
{


	private MaxColaCP<Comparendo> colaPrioridad;
	private ListaEncadenada<Comparendo> lista;
	private Modelo modelo;
	public final String RUTA = "./data/comparendos_dei_2018_small.geojson";



	//---------------SetUp---------------//


	/**
	 * Se hace el setUP1 en donde solo se inicializa el MaxHeap
	 */
	@Before
	public void setUp1()
	{
		colaPrioridad = new MaxColaCP<>(Comparendo.Comparadores.darComparadorLatitud());
	}


	/**
	 * 
	 * Se hace el setUp2 en el cual se cargan los datos en la lista.
	 */
	@Before
	public void setUp2()
	{
		try
		{
			modelo  = new Modelo(true);
			lista = modelo.darListaEncadenadaDatos();
			colaPrioridad = new MaxColaCP<>(Comparendo.Comparadores.darComparadorLatitud());
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


	//---------------Test---------------//


	/**
	 * Test que el m�todoAgregar a�ada correctamente: Los elementos se a�aden en orden decreciente seg�n su latitud.
	 * i.e Mayor prioridad a los que tienen mayor latidud.
	 * As� Comparendo con mayor latitud est� en la primera entrada de la maxColaCP
	 */
	@Test
	public void testAgregar() 
	{
		setUp3();
		int j=0;
		for(int i=1; i<colaPrioridad.darNumElementos(); i++,j++)
			assertTrue("No est� a�adiendo en orden prioritario",colaPrioridad.darElemento(j).darLatitud()>=colaPrioridad.darElemento(i).darLatitud());	

	}


	/**
	 * 
	 */
	@Test
	public void testDarNumeroElementos()
	{

		setUp1();
		assertTrue(colaPrioridad.darNumElementos()==0);

		setUp3();
		assertTrue(colaPrioridad.darNumElementos()==20);
	}



	/**
	 * 
	 */
	@Test
	public void testSacarMax()
	{
		
		setUp1();
		assertNull(colaPrioridad.sacarMax());//Cuando la priority queue est� vac�a. 

		setUp3();
		Comparendo comparendoMaximo = colaPrioridad.sacarMax();
		
		//Verifica que est� eliminando el elemento de la lista
		assertEquals(19, colaPrioridad.darNumElementos());
		
		//Verifica que efectivamente elimin� el m�ximo (comparendo con mayor latitud)
		for(int i = colaPrioridad.darPrimero() ; i <colaPrioridad.darNumElementos()-1;i++)			
			assertTrue(colaPrioridad.darElemento(i).darLatitud()<=comparendoMaximo.darLatitud());

		
		while(colaPrioridad.darNumElementos()>0)
		{
			colaPrioridad.sacarMax();
		}
		
		assertTrue(colaPrioridad.darNumElementos()==0);//Redundante, con el while es suficiente. 
		
	}


	/**
	 * 
	 */
	@Test
	public void testDarMax()
	{

		//Con una maxColaCP vac�a
		setUp1();
		try
		{
			colaPrioridad.darMax();
			fail("No debi� haber seguido porque la priority queue est� vac�a");
			
		}
		catch(Exception e)
		{
			
		}
		
		//Con una maxColaCP no vac�a
		setUp3();
		try{
			double mayorLatitud = colaPrioridad.darElemento(0).darLatitud();
			for (int i = 1; i < colaPrioridad.darNumElementos(); i++) 
			{
				if(mayorLatitud <colaPrioridad.darElemento(i).darLatitud())
					mayorLatitud = colaPrioridad.darElemento(i).darLatitud();
			}

			assertTrue("No esta sacando el comparendo maximo", mayorLatitud==colaPrioridad.darMax().darLatitud());
		}
		catch(Exception e)
		{
			fail("No deberia entrar ac� porque la priority queue no est� vac�a");
		}
		
		
	}


	/**
	 * 
	 */
	@Test
	public void testEsVacia()
	{

		setUp1();
		assertTrue(colaPrioridad.esVacia());

		setUp3();
		assertFalse(colaPrioridad.esVacia());

	}






}
