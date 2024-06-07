package tpe;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tpe.utils.CSVReader;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
	private Map<String, Tarea> tareas = new HashMap<>();
	/*
     Complejidad computacional: O(2n) ó O(n) (preguntar)
     */
	public Servicios(String pathProcesadores, String pathTareas)
	{
		CSVReader reader = new CSVReader();
		reader.readProcessors(pathProcesadores);
		this.tareas = (Map<String, Tarea>) reader.readTasks(pathTareas);
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
		List<Tarea> resultado = new LinkedList<>();

		if (esCritica) {
			for (String id : tareas.keySet()) {
				Tarea tarea = tareas.get(id);
				boolean cumple = tarea.isEs_critica();
				if (cumple) {
					resultado.add(tarea);
				}
			}
		} else {
			for (String id : tareas.keySet()) {
				Tarea tarea = tareas.get(id);
				boolean cumple = tarea.isEs_critica();
				if (!cumple) {
					resultado.add(tarea);
				}
			}
		}
		return resultado;
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
			if (tarea.getNivel_prioridad() >= prioridadInferior && tarea.getNivel_prioridad() <= prioridadSuperior) {
				resultado.add(tarea);
			}
		}
		return resultado;
	}
}