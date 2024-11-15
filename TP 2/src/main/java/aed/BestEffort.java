package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap trasladosGanancias; // Max-heap ordenado por ganancia
    private Heap trasladosAntiguedad; // Min-heap ordenado por antiguedad
    private Ciudad[] ciudadesInfo; // Guarda la informacion de las ciudades 
    private int ciudadConMayorSuperavit; // Almacena temporalmente las estadisticas de superavit y se actualiza tras cada despacho 
    private ArrayList<Integer> ciudadesConMayorGanancia; // Almacena temporalmente las estadisticas de ganancia y se actualiza tras cada despacho 
    private ArrayList<Integer> ciudadesConMayorPerdida; // Almacena temporalmente las estadisticas de perdida y se actualiza tras cada despacho 
    private int cantMayorGanancia; // Guarda la cantidad de la ciudad con mayor ganancia
    private int cantMayorPerdida; // Guarda la cantidad de la ciudad con mayor perdida
    private int gananciaTotal; // Contador ganancia
    private int cantTraslados; // Contador traslados despachados


    public BestEffort(int cantCiudades, Traslado[] traslados){ // O(|C| + |T|). Construir el array infoCiudades es |C| y construir los heaps es |T| 
        this.trasladosGanancias = new Heap(traslados, new Traslado.ComparadorGananciaNeta(),true);
        this.trasladosAntiguedad = new Heap(traslados, new Traslado.ComparadorAntiguedad(), false);
        this.ciudadesInfo = new Ciudad[cantCiudades];
        for(int i = 0; i < cantCiudades; i++){
            ciudadesInfo[i] = new Ciudad(i);
        }
        this.ciudadConMayorSuperavit = -1;
        this.ciudadesConMayorGanancia = new ArrayList<Integer>();
        this.ciudadesConMayorPerdida = new ArrayList<Integer>();
        this.cantMayorGanancia = 0;
        this.cantMayorPerdida = 0;
        this.gananciaTotal = 0;
        this.cantTraslados = 0;
    }

    public void registrarTraslados(Traslado[] traslados){ // Complejidad: O(|traslados| log(|T|)) Insertar en el heap es log(|T|) y lo hacemos |traslados| veces
        for(int i = 0; i < traslados.length; i++){
            trasladosGanancias.insertar(traslados[i]);
            trasladosAntiguedad.insertar(traslados[i]);
        }
    }

    public int[] despacharMasRedituables(int n){ // Complejidad: O(n log(|T|) Despachar del heap es log(|T|) y lo hacemos n veces. ActualizarEstadisticas es O(1) pues unicamente realiza operaciones elementales.
        int m = trasladosGanancias.tamaño();
        if (n > m){
            n = m;
        }
        int[] despachos = new int[n];
        for(int i = 0; i < n; i++){
            Traslado traslado = trasladosGanancias.despachar(); 
            trasladosAntiguedad.despacharEnIndice(traslado.getIdAntiguedad()); // Despacha en trasladosAntiguedad con el handle correspondiente.
            despachos[i] = traslado.getId();
            actualizarEstadisticas(traslado);
        }
        return despachos;
    }

    public int[] despacharMasAntiguos(int n){ // Complejidad: O(n log(|T|) Despachar del heap es log(|T|) y eso lo hacemos n veces. Actualizar las estadisticas es O(1) pues unicamente realiza operaciones elementales.
        int m = trasladosAntiguedad.tamaño();
        if (n > m){
            n = m;
        }
        int[] despachos = new int[n];
        for(int i = 0; i < n; i++){
            Traslado traslado = trasladosAntiguedad.despachar();
            trasladosGanancias.despacharEnIndice(traslado.getIdGanancia());
            despachos[i] = traslado.getId();
            actualizarEstadisticas(traslado);
        }
        return despachos;
    }
    private void actualizarEstadisticas(Traslado traslado){ // Complejidad: O(1). Actualiza contadores, la info de las ciudades comprometidas y estadisticas de las ciudades
        int gananciaPorTraslado = traslado.getGananciaNeta();
        gananciaTotal += gananciaPorTraslado;
        cantTraslados += 1;
        int origen = traslado.getOrigen();
        int destino = traslado.getDestino();
        ciudadesInfo[origen].setGanancia(gananciaPorTraslado); 
        ciudadesInfo[destino].setPerdida(gananciaPorTraslado);
        actualizarCiudadConMayorGanancia(origen);
        actualizarCiudadConMayorPerdida(destino);
        actualizarCiudadConMayorSuperavit(origen);
        actualizarCiudadConMayorSuperavit(destino);
        cantMayorGanancia = ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia();
        cantMayorPerdida = ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida();
    }


    private void actualizarCiudadConMayorGanancia(int ciudad) { // O(1)
        int gananciaCiudad = ciudadesInfo[ciudad].getGanancia();
        if (ciudadesConMayorGanancia.size() == 0 || (gananciaCiudad == ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia() && gananciaCiudad == cantMayorGanancia)) {
            ciudadesConMayorGanancia.add(ciudad);
        } else if (gananciaCiudad >= ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia() && gananciaCiudad != cantMayorGanancia) {
            ciudadesConMayorGanancia.clear();
            ciudadesConMayorGanancia.add(ciudad);
        }
    }

    private void actualizarCiudadConMayorPerdida(int ciudad) { // O(1)
        int perdidaCiudad = ciudadesInfo[ciudad].getPerdida();
        if (ciudadesConMayorPerdida.size() == 0 || (perdidaCiudad == ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida() && perdidaCiudad == cantMayorPerdida)) {
            ciudadesConMayorPerdida.add(ciudad);
        } else if (perdidaCiudad >= ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida() && perdidaCiudad != cantMayorPerdida) {
            ciudadesConMayorPerdida.clear();
            ciudadesConMayorPerdida.add(ciudad);
        }
    }

    private void actualizarCiudadConMayorSuperavit(int ciudad) { // O(1)
        Ciudad.ComparadorSuperavit comparadorSuperavit = new Ciudad.ComparadorSuperavit();
        if (ciudadConMayorSuperavit == -1 || comparadorSuperavit.compare(ciudadesInfo[ciudad], ciudadesInfo[ciudadConMayorSuperavit]) > 0) {
            ciudadConMayorSuperavit = ciudad;
        }
    }

    public int ciudadConMayorSuperavit(){ // O(1) devuelve un atributo de la clase BestEffort.
        return ciudadConMayorSuperavit;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){ // O(1) devuelve un atributo de la clase BestEffort.
        return ciudadesConMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){ // O(1) devuelve un atributo de la clase BestEffort.
        return ciudadesConMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){ // O(1) devuelve un atributo de la clase BestEffort.
        return gananciaTotal / cantTraslados;
    }
}
