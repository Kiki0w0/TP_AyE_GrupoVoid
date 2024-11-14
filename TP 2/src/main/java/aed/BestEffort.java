package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap trasladosGanancias; // heap ordenado por ganancia
    private Heap trasladosAntiguedad; // heap ordenado por antiguedad
    private Ciudad[] ciudadesInfo; // guarda la informacion de las ciudades 
    private int ciudadConMayorSuperavit; // almacena temporalmente y se actualiza tras cada despacho *
    private ArrayList<Integer> ciudadesConMayorGanancia; // *
    private ArrayList<Integer> ciudadesConMayorPerdida; // *
    private int cantMayorGanancia; // guarda la cantidad de la ciudad con mayor ganancia
    private int cantMayorPerdida; // guarda la cantidad de la ciudad con mayor perdida
    private int gananciaTotal; // contador ganancia
    private int cantTraslados; // contador traslados


    public BestEffort(int cantCiudades, Traslado[] traslados){
        for (int i = 0; i < traslados.length; i++) {
            traslados[i].setIdAntiguedad(i);
            traslados[i].setIdGanancia(i);
        }
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

    public void registrarTraslados(Traslado[] traslados){
        for(int i = 0; i < traslados.length; i++){
            trasladosGanancias.insertar(traslados[i]);
            trasladosAntiguedad.insertar(traslados[i]);
        }
    }

    public int[] despacharMasRedituables(int n){
        int m = trasladosGanancias.tamaño();
        if (n > m){
            n = m;
        }
        int[] despachos = new int[n];
        for(int i = 0; i < n; i++){
            Traslado traslado = trasladosGanancias.despachar(); // despacha del heap y devuelve un traslado
            trasladosAntiguedad.despacharEnIndice(traslado.getIdAntiguedad()); // despacha en trasladosAntiguedad con la referencia correspondiente
            despachos[i] = traslado.getId();
            actualizarEstadisticas(traslado);
        }
        return despachos;
    }

    public int[] despacharMasAntiguos(int n){
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
    private void actualizarEstadisticas(Traslado traslado){ // actualiza contadores, info de ciudad y estadisticas de cada ciudad
        gananciaTotal += traslado.getGananciaNeta();
        cantTraslados += 1;
        int origen = traslado.getOrigen();
        int destino = traslado.getDestino();
        int gananciaPorTraslado = traslado.getGananciaNeta();
        ciudadesInfo[origen].setGanancia(gananciaPorTraslado); 
        ciudadesInfo[destino].setPerdida(gananciaPorTraslado);
        actualizarCiudadConMayorGanancia(origen);
        actualizarCiudadConMayorPerdida(destino);
        actualizarCiudadConMayorSuperavit(origen);
        actualizarCiudadConMayorSuperavit(destino);
        cantMayorGanancia = ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia();
        cantMayorPerdida = ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida();
    }


    private void actualizarCiudadConMayorGanancia(int ciudad) {
        int gananciaCiudad = ciudadesInfo[ciudad].getGanancia();
        if (ciudadesConMayorGanancia.size() == 0 || (gananciaCiudad == ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia() && gananciaCiudad == cantMayorGanancia)) {
            ciudadesConMayorGanancia.add(ciudad);
        } else if (gananciaCiudad >= ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia() && gananciaCiudad != cantMayorGanancia) {
            ciudadesConMayorGanancia.clear();
            ciudadesConMayorGanancia.add(ciudad);
        }
    }

    private void actualizarCiudadConMayorPerdida(int ciudad) {
        int perdidaCiudad = ciudadesInfo[ciudad].getPerdida();
        if (ciudadesConMayorPerdida.size() == 0 || (perdidaCiudad == ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida() && perdidaCiudad == cantMayorPerdida)) {
            ciudadesConMayorPerdida.add(ciudad);
        } else if (perdidaCiudad >= ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida() && perdidaCiudad != cantMayorPerdida) {
            ciudadesConMayorPerdida.clear();
            ciudadesConMayorPerdida.add(ciudad);
        }
    }

    private void actualizarCiudadConMayorSuperavit(int ciudad) {
        Ciudad.ComparadorSuperavit comparadorSuperavit = new Ciudad.ComparadorSuperavit();
        if (ciudadConMayorSuperavit == -1 || comparadorSuperavit.compare(ciudadesInfo[ciudad], ciudadesInfo[ciudadConMayorSuperavit]) > 0) {
            ciudadConMayorSuperavit = ciudad;
        }
    }

    public int ciudadConMayorSuperavit(){
        return ciudadConMayorSuperavit;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudadesConMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudadesConMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        return gananciaTotal / cantTraslados;
    }
}
