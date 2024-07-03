package tpe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsignacionTareasGreedy {

    private List<Procesador> procesadores;
    private List<Tarea> tareas;
    private int X; // Tiempo máximo de ejecución permitido para procesadores no refrigerados
    private int candidatosConsiderados;

    public AsignacionTareasGreedy(List<Procesador> procesadores, List<Tarea> tareas, int X) {
        this.procesadores = procesadores;
        this.tareas = tareas;
        this.X = X;
        this.candidatosConsiderados = 0;
    }

    public void greedy() {
        Map<Procesador, List<Tarea>> asignacion = new HashMap<>();
        Map<Procesador, Integer> tiemposActuales = new HashMap<>();

        for (Procesador p : procesadores) {
            asignacion.put(p, new ArrayList<>());
            tiemposActuales.put(p, 0);
        }

        for (Tarea tarea : tareas) {
            Procesador mejorProcesador = null;
            int menorTiempo = Integer.MAX_VALUE;

            for (Procesador procesador : procesadores) {
                candidatosConsiderados++;
                if (puedeAsignar(procesador, tarea, asignacion, tiemposActuales)) {
                    int tiempoTotal = tiemposActuales.get(procesador) + tarea.getTiempo_ejecucion();
                    if (tiempoTotal < menorTiempo) {
                        menorTiempo = tiempoTotal;
                        mejorProcesador = procesador;
                    }
                }
            }

            if (mejorProcesador != null) {
                asignacion.get(mejorProcesador).add(tarea);
                tiemposActuales.put(mejorProcesador, tiemposActuales.get(mejorProcesador) + tarea.getTiempo_ejecucion());
            }
        }

        imprimirResultado(asignacion);
    }

    private boolean puedeAsignar(Procesador procesador, Tarea tarea, Map<Procesador, List<Tarea>> asignacionActual, Map<Procesador, Integer> tiemposActuales) {
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

        if (!procesador.isEsta_refrigerado() && (tiemposActuales.get(procesador) + tarea.getTiempo_ejecucion() > X)) {
            return false;
        }

        return true;
    }

    private void imprimirResultado(Map<Procesador, List<Tarea>> asignacion) {
        int maxTiempo = calcularTiempoMax(asignacion);
        System.out.println("Solución obtenida con Greedy:");
        for (Map.Entry<Procesador, List<Tarea>> entry : asignacion.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                System.out.println("Procesador: " + entry.getKey().getId_procesador());
                for (Tarea tarea : entry.getValue()) {
                    tarea.getInfo();
                }
            }
        }
        System.out.println("Tiempo máximo de ejecución: " + maxTiempo);
        System.out.println("Candidatos considerados: " + candidatosConsiderados);
    }

    private int calcularTiempoMax(Map<Procesador, List<Tarea>> asignacionActual) {
        int maxTiempo = 0;
        for (Map.Entry<Procesador, List<Tarea>> entry : asignacionActual.entrySet()) {
            int tiempoTotal = 0;
            for (Tarea tarea : entry.getValue()) {
                tiempoTotal += tarea.getTiempo_ejecucion();
            }
            if (tiempoTotal > maxTiempo) {
                maxTiempo = tiempoTotal;
            }
        }
        return maxTiempo;
    }
}