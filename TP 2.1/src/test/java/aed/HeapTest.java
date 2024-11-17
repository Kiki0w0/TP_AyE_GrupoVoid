package aed;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.jupiter.api.Test;

import aed.Traslado.ComparadorAntiguedadInvertido;

import org.junit.jupiter.api.BeforeEach;

public class HeapTest {
    Comparator<Traslado> comparadorGananciaNeta;
    Comparator<Traslado> comparadorAntiguedad;
    ArrayList<Traslado> datos;    

    @BeforeEach
    public void setUp() {
        comparadorGananciaNeta = new Traslado.ComparadorGananciaNeta();
        comparadorAntiguedad = new Traslado.ComparadorAntiguedad();
    }


    @Test 
    public void estaVacioGanancia(){
        //Traslado[] traslados = new Traslado[]{};
        //Heap<Traslado> heap = new Heap<>(traslados, comparadorGananciaNeta, true);
        ArrayList<HeapElement<Traslado>> lista = new ArrayList<>();
        Heap<Traslado> heap = new Heap<>(lista, comparadorGananciaNeta);
        
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
        ArrayList<HeapElement<Traslado>> vacio = new ArrayList<>();
        Heap<Traslado> heap = new Heap<>(vacio, comparadorAntiguedad);
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
        for(Traslado traslado : listaTraslados){
            Handle handle = new Handle(-1);
            heap.insertar(new HeapElement<>(handle, traslado));
        };
        assertEquals(cantCiudades, heap.tamaño());
        assertEquals(listaTraslados[0],heap.extraerMax().getValor());
        assertEquals(cantCiudades-1,heap.tamaño());
        //assertEquals(nuevo_heap.tamaño(), 10);
    }

    @Test
    // despacha el el indice mas antiguo en el heap de ganancias
    
    // !!!!!!Esto podría solucionarse creando un nuevo comparador,
    // llamado "comparador de Antiguedad invertido"
    
    public void despacharIndiceAntiguedad(){
        int cantCiudades= 8;
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

        Heap<Traslado> heap = new Heap<>(new ArrayList<>(), new ComparadorAntiguedadInvertido());
        
        for (Traslado traslado : traslados) {
            Handle handle = new Handle(-1);
            heap.insertar(new HeapElement<>(handle,traslado));
        }
        assertEquals(8, heap.tamaño());

        Traslado primerDespachado = heap.extraerMax().getValor();
        assertEquals(17, primerDespachado.getId());

        assertEquals(cantCiudades - 1, heap.tamaño());
        //assertEquals(sistema.ciudadesConMayorGanancia(),[2])
        //sistema.despacharMasRedituables(1);
        //assertEquals(traslados[1],)
    }
    // despacha el el indice con mayor ganancia en el heap de antiguedad
    //public void despacharIndiceGanancia(){

    //}
}
