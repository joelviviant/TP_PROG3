package tpe;

public class Tarea implements Comparable<Tarea>{
    private String id_tarea;
    private String nombre_tarea;
    private int tiempo_ejecucion;
    private boolean es_critica;
    private Integer prioridad;

    public Tarea(String id_tarea, String nombre_tarea, int tiempo_ejecucion, boolean es_critica, Integer prioridad) {
        this.id_tarea = id_tarea;
        this.nombre_tarea = nombre_tarea;
        this.tiempo_ejecucion = tiempo_ejecucion;
        this.es_critica = es_critica;
        this.prioridad = prioridad;
    }

    public void getInfo() {
        System.out.println("Id: " + id_tarea + ", nombre: " + nombre_tarea + ", tiempo de ejecución: " + tiempo_ejecucion + ", es critica: " + es_critica + ", prioridad: " + prioridad);
    }

    public String getId_tarea() {
        return this.id_tarea;
    }

    public String getNombre_tarea() {
        return this.nombre_tarea;
    }

    public int getTiempo_ejecucion() {
        return this.tiempo_ejecucion;
    }

    public boolean isEs_critica() {
        return this.es_critica;
    }

   public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public int compareTo(Tarea otraTarea) {
        return this.prioridad.compareTo(otraTarea.getPrioridad());
    }
}
