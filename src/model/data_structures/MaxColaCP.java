package model.data_structures;

import java.util.*;


public class MaxColaCP<Key extends Comparable<Key>> implements Iterable<Key>,  IPriorityQueue<Key> 
{
	

	//Atributos de la clase

	/**
	 * Guarda los items de los indices del 1 al ultimo
	 */
	private Key[] pq;
	/**
	 * Numero de elementos
	 */
	private int numeroElementos;
	
	/**
	 * Indice del primer elemento en la cola
	 */
	private int first;
	
	private int last;
	
	/**
	 * Comparador
	 */
	private Comparator<Key> comparator;
	
	
	//METODOS Constructores
	
	/**
	 * Inicializa MaxCola con la capacidad que entra por parametro
	 */
	public MaxColaCP(int capacidad)
	{
		pq = (Key[]) new Comparable[capacidad + 1]; //Entonces pq empieza por lo menos con 1 espacio disponible. 
		numeroElementos = 0;
		first = 0;
		last=0;
		
	}
	/**
	 * Inicializa con capacidad de 1 + 1.
	 */
	public MaxColaCP() 
	{
		this(1);
		first = 0;
		last=0;
	}
	
	/**
	 * Inicializa sin especficar la capacidad. Pero si especificando el comparador.
	 * @param comparator. Revisa la llave bajo la cual se van a comparar las cosas. 
	 */
	public MaxColaCP(Comparator<Key> comparator) 
	{
		this(1, comparator);
		first=0;
		last=0;
	}


	/**
	 * Inicializa MaxCola con la capacidad especificada por parametro y un comparator que determina como
	 * se van a comparar los objetos
	 * @param capacidad. Capacidad del arreglo
	 * @param comparator. Comparador por el cual se van a comparar los objetos. 
	 */
	public MaxColaCP(int capacidad, Comparator<Key> comparator) 
	{
		this.comparator = comparator;
		pq = (Key[]) new Comparable[capacidad + 1]; //Entonces pq empieza por lo menos con 1 espacio disponible. 
		numeroElementos = 0;
		first = 0;
		last=0;
	}

	
	//---------------------------------------------------------------------------------
	//METODOS
	//---------------------------------------------------------------------------------

	/**
	 * Revisa si MaxHeap está vacío
	 */
	public boolean esVacia() {
		return numeroElementos == 0;
	}

	/**
	 * Da el tamanio o numero de elementos
	 */
	public int darNumElementos() 
	{
		return numeroElementos;
	}

	/**
	 * Retorna el maximo!
	 */
	public Key darMax() throws Exception
	{
		if (esVacia())
			throw new Exception ("La cola de prioridad está vacia");
		return pq[first];
	}
	
	/**
	 * Inserta un elemento
	 * @param elemento: lo que se quiere agregar
	 */
	public void agregar(Key elemento) 
	{
		
		if (numeroElementos == pq.length)
			resize(2*pq.length);

		
		pq[last] = elemento; 
		int posicionTemp = last;

		
		for(int i = last-1; i>= 0 && less(i,posicionTemp); i--) //Deja ordenado el arreglo de mayor a menor.
		{
			exchange(posicionTemp, i);
			posicionTemp = i;
		}
		
		
		last++;
		
		if(last==pq.length)
			last=0;
		
		numeroElementos++;


	}

	
	/**
	 * Retorna el maximo y LO ELIMINA.
	 * @return El mayor elemento, tambien lo elimina. 
	 */
	public Key sacarMax() 
	{
		if (esVacia())
			return null;

		Key maximo = pq[first];
		pq[first] = null;
		
		numeroElementos--;
		first++;
		
		if(first==pq.length)
			first=0;
		if( numeroElementos>0 && numeroElementos ==pq.length/4)
			resize(pq.length/2);
		
		return maximo;
	
	}
	
	public Key darElemento(int i)
	{
		return pq[i];
	}
	
	public int darPrimero()
	{
		return first;
	}
	
	//---------------------------------------------
	//---------------------------------------------
	//---------------Métodos Privados--------------
	//---------------------------------------------
	//---------------------------------------------
	
	/**
	 *Aumenta el tamanio del array 
	 */
	private void resize(int capacidad)
	{
		assert capacidad > numeroElementos;
		Key[] temp = (Key[]) new Comparable[capacidad];
		for (int i = 0; i < numeroElementos; i++) 
		{
			temp[i] = pq[(first+i)%pq.length];
		}
		pq = temp;
		first= 0;
		last= numeroElementos;
	}

	
	
	
	//Metodos de comparaciones e intercambios
	
	/**
	 * Revisa cuando es mayor o menor, tiene en cuenta el comparator.
	 * @return Cuando i es menor a j retorna true.
	 */
	private boolean less(int i, int j) 
	{
		if (comparator == null) 
		{
			return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
		}
		else 
		{
			return comparator.compare(pq[i], pq[j]) < 0;
		}
	}
	
	
	/**
	 * Intercambia posición i y j.
	 * @param i
	 * @param j
	 */
	private void exchange(int i, int j) 
	{
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}
	

	
	@Override
	public Iterator<Key> iterator() 
	{
		return new ColaIterator();
	}
	
	

	private class ColaIterator implements Iterator<Key>
	{

		// create a new pq
		private MaxColaCP<Key> copy;

		// add all items to copy of heap
		// takes linear time since already in heap order so no keys move		
		public ColaIterator()
		{
			if(comparator==null)
				copy = new MaxColaCP<Key>(darNumElementos());
			else
				copy = new MaxColaCP<Key>(darNumElementos(), comparator);
			for(int i=1; i<=numeroElementos;i++)
				copy.agregar(pq[i]);
		}

		public boolean hasNext()  
		{ 
			return !copy.esVacia();                
		}
		public void remove()      
		{ 
			throw new UnsupportedOperationException(); 
		}

		public Key next()
		{
			try
			{
				return copy.sacarMax();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}
		}
	}
	
	
	
	
}
