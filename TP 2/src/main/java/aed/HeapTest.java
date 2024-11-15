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
    Comparator<Traslado> ComparadorGananciaNeta;
    Comparator<Traslado> comparadorAntiguedad;
    ArrayList<Traslado> datos;    


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
    public void insertarSoloTraslados(){
        int cantCiudades = 7;
        ArrayList<Traslado> vacio={};
        Heap heap = new Heap(vacio, comparadorAntiguedad, false);
        ArrayList[] listaTraslados = new Traslado[] {
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
        assertEquals(heap.despachar(),listaTraslados[0]);
        assertEquals(cantCiudades-1,heap.tamaño());
        //assertEquals(nuevo_heap.tamaño(), 10);
    }

    @Test
    // despacha el el indice mas antiguo en el heap de ganancias
    public void despacharIndiceAntiguedad(){
        int cantCiudades= 22;
        Traslado[] traslados = new Traslado[]{
                new Traslado(8, 2, 5, 1500, 30),
                new Traslado(9, 0, 2, 600, 25),
                new Traslado(17, 2, 6, 2200, 55),
                new Traslado(18, 4, 1, 800, 22),
                new Traslado(19, 5, 0, 1500, 40),
                new Traslado(20, 6, 2, 1800, 48),
                new Traslado(10, 3, 1, 800, 35),
                new Traslado(11, 4, 2, 1200, 50)
        };

        Heap heap = new Heap(traslados, comparadorGananciaNeta, true);

        //assertEquals(sistema.ciudadesConMayorGanancia(),[2])
        //sistema.despacharMasRedituables(1);
        //assertEquals(traslados[1],)
    }
    // despacha el el indice con mayor ganancia en el heap de antiguedad
    //public void despacharIndiceGanancia(){

    //}
}
