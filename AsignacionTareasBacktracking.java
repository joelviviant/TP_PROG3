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

    /*
     La estrategia de Backtracking consiste en asignar recursivamente cada tarea a cada procesador,
     verificando en cada paso que se cumplan las restricciones (no más de dos tareas críticas por procesador
     y no exceder el tiempo máximo permitido para procesadores no refrigerados). Durante el proceso,
     se guarda la mejor asignación encontrada en términos de tiempo máximo de ejecución
     y se lleva un conteo de la cantidad de estados generados para evaluar el costo de la solución.
     */
    public void backtracking() {
        Map<Procesador, List<Tarea>> asignacionActual = new HashMap<>();
        for (Procesador p : procesadores) {
            asignacionActual.put(p, new ArrayList<>());
        }
        backtrackingRecursivo(0, asignacionActual);
    }

    private void backtrackingRecursivo(int tareaIndex, Map<Procesador, List<Tarea>> asignacionActual) {
        estadosGenerados++;
        if (tareaIndex == tareas.size()) {
            int tiempoMax = calcularTiempoMax(asignacionActual);
            if (tiempoMax < mejorTiempoMax) {
                mejorTiempoMax = tiempoMax;
                mejorAsignacion = new HashMap<>(asignacionActual);
            }
            return;
        }

        Tarea tarea = tareas.get(tareaIndex);
        for (Procesador procesador : procesadores) {
            if (puedeAsignar(procesador, tarea, asignacionActual)) {
                asignacionActual.get(procesador).add(tarea);
                backtrackingRecursivo(tareaIndex + 1, asignacionActual);
                asignacionActual.get(procesador).remove(tarea);
            }
        }
    }

    private boolean puedeAsignar(Procesador procesador, Tarea tarea, Map<Procesador, List<Tarea>> asignacionActual) {
        List<Tarea> tareasAsignadas = asignacionActual.get(procesador);
        if (tarea.isEs_critica()) {
            int countCriticas = 0;
            for (Tarea t : tareasAsignadas) {
                if (t.isEs_critica()) {
                    countCriticas++;
                }
            }
            if (countCriticas >= 2) {
                return false;
            }
        }
        if (!procesador.isEsta_refrigerado()) {
            int tiempoTotal = 0;
            for (Tarea t : tareasAsignadas) {
                tiempoTotal += t.getTiempo_ejecucion();
            }
            if (tiempoTotal + tarea.getTiempo_ejecucion() > X) {
                return false;
            }
        }
        return true;
    }

    private int calcularTiempoMax(Map<Procesador, List<Tarea>> asignacionActual) {
        int maxTiempo = 0;
        for (Map.Entry<Procesador, List<Tarea>> entry : asignacionActual.entrySet()) {
            int tiempoTotal = 0;
            for (Tarea tarea : entry.getValue()) {
                tiempoTotal += tarea.getTiempo_ejecucion();
            }
            if (!entry.getKey().isEsta_refrigerado() && tiempoTotal > X) {
                // Si el procesador no está refrigerado y el tiempo total excede X, ajustamos el tiempo máximo
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