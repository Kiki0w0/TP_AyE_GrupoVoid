package aed;

import java.util.Comparator;

public class Traslado { 
    private int id;
    private int idGanancia; // referencia de indice en el heap de ganancia
    private int idAntiguedad; // referencia de indice en el heap de antiguedad
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;


    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.idGanancia = -1;
        this.idAntiguedad = -1;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public void setIdGanancia(int i){
        idGanancia = i;
    }

    public void setIdAntiguedad(int i){
        idAntiguedad = i;
    } 

    public int getId(){
        return id;
    }
    public int getIdGanancia(){
        return idGanancia;
    }

    public int getIdAntiguedad(){
        return idAntiguedad;
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
            return Integer.compare(tras2.gananciaNeta, tras1.gananciaNeta);  
        }
    }
    
    public static class ComparadorAntiguedad implements Comparator<Traslado> {
        @Override
        public int compare(Traslado tras1, Traslado tras2) {
            return Integer.compare(tras1.timestamp, tras2.timestamp); 
        }
    }
}


