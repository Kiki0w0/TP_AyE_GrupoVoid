package aed;

public class Tupla<T> { 
    private Handle handle;
    private T objeto;

    public Tupla(Handle handle, T objeto){
        this.handle = handle;
        this.objeto = objeto;
    }

    public Handle getHandle(){
        return handle;
    }

    public T getObjeto(){
        return objeto;
    }

    public void setHandle(int indice){
        this.handle.setIndice(indice);;
    }
    
    public void setObjeto(T objeto){
        this.objeto = objeto;
    }
}