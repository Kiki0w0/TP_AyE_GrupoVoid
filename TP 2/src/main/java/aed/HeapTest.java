package aed;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HeapTest {
    private Comparator<Traslado> comparadorGanancia;// maxHeap
    private Comparator<Traslado> comparadorAntiguedad;//minHeap


    @Test 
    public void estaVacioGanancia(){
        Traslado[] traslados = new Traslado[]{};
        Heap heap = new Heap(traslados, comparadorGanancia, true);
        assertEquals(0,heap.tamaño());
        assertTrue(heap.estaVacio());
    }

    /* 
    @Test
    public void estaVacioAntiguedad(){
        Traslado[] traslados = new Traslado[]{};
        Heap heap = new Heap(traslados, comparadorAntiguedad, false);
        assertEquals(0,heap.tamaño());
        assertTrue(heap.estaVacio());
    }
    */

    @Test 
    public void insertarMasTraslados(){
        int cantCiudades = 7;
        Heap heap = new Heap(new Traslado[]{}, comparadorAntiguedad, true);
        Traslado[] listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 1, 100, 10),
                                            new Traslado(2, 0, 1, 400, 20),
                                            new Traslado(3, 3, 4, 500, 50),
                                            new Traslado(4, 4, 3, 500, 11),
                                            new Traslado(5, 1, 0, 1000, 40),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 2000, 42)
                                        };
        
        //Heap nuevo_heap= new Heap(listaTraslados, comparadorGanancia, true);                               
        //assertEquals(8,nuevo_heap.tamaño());
        for(int i=0; i<cantCiudades;i++){
            heap.insertar(listaTraslados[i]);
        };
        assertEquals(cantCiudades, heap.tamaño());
        //assertEquals(nuevo_heap.tamaño(), 10);
    }

}