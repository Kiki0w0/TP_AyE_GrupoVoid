package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<Tupla<T>> datos;
    private Comparator<T> comparador;

    public Heap(ArrayList<Tupla<T>> datos, Comparator<T> comparador){
        this.datos = datos;
        this.comparador = comparador;
        construirHeap();
    }

    public void construirHeap(){
        int n = datos.size();
        for(int i = (n - 1) / 2; i >= 0; i--){
            heapifyDown(i);
        }
    }

    public int tamaño(){
        return datos.size();
    }


    public boolean estaVacio(){
        return datos.size() == 0;
    }


    public Tupla<T> extraerMax() { // saca la tupla con el objeto de mayor prioridad y reacomoda el heap 
        if (datos.size() == 0) {
            return null;
        }
        if (datos.size() == 1) {
            return datos.remove(0);
        } else {
            Tupla<T> max = datos.get(0);
            Tupla<T> ultimo = datos.get(datos.size() - 1);
            ultimo.setHandle(0);
            datos.set(0, ultimo);
            datos.remove(datos.size() - 1);
            heapifyDown(0);
            return max;
        }
    }

    public Tupla<T> devolverMax() {
        return datos.get(0);
    }
    
    public void despacharEnIndice(int i) { // despacha en el indice que indiquemos y reacomoda el heap
        Tupla<T> ultimo = datos.get(datos.size() - 1);
        ultimo.setHandle(i);
        datos.set(i, ultimo);
        datos.remove(datos.size() - 1);
    
        actualizarEnIndice(i);
    }
    
    public void actualizarEnIndice(int i) { // reacomoda el heap cuando uno de sus valores cambia
        if (i < datos.size()) {
            int padre = (i - 1) / 2;
            if (comparador.compare(datos.get(i).getObjeto(), datos.get(padre).getObjeto()) > 0) {
                heapifyUp(i);
            } else {
                heapifyDown(i);
            }
        }
    }

    public void insertar(Tupla<T> objeto){ // inserta el traslado al final del heap, actualiza id y luego acomoda
        objeto.setHandle(datos.size() - 1);
        datos.add(objeto);
        heapifyUp(datos.size() - 1);
    }

    public void heapifyUp(int i){
        int padre = (i - 1) / 2;
         if (i > 0 && comparador.compare(datos.get(i).getObjeto(), datos.get(padre).getObjeto()) > 0){
            swap(i, padre);
            heapifyUp(padre);
         }
    }

    public void heapifyDown(int i){
        int hijoIzq = 2 * i + 1;
        int hijoDer = 2 * i + 2;
        int mayor = i;

        if (hijoIzq < datos.size() && comparador.compare(datos.get(hijoIzq).getObjeto(), datos.get(mayor).getObjeto()) > 0){
            mayor = hijoIzq;
        }

        if(hijoDer < datos.size() && comparador.compare(datos.get(hijoDer).getObjeto(), datos.get(mayor).getObjeto()) > 0){
            mayor = hijoDer;
        }

        if (mayor != i){
            swap(mayor, i);
            heapifyDown(mayor);
        }

    }

    public void swap(int i, int j){ // intercambia la posicion de traslados y actualiza los id de referencia
        Tupla<T> tempj = datos.get(j);
        Tupla<T> tempi = datos.get(i);
        tempj.setHandle(i);
        tempi.setHandle(j);
        datos.set(i, tempj);
        datos.set(j, tempi);
    }

}
