package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap trasladosGanancias; // heap ordenado por ganancia
    private Heap trasladosAntiguedad; // heap ordenado por antiguedad
    private Ciudad[] ciudadesInfo; // guarda la informacion de las ciudades 
    private int ciudadConMayorSuperavit; // almacena temporalmente y se actualiza tras cada despacho *
    private ArrayList<Integer> ciudadesConMayorGanancia; // *
    private ArrayList<Integer> ciudadesConMayorPerdida; // *
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
        actualizarEstadisticasCiudad(origen);
        actualizarEstadisticasCiudad(destino);
    }

    // DUDOSA
    private void actualizarEstadisticasCiudad(int ciudad){ // actualiza mayor superavit, mayor ganancia y mayor perdida
        Ciudad.ComparadorSuperavit ComparadorSuperavit = new Ciudad.ComparadorSuperavit();
        if (ciudadConMayorSuperavit == -1 || ComparadorSuperavit.compare(ciudadesInfo[ciudad], ciudadesInfo[ciudadConMayorSuperavit]) > 0) {
            ciudadConMayorSuperavit = ciudad;
        }
        if (ciudadesConMayorGanancia.size() > 0){
            int maxGanancia = ciudadesInfo[ciudadesConMayorGanancia.get(0)].getGanancia();
            if (ciudadesInfo[ciudad].getGanancia() == maxGanancia){ // *
                ciudadesConMayorGanancia.add(ciudad);
            } else if (ciudadesInfo[ciudad].getGanancia() > maxGanancia){
                ciudadesConMayorGanancia.clear();
                ciudadesConMayorGanancia.add(ciudad);
            }
        } else {
            ciudadesConMayorGanancia.add(ciudad);
        }
        if(ciudadesConMayorPerdida.size() > 0){
            int maxPerdida = ciudadesInfo[ciudadesConMayorPerdida.get(0)].getPerdida();
            if(ciudadesInfo[ciudad].getPerdida() == maxPerdida){ 
                ciudadesConMayorPerdida.add(ciudad);
            } else if (ciudadesInfo[ciudad].getPerdida() > maxPerdida){
                ciudadesConMayorPerdida.clear();
                ciudadesConMayorPerdida.add(ciudad);
            }
        } else {
            ciudadesConMayorPerdida.add(ciudad);
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
