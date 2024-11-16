package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap<Traslado> trasladosGanancias; // heap de traslados ordenados por ganancia
    private Heap<Traslado> trasladosAntiguedad; // heap de traslados ordenados por antiguedad
    private Ciudad[] ciudadesInfo; // guarda la informacion de las ciudades 
    private Heap<Ciudad> ciudadesSuperavit; // heap de ciudades ordenados por superavit
    private ArrayList<Integer> ciudadesConMayorGanancia; // *
    private ArrayList<Integer> ciudadesConMayorPerdida; // *
    private int cantMayorGanancia; // guarda la cantidad de la ciudad con mayor ganancia para ser comparada con la ciudad ingresada
    private int cantMayorPerdida; // guarda la cantidad de la ciudad con mayor perdida para ser comparada con la ciudad ingresada
    private int gananciaTotal; // contador ganancia
    private int cantTraslados; // contador traslados


    public BestEffort(int cantCiudades, Traslado[] traslados){
        ArrayList<Tupla<Traslado>> listaTrasladosGanancia = new ArrayList<>();
        ArrayList<Tupla<Traslado>> listaTrasladosAntiguedad = new ArrayList<>();
        for (int i = 0; i < traslados.length; i++) {
            Traslado traslado = traslados[i];
            traslado.setIdGanancia(i);
            traslado.setIdAntiguedad(i);
            Tupla<Traslado> tuplaTrasladoGanancia = new Tupla<Traslado>(traslado.getHandleGanancia(), traslado);
            Tupla<Traslado> tuplaTrasladoAntiguedad = new Tupla<Traslado>(traslado.getHandleAntiguedad(), traslado);
            listaTrasladosGanancia.add(tuplaTrasladoGanancia);
            listaTrasladosAntiguedad.add(tuplaTrasladoAntiguedad);
        }
        this.trasladosGanancias = new Heap<Traslado>(listaTrasladosGanancia, new Traslado.ComparadorGananciaNeta());
        this.trasladosAntiguedad = new Heap<Traslado>(listaTrasladosAntiguedad, new Traslado.ComparadorAntiguedad());
        this.ciudadesInfo = new Ciudad[cantCiudades];
        ArrayList<Tupla<Ciudad>> listaCiudadesSuperavit = new ArrayList<>();
        for(int i = 0; i < cantCiudades; i++){
            Ciudad ciudad = new Ciudad(i);
            ciudad.setIdSuperavit(i);
            ciudadesInfo[i] = ciudad;
            Tupla<Ciudad> tuplaCiudad = new Tupla<Ciudad>(ciudad.getHandleSuperavit(), ciudad);
            listaCiudadesSuperavit.add(tuplaCiudad);
        }
        this.ciudadesSuperavit = new Heap<Ciudad>(listaCiudadesSuperavit, new Ciudad.ComparadorSuperavit());
        this.ciudadesConMayorGanancia = new ArrayList<Integer>();
        this.ciudadesConMayorPerdida = new ArrayList<Integer>();
        this.cantMayorGanancia = 0;
        this.cantMayorPerdida = 0;
        this.gananciaTotal = 0;
        this.cantTraslados = 0;
    }

    public void registrarTraslados(Traslado[] traslados){
        for(int i = 0; i < traslados.length; i++){
            Traslado traslado = traslados[i];
            trasladosGanancias.insertar(new Tupla<Traslado>(traslado.getHandleGanancia(), traslado));
            trasladosAntiguedad.insertar(new Tupla<Traslado>(traslado.getHandleAntiguedad(), traslado));
        }
    }

    public int[] despacharMasRedituables(int n){
        int m = trasladosGanancias.tamaño();
        if (n > m){
            n = m;
        }
        int[] despachos = new int[n];
        for(int i = 0; i < n; i++){
            Tupla<Traslado> tuplaTraslado = trasladosGanancias.extraerMax(); // despacha del heap y devuelve un traslado
            trasladosAntiguedad.despacharEnIndice(tuplaTraslado.getObjeto().getIdAntiguedad()); // despacha en trasladosAntiguedad con la referencia correspondiente
            despachos[i] = tuplaTraslado.getObjeto().getId();
            actualizarEstadisticas(tuplaTraslado);
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
            Tupla<Traslado> tuplaTraslado = trasladosAntiguedad.extraerMax(); // despacha del heap y devuelve un traslado
            trasladosGanancias.despacharEnIndice(tuplaTraslado.getObjeto().getIdGanancia()); // despacha en trasladosAntiguedad con la referencia correspondiente
            despachos[i] = tuplaTraslado.getObjeto().getId();
            actualizarEstadisticas(tuplaTraslado);
        }
        return despachos;
    }

    private void actualizarEstadisticas(Tupla<Traslado> tuplaTraslado){ // actualiza contadores, info de ciudad y estadisticas de cada ciudad
        Ciudad origen = ciudadesInfo[tuplaTraslado.getObjeto().getOrigen()];
        Ciudad destino = ciudadesInfo[tuplaTraslado.getObjeto().getDestino()];
        int gananciaPorTraslado = tuplaTraslado.getObjeto().getGananciaNeta();
        gananciaTotal += gananciaPorTraslado;
        cantTraslados += 1;
        origen.setGanancia(gananciaPorTraslado); 
        destino.setPerdida(gananciaPorTraslado);
        actualizarCiudadConMayorGanancia(origen.getId());
        actualizarCiudadConMayorPerdida(destino.getId());
        ciudadesSuperavit.actualizarEnIndice(origen.getIdSuperavit());
        ciudadesSuperavit.actualizarEnIndice(destino.getIdSuperavit());
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

    public int ciudadConMayorSuperavit(){
        return ciudadesSuperavit.devolverMax().getObjeto().getId();
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
