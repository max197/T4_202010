package model.data_structures;

public interface IPriorityQueue<T extends Comparable<T>> 
{
	int darNumElementos();
	void agregar (T elemento);
	T sacarMax() throws Exception;
	T darMax() throws Exception;
	boolean esVacia();
	
}
