package test.data_structures;
import model.data_structures.ListaEncadenada;
import model.data_structures.Nodo;
import model.logic.Comparendo;
import model.logic.Modelo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;



public class TestListaEncadenada
{

	private ListaEncadenada<Comparendo> lista;
	private Modelo modelo;
	public final String RUTA = "./data/comparendos_dei_2018_small.geojson";

	/**
	 * Lista vacia
	 */
	@Before
	public void setUp1() 
	{
		lista =  new ListaEncadenada<>();
	}

	/**
	 * 
	 * Lista con los datos del json
	 */
	public void setUp2() throws IOException
	{
		try{
			modelo  = new Modelo(true);
			modelo.cargarComparendos(RUTA);
			lista = modelo.darListaEncadenadaDatos();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	@Test
	/**
	 * Prueba que la lista se inicialice bien con el segundo constructor
	 */
	public void testConstructorListaEncadenada()
	{
		Date fecha1 = new Date(1);
		Comparendo nuevo = new Comparendo(1, fecha1, "a", "b", "c", "d", "e", "f", 5456, 465);
		Comparendo nuevo2 = new Comparendo(2, fecha1, "a", "b", "c", "d", "e", "f", 5456, 465);
		
		Nodo<Comparendo> primero = new Nodo<Comparendo>(nuevo);
		Nodo<Comparendo> segundo = new Nodo<Comparendo>(nuevo2);
		
		//Caso en el que se mete nulo el argumento
		lista = new ListaEncadenada<Comparendo>(null);
		assertNull(lista.darPrimero());
		assertNull(lista.darUltimo());
		assertNull(lista.darActual());
		assertEquals(0, lista.size());
		
		lista = null;
		
		//Caso en el que se coloca un nodo y es nulo el siguiente nodo
		lista = new ListaEncadenada<Comparendo>(primero);
		assertEquals(1, lista.size());
		assertEquals(1, lista.darPrimerElemento().darID());
		assertEquals(1, lista.darUltimo().retornarItem().darID());
		assertEquals(1, lista.darActual().retornarItem().darID());
		
		lista = null;
		
		//Caso en el que se coloca un nodo que tiene varios siguientes.
		primero.cambiarSiguiente(segundo);
		lista = new ListaEncadenada<Comparendo>(primero);
		assertEquals(2, lista.size());
		assertEquals(1, lista.darPrimerElemento().darID());
		assertEquals(2, lista.darUltimo().retornarItem().darID());
		
		//cuando son muchos mas nodos que 
		Nodo<Comparendo> actual = primero.darSiguiente();
		for (int i = 3; i < 20; i++)
		{
			Comparendo nuevooo = new Comparendo(i, fecha1, "a", "b", "c", "d", "e", "f", 5456, 465);
			Nodo<Comparendo> elNodo = new Nodo<Comparendo>(nuevooo);
			actual.cambiarSiguiente(elNodo);
			actual = actual.darSiguiente();	
		}
		lista = new ListaEncadenada<Comparendo>(primero);
		assertEquals(19, lista.size());
		actual = primero;
		for (int i= 1; i < 20; i++)
		{
			assertEquals(i, actual.retornarItem().darID());
			assertEquals(1, lista.darPrimerElemento().darID());
			assertEquals(19, lista.darUltimo().retornarItem().darID());
			
			actual = actual.darSiguiente();
			
		}
		
		
	}

	/**
	 * Prueba que la lista esté vacía
	 */
	@Test 
	public void testListaEncadenadaVacia() {

		setUp1();
		assertEquals( "La lista debería tener cero elementos", 0,lista.size());

	}


	/**
	 * Prueba que la lista tenga los 20 objetos del json
	 */
	@Test 
	public void testListaEncadenada() throws IOException
	{
		setUp2();
		assertEquals("La lista debería tener 20 elementos", 20, lista.size());

	}



	@Test
	public void testAgregar() throws IOException 
	{

		setUp2();
		Date fecha = new Date(1);
		double latitud = 9.5;
		double longitud = 15.6;
		Comparendo nuevo = new Comparendo(1, fecha , "b", "c","d" ,"e" , "f","g", latitud, longitud);


		lista.add(nuevo);

		assertEquals("No se agrego", 21, lista.size());
		assertTrue("No se agrego correctamente", lista.darUltimo().retornarItem().equals(nuevo));


	}
	
	
	@Test
	public void testEliminarElemento() throws IOException 
	{

		setUp2();
		int j = lista.size();
		for (int i = 0; i < 20; i++)
		{
			assertNotNull(lista.remove(lista.get(0)));
			j--;
			assertEquals("No esta eliminando bien", lista.size(), j);
		}
		assertTrue("Deberia tener 0 elementos", lista.size()==0);
		
	}

	@Test
	public void testGet() throws IOException
	{
		setUp2();
		Comparendo primerElemento = lista.get(0);
		
		assertEquals("Primer elemento mal recuperado", 29042 ,primerElemento.darID());
		
		Comparendo mitad = lista.get((int)lista.size()/2);
		assertEquals("elemento en la mitad mal recuperado", 264952, mitad.darID());
		
		Comparendo ultimo = lista.get(lista.size() - 1);
		assertEquals("Ultimo elemento mal recuperado", 209146, ultimo.darID());
		
		assertNull(lista.get(-1));
		assertNull(lista.get(lista.size()));
		assertNull(lista.get(lista.size() + 1));
		
	}
	@Test
	public void testContains() throws IOException
	{
		setUp1();
		Date fecha = new Date(1);
		Comparendo noLoContiene = new Comparendo(1, fecha , "b", "c","d" ,"e" , "f","g", 10, 20.1);
		
		assertFalse("No lo deberia contener", lista.contains(noLoContiene));
		
		setUp2();
		assertFalse("No lo deberia contener", lista.contains(noLoContiene));
		
		for (int i = 0; i < 20; i++)
		{
			assertTrue("Lo deberia contener", lista.contains(lista.get(i)));
		}
	}
	
	@Test
	public void testRemoveIndex() throws IOException
	{
		setUp1();
		//no hay nada
		lista.removeIndex(0);
		assertEquals(0, lista.size());
		
		setUp2();
		int j = lista.size();
		for (int i = 0; i < 20; i++)
		{
			lista.removeIndex(0);
			j--;
			assertEquals(lista.size(), j);
		}
	}


}









