package tpe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tpe.Procesador;
import tpe.Tarea;

public class CSVReader {

	public CSVReader() {
	}

	public List<Tarea> readTasks(String taskPath) {
		List<Tarea> tareas = new ArrayList<>();
		ArrayList<String[]> lines = this.readContent(taskPath);

		for (String[] line : lines) {
			String id = line[0].trim();
			String nombre = line[1].trim();
			Integer tiempo = Integer.parseInt(line[2].trim());
			Boolean critica = Boolean.parseBoolean(line[3].trim());
			Integer prioridad = Integer.parseInt(line[4].trim());
			tareas.add(new Tarea(id, nombre, tiempo, critica, prioridad));
		}

		return tareas;
	}

	public List<Procesador> readProcessors(String processorPath) {
		List<Procesador> procesadores = new ArrayList<>();
		ArrayList<String[]> lines = this.readContent(processorPath);

		for (String[] line : lines) {
			String id = line[0].trim();
			String codigo = line[1].trim();
			Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
			Integer anio = Integer.parseInt(line[3].trim());
			procesadores.add(new Procesador(id, codigo, refrigerado, anio));
		}

		return procesadores;
	}

	private ArrayList<String[]> readContent(String path) {
		ArrayList<String[]> lines = new ArrayList<>();
		File file = new File(path);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				lines.add(line.split(";"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}
}