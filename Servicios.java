package tpe;

import java.util.*;

import tpe.utils.CSVReader;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
	private Map<String, Tarea> tareas = new HashMap<>();
	private LinkedList<Tarea> tareasCriticas;
	private LinkedList<Tarea> tareasNoCriticas;
	private ArrayList<Tarea> tareasPrioridad;


	public Servicios(String pathProcesadores, String pathTareas)
	{
		CSVReader reader = new CSVReader();
		this.tareasCriticas = new LinkedList<>();
		this.tareasNoCriticas = new LinkedList<>();
		this.tareasPrioridad = new ArrayList<>();
		reader.readProcessors(pathProcesadores);
		this.inicializarEstructuras(reader.readTasks(pathTareas));
	}


	private void inicializarEstructuras(List<Tarea> tareas) {
		for (Tarea t : tareas) {
			if (t.isEs_critica()) // Listas de tareas críticas y no críticas
				this.tareasCriticas.add(t);
			else
				this.tareasNoCriticas.add(t);

			this.tareas.put(t.getId_tarea(), t); // Hashmap por id
			this.tareasPrioridad.add(t); // Lista por prioridad
		}

		Collections.sort(this.tareasPrioridad);
	}
	
	/*
     Complejidad computacional: O(1)
    */
	public Tarea servicio1(String ID) {
		return tareas.get(ID);
	}
    
    /*
	  Complejidad computacional: O(n)
     */
	public List<Tarea> servicio2(boolean esCritica) {
		if (esCritica)
			return this.tareasCriticas;
		else
			return this.tareasNoCriticas;
	}

    /*
      Complejidad computacional: O(n)
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		List<Tarea> resultado = new LinkedList<>();
		if (prioridadInferior > prioridadSuperior) {
			System.out.println("ERROR, la prioridad inferior debe ser menor que la prioridad superior");
			return resultado;
		}
		for (String id : tareas.keySet()) {
			Tarea tarea = tareas.get(id);
			if (tarea.getPrioridad() >= prioridadInferior && tarea.getPrioridad() <= prioridadSuperior) {
				resultado.add(tarea);
			}
		}
		return resultado;
	}
}