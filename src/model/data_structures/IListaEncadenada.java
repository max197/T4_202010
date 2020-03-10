package model.data_structures;

public interface IListaEncadenada<T>
{
	/**
	 * Los metodos que deberia permitir una lista:
	 */
	void add (T elemento);
	T remove(T elemento);
	int size();
}
