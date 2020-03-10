package model.data_structures;



import java.util.Comparator;
import java.util.Iterator;

public class MaxHeapCP<Key extends Comparable<Key>> implements Iterable<Key>, IPriorityQueue<Key>
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
	 * Comparador
	 */
	private Comparator<Key> comparator;


	//METODOS Constructores

	/**
	 * Inicializa el Heap con la capacidad que entra por parametro
	 */
	public MaxHeapCP(int capacidad)
	{
		pq = (Key[]) new Comparable[capacidad + 1];
		numeroElementos = 0;
	}
	/**
	 * Inicializa con capacidad de 1 + 1.
	 */
	public MaxHeapCP() 
	{
		this(1);
	}

	/**
	 * Inicializa el heap con la capacidad especificada por parametro y un comparator que determina como
	 * se van a comparar los objetos
	 * @param capacidad. Capacidad del arreglo
	 * @param comparator. Comparador por el cual se van a comparar los objetos. 
	 */
	public MaxHeapCP(int capacidad, Comparator<Key> comparator) 
	{
		this.comparator = comparator;
		pq = (Key[]) new Comparable[capacidad + 1];
		numeroElementos = 0;
	}

	/**
	 * Inicializa sin especficar la capacidad. Pero si especificando el comparador.
	 * @param comparator. Revisa la llave bajo la cual se van a comparar las cosas. 
	 */
	public MaxHeapCP(Comparator<Key> comparator) 
	{
		this(1, comparator);
	}

	//---------------------------------------------------------------------------------
	//METODOS
	//---------------------------------------------------------------------------------

	/**
	 * Revisa si esta vacio el Heap
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
		return pq[1];
	}

	/**
	 *Aumenta el tamanio del array 
	 */
	private void resize(int capacidad)
	{
		assert capacidad > numeroElementos;
		Key[] temp = (Key[]) new Comparable[capacidad];
		for (int i = 1; i <= numeroElementos; i++) 
		{
			temp[i] = pq[i];
		}
		pq = temp;
	}


	/**
	 * Inserta un elemento
	 * elemento. Lo que se quiere agregar
	 */
	public void agregar(Key elemento) 
	{
		if (numeroElementos == pq.length - 1)
			resize(2*pq.length);
		//agrega el elemento
		pq[++numeroElementos] = elemento;
		swim(numeroElementos);
		assert isMaxHeap();

	}

	/**
	 * Retorna el maximo y LO ELIMINA.
	 * @return El mayor elemento, tambien lo elimina. 
	 */
	public Key sacarMax() throws Exception
	{
		if (esVacia())
			throw new Exception ("La cola de prioridad está vacia");

		//guardo el mayor elemento
		Key maximo = pq[1];

		exchange(1, numeroElementos--);
		sink(1);

		pq[numeroElementos+1] = null;

		if ((numeroElementos > 0 ) && (numeroElementos == (pq.length - 1)/4))
			resize(pq.length/2);
		assert isMaxHeap();
		return maximo;
	}

	//------------------
	//Metodos de Swim y Sink
	//------------------
	private void swim (int k )
	{
		while (k > 1 && less(k/2, k)) 
		{
			exchange(k, k/2);
			k = k/2;
		}
	}

	private void sink (int k)
	{
		while (2*k <= numeroElementos) 
		{
			int j = 2*k;
			if (j < numeroElementos && less(j, j+1)) 
				j++;
			if (!less(k, j)) 
				break;
			exchange(k, j);
			k = j;
		}
	}

	//------------------
	//Metodos de comparaciones e intercambios
	//------------------
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
	 * 
	 * @param i
	 * @param j
	 */
	private void exchange(int i, int j) 
	{
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	//------------------
	//Metodos de las invariantes.
	//------------------

	/**
	 * Revisa si la estructura es un maxHeap. 
	 * Funciona para el invariante (el assert que hay en muchos metodos).
	 * @return Dice true si si es un max heap.
	 */
	public boolean isMaxHeap() 
	{
		for (int i = 1; i <= numeroElementos; i++) 
		{
			if (pq[i] == null) 
				return false;
		}
		for (int i = numeroElementos+1; i < pq.length; i++) 
		{
			if (pq[i] != null) 
				return false;
		}
		if (pq[0] != null) 
			return false;
		return isMaxHeapOrdered(1);
	}

	// is subtree of pq[1..n] rooted at k a max heap?
	/**
	 * Se realiza la siguiente pregunta: es un subarbol de pq[1...numeroElementos] con raiz k un max heap?
	 * @param k elemento de la raiz.
	 * @return
	 */
	private boolean isMaxHeapOrdered(int k) 
	{
		if (k > numeroElementos) 
			return true;
		int left = 2*k;
		int right = 2*k + 1;
		if (left  <= numeroElementos && less(k, left))  
			return false;
		if (right <= numeroElementos && less(k, right)) 
			return false;
		return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
	}


	//------------------
	//El iterador!
	//------------------

	@Override
	public Iterator<Key> iterator() 
	{
		return new HeapIterator();
	}



	private class HeapIterator implements Iterator<Key> 
	{

		// create a new pq
		private MaxHeapCP<Key> copy;

		// add all items to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() 
		{
			if (comparator == null) 
				copy = new MaxHeapCP<Key>(darNumElementos());
			else                    
				copy = new MaxHeapCP<Key>(darNumElementos(), comparator);
			for (int i = 1; i <= numeroElementos; i++)
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
