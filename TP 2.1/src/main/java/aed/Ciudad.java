package aed;

import java.util.Comparator;

public class Ciudad {
    private int id;
    private Handle idSuperavit;
    private int ganancia;
    private int perdida;

    public Ciudad(int id){
        this.id = id;
        this.idSuperavit = new Handle(-1);
        this.ganancia = 0;
        this.perdida = 0;
    }

    public int getId(){
        return id;
    }

    public int getGanancia(){
        return ganancia;
    }

    public int getPerdida(){
        return perdida;
    }

    public int getIdSuperavit(){
        return idSuperavit.getIndice();
    }

    public Handle getHandleSuperavit(){
        return idSuperavit;
    }

    public void setGanancia(int ganancia){
        this.ganancia += ganancia;
    }

    public void setPerdida(int perdida){
        this.perdida += perdida;
    }

    public void setIdSuperavit(int indice){
        this.idSuperavit.setIndice(indice);
    }

    // COMPARADORES DE CIUDAD
    public static class ComparadorSuperavit implements Comparator<Ciudad> {
        @Override 
        public int compare(Ciudad ciud1, Ciudad ciud2){
            int superavit1 = ciud1.getGanancia() - ciud1.getPerdida();
            int superavit2 = ciud2.getGanancia() - ciud2.getPerdida();
            if (superavit1 != superavit2){
                return Integer.compare(superavit1,superavit2);
            } else {
                return Integer.compare(ciud2.id, ciud1.id);
            }
        }
    }

}
