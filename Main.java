package tpe;

import java.util.List;
import tpe.utils.CSVReader;

public class Main {

	public static void main(String[] args) {
		// Crear instancias del lector CSV
		CSVReader reader = new CSVReader();

		// Leer los procesadores y las tareas desde los archivos CSV
		List<Procesador> procesadores = reader.readProcessors("/home/jowi/IdeaProjects/TP_PROG3/datasets/Procesadores.csv");
		List<Tarea> tareas = reader.readTasks("/home/jowi/IdeaProjects/TP_PROG3/datasets/Tareas.csv");

		int tiempoMaxNoRefrigerado = 70;


		/*AsignacionTareasBacktracking backtracking = new AsignacionTareasBacktracking(procesadores, tareas, tiempoMaxNoRefrigerado);

		// Ejecutar el algoritmo de Backtracking
		backtracking.backtracking();

		// Imprimir los resultados
		backtracking.imprimirResultado();


		 */
		AsignacionTareasGreedy greedy = new AsignacionTareasGreedy(procesadores, tareas, tiempoMaxNoRefrigerado);

		greedy.greedy();

	}
}