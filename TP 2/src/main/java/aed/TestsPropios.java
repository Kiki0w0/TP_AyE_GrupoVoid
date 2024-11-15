package aed;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestsPropios {
    ///PRUEBA
    @Test
    public void testDespacharMasRedituables() {
        int cantCiudades = 4;
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 100, 5),
            new Traslado(2, 1, 2, 200, 10),
            new Traslado(3, 2, 3, 300, 15)
        };
        
        BestEffort sistema = new BestEffort(cantCiudades, traslados);
        int[] despachados = sistema.despacharMasRedituables(2);
        assertEquals(2, despachados.length);
        assertEquals(3, despachados[0]); // Despacho más rentable primero
        assertEquals(2, despachados[1]); // Segundo despacho más rentable
    }
    @Test
    public void despachosMasRedituablesIguales(){
        int cantCiudades= 4;
        Traslado[] traslados={
            new Traslado(1, 0, 1, 100, 5),
            new Traslado(2, 1, 2, 200, 10),
            new Traslado(3, 2, 3, 300, 15),
            new Traslado(4, 2, 1, 300, 15)
        };
        BestEffort sistema=new BestEffort(cantCiudades, traslados);
        int[] despachos=sistema.despacharMasRedituables(2);
        assertEquals(traslados[3].getId(),despachos[0]);
        assertEquals(traslados[2].getId(), despachos[1]);
    }

    @Test 
    public void despachoMixtoConMismaLong(){
        // mayor ganancia y mayor perdida con el mixto con n igual al arreglo
        int cantCiudades=10;
        Traslado[] traslados= {
            new Traslado(1, 0, 1, 300, 15),
            new Traslado(2, 1, 2, 300, 15),
            new Traslado(3, 2, 3, 300, 15),
            new Traslado(4, 3, 1, 300, 15),
            new Traslado(5, 4, 1, 300, 15),
            new Traslado(6, 7, 1, 300, 15),
            new Traslado(7, 6, 1, 300, 15),
            new Traslado(8, 5, 1, 300, 15)
        };
        BestEffort sistema= new BestEffort(cantCiudades,traslados);
        sistema.registrarTraslados(traslados);

        int[] despachos_red = sistema.despacharMasRedituables(cantCiudades);
        int[] despachos_ant =sistema.despacharMasAntiguos(cantCiudades);
        assertEquals(8, despachos_ant.length);
        assertEquals(8, despachos_red.length);
    }

// caso despachar hasta que este todo vacio;
    @Test 
    public void despacharTodos(){
        int cantCiudades=7;
        Traslado[] traslados= {
            new Traslado(1, 0, 1, 300, 15),
            new Traslado(2, 1, 2, 200, 15),
            new Traslado(3, 2, 3, 100, 15),
            new Traslado(4, 3, 1, 500, 15),
            new Traslado(5, 4, 1, 300, 15),
            new Traslado(6, 7, 1, 200, 15)

        };
        BestEffort sistema= new BestEffort(cantCiudades,traslados);
        sistema.registrarTraslados(traslados);
        for(int i=0;i<traslados.length;i++){
            sistema.ciudadesConMayorGanancia();
            sistema.ciudadesConMayorPerdida();
        }
        assertTrue(sistema.tamaño,0)

    }

    @Test 
// agrego mas traslados y los despacho menos del mayor perdida(contenido en los nuevps traslados)
    public void esDeMayorPerdida(){
        int cantCiudades=4;
        Traslado[] traslados= {
            new Traslado(1, 0, 1, 300, 15),
            new Traslado(2, 1, 2, 200, 15),
            new Traslado(3, 2, 3, 100, 15),
        };
        
        BestEffort sistema= new BestEffort(cantCiudades,traslados);
        sistema.registrarTraslados(traslados);
               

    }
// caso, todos los despachos son iguales los n mas redituables son la long del arreglo, tienen la misma ganancia

}
