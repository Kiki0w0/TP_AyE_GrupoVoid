package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<HeapElement<T>> datos;
    private Comparator<T> comparador;

    // Sea N = cantidad de elementos en el heap

    public Heap(ArrayList<HeapElement<T>> datos, Comparator<T> comparador){ // O(N) por algoritmo de Floyd
        this.datos = datos;
        this.comparador = comparador;
        heapify();
    }

    public void heapify(){ // O(N)
        int n = datos.size();
        for(int i = (n - 1) / 2; i >= 0; i--){
            heapifyDown(i);
        }
    }

    public int tamaño(){ // O(1)
        return datos.size();
    }

    public HeapElement<T> extraerMax() { // O(log(N))
        if (datos.size() == 0) {
            return null;
        }
        if (datos.size() == 1) {
            return datos.remove(0);
        } else {
            HeapElement<T> max = datos.get(0);
            HeapElement<T> ultimo = datos.get(datos.size() - 1);
            ultimo.setHandle(0);
            datos.set(0, ultimo);
            datos.remove(datos.size() - 1);
            heapifyDown(0);
            return max;
        }
    }

    public HeapElement<T> consultarMax() { // O(1)
        return datos.get(0);
    }
    
    public void eliminar(int i) { // O(log(N)) Despacha en el handle correspondiente y reacomoda el heap
        HeapElement<T> ultimo = datos.get(datos.size() - 1);
        ultimo.setHandle(i);
        datos.set(i, ultimo);
        datos.remove(datos.size() - 1);
    
        actualizarEnIndice(i);
    }
    
    public void actualizarEnIndice(int i) { // O(log(N)) Reacomoda el heap cuando alguno de sus valores Cambia
        if (i < datos.size()) {
            int padre = (i - 1) / 2;
            if (comparador.compare(datos.get(i).getValor(), datos.get(padre).getValor()) > 0) {
                heapifyUp(i);
            } else {
                heapifyDown(i);
            }
        }
    }

    public void insertar(HeapElement<T> objeto){ // O(log(N)) Inserta un elemento en el heap, lo acomoda y actualiza su handle correspondiente
        objeto.setHandle(datos.size() - 1);
        datos.add(objeto);
        heapifyUp(datos.size() - 1);
    }

    public void heapifyUp(int i){ // O(log(N))
        int padre = padre(i);
         if (i > 0 && comparador.compare(datos.get(i).getValor(), datos.get(padre).getValor()) > 0){
            swap(i, padre);
            heapifyUp(padre);
         }
    }

    public void heapifyDown(int i){ // O(log(N))
        int hijoIzq = hijoIzq(i);
        int hijoDer = hijoDer(i);
        int mayor = i;

        if (hijoIzq < datos.size() && comparador.compare(datos.get(hijoIzq).getValor(), datos.get(mayor).getValor()) > 0){
            mayor = hijoIzq;
        }

        if(hijoDer < datos.size() && comparador.compare(datos.get(hijoDer).getValor(), datos.get(mayor).getValor()) > 0){
            mayor = hijoDer;
        }

        if (mayor != i){
            swap(mayor, i);
            heapifyDown(mayor);
        }

    }

    public void swap(int i, int j){ // O(1) Intercambia la posicion de dos elementos y actualiza sus handles correspondientes
        HeapElement<T> tempj = datos.get(j);
        HeapElement<T> tempi = datos.get(i);
        tempj.setHandle(i);
        tempi.setHandle(j);
        datos.set(i, tempj);
        datos.set(j, tempi);
    }

    public int padre(int indice) { // O(1)
        return (indice - 1) / 2;
    }

    public int hijoIzq(int indice) { // O(1)
        return 2 * indice + 1;
    }

    public int hijoDer(int indice) { // O(1)
        return 2 * indice + 2;
    }

}
