package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap {
    private ArrayList<Traslado> datos;
    private Comparator<Traslado> comparador;
    private boolean esHeapGanancia; // indica si es heap ganancia o no para actualizar el id de referencia dependiendo de cual sea

    public Heap(Traslado[] datos, Comparator<Traslado> comparador, boolean esHeapGanancia){
        this.datos = new ArrayList<>();
        for(int i = 0; i < datos.length; i++){
            this.datos.add(datos[i]);
        }
        this.comparador = comparador;
        this.esHeapGanancia = esHeapGanancia;
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

    public Traslado despachar() { // saca el traslado con mayor prioridad y reacomoda el heap 
        if (datos.size() == 0) {
            return null;
        }
        if (datos.size() == 1) {
            return datos.remove(0);
        } else {
            Traslado max = datos.get(0);
            datos.set(0, datos.get(datos.size() - 1));
            datos.remove(datos.size() - 1);
            heapifyDown(0);
            return max;
        }
    }
    
    public void despacharEnIndice(int i) { // despacha en el indice que indiquemos y reacomoda el heap
        datos.set(i, datos.get(datos.size() - 1));
        datos.remove(datos.size() - 1);
    
        if (i < datos.size()) {
            int padre = (i - 1) / 2;
            if (comparador.compare(datos.get(i), datos.get(padre)) > 0) {
                heapifyUp(i);
            } else {
                heapifyDown(i);
            }
        }
    }
    

    public void insertar(Traslado traslado){ // inserta el traslado al final del heap, actualiza id y luego acomoda
        if(esHeapGanancia){
            traslado.setIdGanancia(datos.size());
        } else {
            traslado.setIdAntiguedad(datos.size());
        }
        datos.add(traslado);
        heapifyUp(datos.size() - 1);
    }

    public void heapifyUp(int i){
        int padre = (i - 1) / 2;
         if (i > 0 && comparador.compare(datos.get(i), datos.get(padre)) > 0){
            swap(datos, i, padre);
            heapifyUp(padre);
         }
    }

    public void heapifyDown(int i){
        int hijoIzq = 2 * i + 1;
        int hijoDer = 2 * i + 2;
        int mayor = i;

        if (hijoIzq < datos.size() && comparador.compare(datos.get(hijoIzq), datos.get(mayor)) > 0){
            mayor = hijoIzq;
        }

        if(hijoDer < datos.size() && comparador.compare(datos.get(hijoDer), datos.get(mayor)) > 0){
            mayor = hijoDer;
        }

        if (mayor != i){
            swap(datos, mayor, i);
            heapifyDown(mayor);
        }

    }

    public void swap(ArrayList<Traslado> datos, int i, int j){ // intercambia la posicion de traslados y actualiza los id de referencia
        Traslado temp = datos.get(j);
        datos.set(j, datos.get(i));
        datos.set(i, temp);

        if (esHeapGanancia){
            datos.get(i).setIdGanancia(i);
            datos.get(j).setIdGanancia(j);
        } else {
            datos.get(i).setIdAntiguedad(i);
            datos.get(j).setIdAntiguedad(j);
        }
    }

}
