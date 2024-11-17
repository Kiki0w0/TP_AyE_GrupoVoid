package aed;

// Utilizaremos esta clase para referenciar los indices de elementos que pertenecen a mas de un heap
public class Handle { 
    private int indice;

    public Handle(int indice) {
        this.indice = indice;
    }

    public int getIndice() {
        return indice; 
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
