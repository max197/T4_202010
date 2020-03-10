package model.data_structures;



public class Nodo<E> 
{

	private Nodo<E> siguiente;
	
	private E item;

	public Nodo(E pItem)
	{
		siguiente = null;
		this.item = pItem;
	}
	public Nodo<E> darSiguiente()
	{
		return siguiente;
	}
	
	public void cambiarSiguiente(Nodo<E> pSiguiente)
	{
		this.siguiente =  pSiguiente;
		
	}
	
	public int compareTo(Nodo<E> n)
	{
		if(n.equals(this))
			return 0;
		else
			return -1;
	}
	
	public E retornarItem()
	{
		return item;
	}
	
	public void cambiarItem(E pItem)
	{
		this.item  = pItem;
	}

}
