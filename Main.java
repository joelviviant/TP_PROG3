package tpe;

public class Main {
	public static void main(String args[]) {
		Servicios servicios = new Servicios("/direccion del archivo Procesadores.csv", "/direccion del archivo Tareas.csv");
		System.out.println(servicios.servicio3(43, 32));
	}
}