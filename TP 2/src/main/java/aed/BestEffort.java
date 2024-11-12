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
        // Implementar
        return null;
    }

    private void actualizarEstadisticas(Traslado traslado){ // actualiza contadores, info de ciudad y estadisticas de cada ciudad
        gananciaTotal += traslado.getGananciaNeta();
        cantTraslados += 1;
        int origen = traslado.getOrigen();
        int destino = traslado.getDestino();
        int gananciaPorTraslado = traslado.getGananciaNeta();
        ciudadesInfo[origen].setGanancia(gananciaPorTraslado); 
        ciudadesInfo[destino].setPerdida(gananciaPorTraslado);
        actualizarEstadisticasCiudad(origen, destino);
    }

    // DUDOSA
    private void actualizarEstadisticasCiudad(int origen, int destino){ // actualiza mayor superavit, mayor ganancia y mayor perdida
        Ciudad.ComparadorSuperavit ComparadorSuperavit = new Ciudad.ComparadorSuperavit();
        if (ciudadConMayorSuperavit == -1 || ComparadorSuperavit.compare(ciudadesInfo[origen], ciudadesInfo[ciudadConMayorSuperavit]) > 0) {
            ciudadConMayorSuperavit = origen; // me parece que falta hacer lo mismo con destino *
        }
        if (ciudadesConMayorGanancia.size() > 0){
            int maxGanancia = ciudadesConMayorGanancia.get(0);
            if (ciudadesInfo[origen].getGanancia() == ciudadesInfo[maxGanancia].getGanancia()){ // *
                ciudadesConMayorGanancia.add(origen);
            } else if (ciudadesInfo[origen].getGanancia() > ciudadesInfo[maxGanancia].getGanancia()){
                ciudadesConMayorGanancia.clear();
                ciudadesConMayorGanancia.add(origen);
            }
        } else {
            ciudadesConMayorGanancia.add(origen);
        }
        if(ciudadesConMayorPerdida.size() > 0){
            int maxPerdida = ciudadesConMayorPerdida.get(0);
            if(ciudadesInfo[destino].getPerdida() == ciudadesInfo[maxPerdida].getPerdida()){ // falta lo mismo pero con origen
                ciudadesConMayorPerdida.add(destino);
            } else if (ciudadesInfo[destino].getPerdida() > ciudadesInfo[maxPerdida].getPerdida()){
                ciudadesConMayorPerdida.clear();
                ciudadesConMayorPerdida.add(destino);
            }
        } else {
            ciudadesConMayorPerdida.add(destino);
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
        // Implementar
        return 0;
    }
    
}
