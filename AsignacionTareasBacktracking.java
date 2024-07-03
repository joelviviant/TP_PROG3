package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsignacionTareasBacktracking {

    private List<Procesador> procesadores;
    private List<Tarea> tareas;
    private int X; // Tiempo máximo de ejecución permitido para procesadores no refrigerados
    private int mejorTiempoMax;
    private Map<Procesador, List<Tarea>> mejorAsignacion;
    private int estadosGenerados;

    public AsignacionTareasBacktracking(List<Procesador> procesadores, List<Tarea> tareas, int X) {
        this.procesadores = procesadores;
        this.tareas = tareas;
        this.X = X;
        this.mejorTiempoMax = Integer.MAX_VALUE;
        this.mejorAsignacion = new HashMap<>();
        this.estadosGenerados = 0;
    }

    public void backtracking() {
        Map<Procesador, List<Tarea>> asignacionActual = new HashMap<>();
        Map<Procesador, Integer> tiemposActuales = new HashMap<>();
        Map<Procesador, Integer> tareasCriticasActuales = new HashMap<>();

        for (Procesador p : procesadores) {
            asignacionActual.put(p, new ArrayList<>());
            tiemposActuales.put(p, 0);
            tareasCriticasActuales.put(p, 0);
        }
        backtrackingRecursivo(0, asignacionActual, tiemposActuales, tareasCriticasActuales);
    }

    private void backtrackingRecursivo(int tareaIndex, Map<Procesador, List<Tarea>> asignacionActual, Map<Procesador, Integer> tiemposActuales, Map<Procesador, Integer> tareasCriticasActuales) {
        estadosGenerados++;
        if (tareaIndex == tareas.size()) {
            int tiempoMax = calcularTiempoMax(tiemposActuales);
            if (tiempoMax < mejorTiempoMax) {
                mejorTiempoMax = tiempoMax;
                mejorAsignacion = new HashMap<>(asignacionActual);
            }
            return;
        }

        Tarea tarea = tareas.get(tareaIndex);
        for (Procesador procesador : procesadores) {
            if (puedeAsignar(procesador, tarea, tiemposActuales, tareasCriticasActuales)) {
                asignacionActual.get(procesador).add(tarea);
                int tiempoNuevo = tiemposActuales.get(procesador) + tarea.getTiempo_ejecucion();
                int tareasCriticasNuevas = tareasCriticasActuales.get(procesador) + (tarea.isEs_critica() ? 1 : 0);
                tiemposActuales.put(procesador, tiempoNuevo);
                tareasCriticasActuales.put(procesador, tareasCriticasNuevas);

                // Poda
                if (calcularTiempoMax(tiemposActuales) < mejorTiempoMax) {
                    backtrackingRecursivo(tareaIndex + 1, asignacionActual, tiemposActuales, tareasCriticasActuales);
                }

                asignacionActual.get(procesador).remove(tarea);
                tiemposActuales.put(procesador, tiemposActuales.get(procesador) - tarea.getTiempo_ejecucion());
                tareasCriticasActuales.put(procesador, tareasCriticasActuales.get(procesador) - (tarea.isEs_critica() ? 1 : 0));
            }
        }
    }

    private boolean puedeAsignar(Procesador procesador, Tarea tarea, Map<Procesador, Integer> tiemposActuales, Map<Procesador, Integer> tareasCriticasActuales) {
        if (tarea.isEs_critica() && tareasCriticasActuales.get(procesador) >= 2) {
            return false;
        }
        if (!procesador.isEsta_refrigerado() && (tiemposActuales.get(procesador) + tarea.getTiempo_ejecucion() > X)) {
            return false;
        }
        return true;
    }

    private int calcularTiempoMax(Map<Procesador, Integer> tiemposActuales) {
        int maxTiempo = 0;
        for (Map.Entry<Procesador, Integer> entry : tiemposActuales.entrySet()) {
            int tiempoTotal = entry.getValue();
            if (!entry.getKey().isEsta_refrigerado() && tiempoTotal > X) {
                tiempoTotal = X;
            }
            if (tiempoTotal > maxTiempo) {
                maxTiempo = tiempoTotal;
            }
        }
        return maxTiempo;
    }

    public void imprimirResultado() {
        System.out.println("Solución obtenida con Backtracking:");
        for (Map.Entry<Procesador, List<Tarea>> entry : mejorAsignacion.entrySet()) {
            System.out.println("Procesador: " + entry.getKey().getId_procesador());
            for (Tarea tarea : entry.getValue()) {
                tarea.getInfo();
            }
        }
        System.out.println("Tiempo máximo de ejecución: " + mejorTiempoMax);
        System.out.println("Estados generados: " + estadosGenerados);
    }
}