package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap<Traslado> heapRedituables; // Heap de traslados ordenados por ganancia
    private Heap<Traslado> heapAntiguedad; // Heap de traslados ordenados por antiguedad
    private Ciudad[] ciudadesInfo; // Guarda la informacion de las ciudades 
    private Heap<Ciudad> heapSuperavit; // Heap de ciudades ordenados por superavit
    private ArrayList<Integer> ciudadesConMayorGanancia; // Lista de ciudades con mayor ganancia
    private ArrayList<Integer> ciudadesConMayorPerdida; // Lista de ciudades con mayor perdida
    private int maxGanancia; // Guarda la mayor ganancia de una ciudad
    private int maxPerdida; // Guarda la mayor perdida de una ciudad
    private int gananciaTotal; // Contador ganancia por traslados despachados
    private int despachados; // Contador de traslados despachados


    // Sea T = Conjunto de traslados
    // Sea C = Conjunto de ciudades

    public BestEffort(int cantCiudades, Traslado[] traslados){ // Complejidad: O(|C| + |T|)

        // Inicializa las listas de traslados para armar el heap
        ArrayList<HeapElement<Traslado>> listaTrasladosGanancia = new ArrayList<>();
        ArrayList<HeapElement<Traslado>> listaTrasladosAntiguedad = new ArrayList<>();

        // Procesa cada traslado para asignarle identificadores unicos y los añade a las listas correspondientes
        // Todas las operaciones realizadas en cada iteracion son O(1)
        for (int i = 0; i < traslados.length; i++) { // O(T)
            Traslado traslado = traslados[i];
            traslado.setIdGanancia(i);
            traslado.setIdAntiguedad(i);
            HeapElement<Traslado> trasladoRedituable = new HeapElement<Traslado>(traslado.getHandleGanancia(), traslado);
            HeapElement<Traslado> trasladoAntiguedad = new HeapElement<Traslado>(traslado.getHandleAntiguedad(), traslado);
            listaTrasladosGanancia.add(trasladoRedituable);
            listaTrasladosAntiguedad.add(trasladoAntiguedad);
        }
        this.heapRedituables = new Heap<Traslado>(listaTrasladosGanancia, new Traslado.ComparadorGananciaNeta()); // O(|T|) por algoritmo de Floyd
        this.heapAntiguedad = new Heap<Traslado>(listaTrasladosAntiguedad, new Traslado.ComparadorAntiguedad()); // O(|T|) por algoritmo de Floyd
        this.ciudadesInfo = new Ciudad[cantCiudades];
        ArrayList<HeapElement<Ciudad>> listaSuperavit = new ArrayList<>();

        // Todas las operaciones realizadas en cada iteracion son O(1)
        for(int i = 0; i < cantCiudades; i++){ // O(|C|)
            Ciudad ciudad = new Ciudad(i);
            ciudad.setIdSuperavit(i);
            ciudadesInfo[i] = ciudad;
            HeapElement<Ciudad> ciudadSuperavit = new HeapElement<Ciudad>(ciudad.getHandleSuperavit(), ciudad);
            listaSuperavit.add(ciudadSuperavit);
        }
        this.heapSuperavit = new Heap<Ciudad>(listaSuperavit, new Ciudad.ComparadorSuperavit()); // O(|C|) por algoritmo de Floyd
        this.ciudadesConMayorGanancia = new ArrayList<Integer>();
        this.ciudadesConMayorPerdida = new ArrayList<Integer>();
        this.maxGanancia = 0;
        this.maxPerdida = 0;
        this.gananciaTotal = 0;
        this.despachados = 0;
    }

    public void registrarTraslados(Traslado[] traslados){ // O(|traslados| log(|T|)
        for(int i = 0; i < traslados.length; i++){ // O(|traslados|)
            Traslado traslado = traslados[i];
            heapRedituables.insertar(new HeapElement<Traslado>(traslado.getHandleGanancia(), traslado)); // O(log T)
            heapAntiguedad.insertar(new HeapElement<Traslado>(traslado.getHandleAntiguedad(), traslado)); // O(log T)
        }
    }

    // Extrae n veces un elemento de ambos heaps de traslado, actualiza las estadisticas y reacomoda heapSuperavit
    public int[] despacharMasRedituables(int n){ // O(n (log(T) + log(C)))
        int m = heapRedituables.tamaño(); 
        if (n > m){ 
            n = m; 
        }
        int[] despachos = new int[n];
        for(int i = 0; i < n; i++){ // O(n)
            HeapElement<Traslado> despacho = heapRedituables.extraerMax(); // O(log(|T|)) 
            heapAntiguedad.eliminar(despacho.getValor().getIdAntiguedad()); // O(log(|T|))
            despachos[i] = despacho.getValor().getId(); 
            actualizarEstadisticas(despacho); // O(log (|C|)
        }
        return despachos; 
    }

    // Extrae n veces un elemento de ambos heaps de traslado, actualiza las estadisticas y reacomoda heapSuperavit
    public int[] despacharMasAntiguos(int n){ // O(n (log(|T|) + log(|C|)))
        int m = heapAntiguedad.tamaño(); 
        if (n > m){ 
            n = m; 
        }
        int[] despachos = new int[n];
        for(int i = 0; i < n; i++){ // O(n)
            HeapElement<Traslado> despacho = heapAntiguedad.extraerMax(); // O(log(|T|))
            heapRedituables.eliminar(despacho.getValor().getIdGanancia()); // O(log(|T|))
            despachos[i] = despacho.getValor().getId();
            actualizarEstadisticas(despacho); // O(log(|C|))
        }
        return despachos; 
    }

    public int ciudadConMayorSuperavit(){ // O(1)
        Ciudad max = heapSuperavit.consultarMax().getValor();
        int id = max.getId();
        return id;
    } 

    public ArrayList<Integer> ciudadesConMayorGanancia(){ // O(1)
        return ciudadesConMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){ // O(1) 
        return ciudadesConMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){ // O(1)
        return gananciaTotal / despachados;
    } 


    // FUNCIONES AUXILIARES

    private void actualizarEstadisticas(HeapElement<Traslado> despacho){ // O(log(|C|) 
        Ciudad origen = ciudadesInfo[despacho.getValor().getOrigen()]; 
        Ciudad destino = ciudadesInfo[despacho.getValor().getDestino()]; 
        int gananciaPorTraslado = despacho.getValor().getGananciaNeta(); 
        // Actualizamos los contadores
        gananciaTotal += gananciaPorTraslado; 
        despachados += 1; 
        // Actualizamos la informacion de las ciudades
        origen.setGanancia(gananciaPorTraslado); 
        destino.setPerdida(gananciaPorTraslado); 
        // Actualizamos las estadisticas de ciudades
        actualizarCiudadConMayorGanancia(origen.getId());
        actualizarCiudadConMayorPerdida(destino.getId());
        heapSuperavit.actualizarEnIndice(origen.getIdSuperavit()); // O(log(|C|)) reacomoda heapSuperavit
        heapSuperavit.actualizarEnIndice(destino.getIdSuperavit()); // O(log (|C|)) reacomoda heapSuperavit
        maxGanancia = ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia(); 
        maxPerdida = ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida();
    }

    private void actualizarCiudadConMayorGanancia(int ciudad) { // O(1)
        int gananciaCiudad = ciudadesInfo[ciudad].getGanancia();
        if (ciudadesConMayorGanancia.size() == 0 || (gananciaCiudad == ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia() && gananciaCiudad == maxGanancia)) {
            ciudadesConMayorGanancia.add(ciudad); 
        } else if (gananciaCiudad >= ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia() && gananciaCiudad != maxGanancia) { 
            ciudadesConMayorGanancia.clear(); 
            ciudadesConMayorGanancia.add(ciudad);
        }
    }

    private void actualizarCiudadConMayorPerdida(int ciudad) { // O(1)
        int perdidaCiudad = ciudadesInfo[ciudad].getPerdida(); 
        if (ciudadesConMayorPerdida.size() == 0 || (perdidaCiudad == ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida() && perdidaCiudad == maxPerdida)) {
            ciudadesConMayorPerdida.add(ciudad); 
        } else if (perdidaCiudad >= ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida() && perdidaCiudad != maxPerdida) {
            ciudadesConMayorPerdida.clear(); 
            ciudadesConMayorPerdida.add(ciudad);
        }
    }


}
