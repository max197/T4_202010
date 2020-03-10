package main;
import controller.Controller;

public class Main {
	
	public static void main(String[] args) 
	{
		System.out.println("Esta implementacion, al correrse carga inmediatamente los datos a la LISTA");
		System.out.println("Cargando...");
		Controller controler = new Controller();
		controler.run();
	}
}
