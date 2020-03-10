package model.data_structures;
import java.lang.Iterable;
import java.util.Iterator;

public class ListaEncadenada<T extends Comparable<T>> implements Iterable<T>, IListaEncadenada<T>
{

	private Nodo<T> primero;
	private int numeroElementos;
	private Nodo<T> actual;
	
	private Nodo<T> ultimo;


	public ListaEncadenada()
	{
		primero = null;	
		actual = null;
		ultimo = null;
		numeroElementos=0;
	}
	
	/**
	 * Genera una lista basandose en el primero nodo que le pasan
	 * @param pPrimero primer nodo. 
	 */
	public ListaEncadenada(Nodo<T> pPrimero)
	{
		if (pPrimero == null)
		{
			primero = null;	
			actual = null;
			ultimo = null;
			numeroElementos=0;
			return;
		}
		actual = pPrimero;
		if (actual.darSiguiente() == null)
		{
			numeroElementos = 1;
			primero = pPrimero;
			actual = pPrimero;
			ultimo = actual;
		}
		else if (actual.darSiguiente() != null)
		{
			primero = pPrimero;
			actual = primero;
			while (actual.darSiguiente() !=null)
			{
				numeroElementos++;	
				actual = actual.darSiguiente();
			}
			numeroElementos++;
			ultimo = actual;
		}
	}


	public void add(T elemento)
	{
		Nodo<T> nuevoNodo = new Nodo<T>(elemento);
		if(primero==null)
		{
			primero = nuevoNodo;
			ultimo = nuevoNodo;
			numeroElementos++;
		}
		else
		{
			ultimo.cambiarSiguiente(nuevoNodo);
			ultimo = ultimo.darSiguiente();
			numeroElementos++;
		}	


	}
	/**
	 * Borra el elemento que entre por parametro
	 * @param elemento 
	 * @return Nulo si no hay elementos, retorna el elemento que elimino. Si hay mas elementos iguales
	 * solo borra el primero.
	 */
	public T remove(T elemento)
	{		
		//Caso en el que el elemento es el primero
		if (primero.retornarItem().equals(elemento))
		{
			T temporal = primero.retornarItem();
			primero = primero.darSiguiente();
			numeroElementos--;
			return temporal;
		
		}
		else
		{
			actual = primero;
			Nodo<T> anterior = null;
			
			while (actual != null)
			{
				if (actual.retornarItem().equals(elemento))
				{
					T temporal = actual.retornarItem();
					anterior.cambiarSiguiente(actual.darSiguiente());
					numeroElementos--;
					return temporal;
				}
				anterior = actual;
				actual = actual.darSiguiente();
			}
			return null;
			
		}
	}


	public int size()
	{
		return numeroElementos;
	}
	
	public T ultimoElemento()
	{
		return ultimo.retornarItem();
	}
	public T darPrimerElemento()
	{
		return primero.retornarItem();

	}

	public Nodo<T> darPrimero()
	{
		return primero;
	}

	
	public Nodo<T> darActual()
	{
		return actual;
	}
	
	public Nodo<T> darUltimo()
	{
		return ultimo;
	}

	
	public void iniciarRecorrido()
	{
		
		actual = primero;
	}
	
	/**
	 * Este metodo retorna el elemento dado con el indice
	 * @param Indice
	 * @return El elemento. Retorna null si el indice es menor a 0 o mayor a los datos que tienen.
	 */
	public T get(int indice)
	{
		if (indice < 0 || indice > numeroElementos-1)
			return null;
		else
		{
			int i = indice;
			actual = primero;
			while (i > 0)
			{
				actual = actual.darSiguiente();
				i--;
			}
			return actual.retornarItem();
		}
	}
	
	/**
	 * Metodo que sirve para saber si contiene un elemento.
	 * @return True si lo contiene, falso si no
	 */
	public boolean contains(T valor)
	{
		actual = primero;
		while(actual != null)
		{
			if (actual.retornarItem().equals(valor))
				return true;
			actual = actual.darSiguiente();

		}
		return false;
	}
	
	
	
	/**
	 * Remueve el que esta en cierta posicion i
	 * @param i. La posicion
	 */
	public void removeIndex(int i )
	{
		if (i < 0 || i > numeroElementos-1)
			return;
		else
		{
			int indice = i;
			Nodo<T> anterior = null;
			actual = primero;
			
			if (indice == 0)
			{
				primero = actual.darSiguiente();
				numeroElementos--;
				return;
			}
			else
			{
				while(indice > 0)
				{
					anterior = actual;
					actual = actual.darSiguiente();
					indice--;
				}
				anterior.cambiarSiguiente(actual.darSiguiente());
				numeroElementos--;
			}
			
		}
		
	}

	
	@Override
	public Iterator<T> iterator()
	{

		Iterator<T> iter = new IteratorNodo();
		return iter;
	}
	protected class IteratorNodo implements Iterator<T>
	{

		protected Nodo<T> n;

		public IteratorNodo()
		{		
			n = new Nodo<T>(null);
			n.cambiarSiguiente(primero);

		}	
		@Override
		public boolean hasNext()
		{
			return n.darSiguiente() != null;

		}

		@Override
		public T next()
		{
			n = n.darSiguiente();
			return n.retornarItem();
		}

		@Override
		public void remove()
		{
			//Por ahora nada;
			System.out.println("Remover elementos es peligroso ");
		}
	}
}
