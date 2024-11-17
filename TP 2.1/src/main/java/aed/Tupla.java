package aed;

public class HeapElement<T> {  // Define un elemento que puede pertenecer a mas de un heap 
    private Handle handle; // Referencia su ubicacion en cada heap
    private T valor; // Guarda el valor del elemento

    public HeapElement(Handle handle, T valor){
        this.handle = handle;
        this.valor = valor;
    }

    public Handle getHandle(){
        return handle;
    }

    public T getValor(){
        return valor;
    }

    public void setHandle(int indice){
        this.handle.setIndice(indice);;
    }
}
