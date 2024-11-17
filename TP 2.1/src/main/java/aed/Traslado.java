package aed;

import java.util.Comparator;

public class Traslado { 
    private int id;
    private Handle idGanancia; // Referencia de indice en heapGanancia
    private Handle idAntiguedad; // Referencia de indice en heapAntiguedad
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;


    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.idGanancia = new Handle(-1);
        this.idAntiguedad = new Handle(-1);
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public void setIdGanancia(int i){
        idGanancia.setIndice(i);
    }

    public void setIdAntiguedad(int i){
        idAntiguedad.setIndice(i);
    }

    public Handle getHandleGanancia() {
        return idGanancia;
    }

    public Handle getHandleAntiguedad() {
        return idAntiguedad;
    }

    public int getId(){
        return id;
    }
    public int getIdGanancia(){
        return idGanancia.getIndice();
    }

    public int getIdAntiguedad(){
        return idAntiguedad.getIndice();
    }

    public int getGananciaNeta(){
        return gananciaNeta;
    }

    public int getOrigen(){
        return origen;
    }

    public int getDestino(){
        return destino;
    }

    // COMPARADORES DE TRASLADO
    public static class ComparadorGananciaNeta implements Comparator<Traslado> {
        @Override
        public int compare(Traslado tras1, Traslado tras2) {
            return Integer.compare(tras1.gananciaNeta, tras2.gananciaNeta);  
        }
    }
    
    public static class ComparadorAntiguedad implements Comparator<Traslado> {
        @Override
        public int compare(Traslado tras1, Traslado tras2) {
            return Integer.compare(tras2.timestamp, tras1.timestamp); 
        }
    }

// Comparador para usar en el test case despacharIndiceAntiguedad del archivo HeapTest
    public static class ComparadorAntiguedadInvertido implements Comparator<Traslado> {
        @Override
        public int compare(Traslado tras1, Traslado tras2) {
            return Integer.compare(tras1.timestamp, tras2.timestamp);
        }
    }
}


