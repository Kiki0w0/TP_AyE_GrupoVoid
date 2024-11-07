// CLASES AUXILIARES

Clase Traslado{
    int id
    int origen
    int destino
    int gananciaNeta
    int timestamp
}

Clase Ciudad {
    int id
    int ganancia
    int perdida 
}

Clase Heap<T extends Comparable<T>>{
    ArrayList<T> datos
    Comparator<T> comparador

    proc construirHeap(in elems: ArrayList<T>, in comparador : Comparator<T>): Heap<T>{
        // implementar
    } 
    proc extraerMax(inout h: Heap<T>): T {
        // implementar
    }
    proc insertar(inout h: Heap<T>, in e: T){
        // implementar
    }
    proc reordenarHeap(ionut h: Heap<T>){
        // implementar
    }
}

Clase ComparadorGananciaNeta implements Comparator<Traslado>{
    @Override 
    public int compare(Traslado tras1, Traslado tras2){
        return Integer.compare(tras1.gananciaNeta, tras2.gananciaNeta);
    }
}

Clase ComparadorAntiguedad implements Comparator<Traslado>{
    @Override 
    public int compare(Traslado tras1, Traslado tras2){
        return Integer.compare(tras1.timestamp, tras2.timestamp);
    }
}

Class ComparadorID implements Comparator<Ciudad>{
    @Override 
    public int compare(Ciudad ciud1, Ciudad ciud2){
        return Integer.compare(ciud1.id, ciud2.id);
    }
}

Clase ComparadorGanancia implements Comparator<Ciudad>{
    @Override 
    public int compare(Ciudad ciud1, Ciudad ciud2){
        return Integer.compare(ciud1.ganancia, ciud2.ganancia);
    }
}

Clase ComparadorPerdida implements Comparator<Ciudad>{
    @Override 
    public int compare(Ciudad ciud1, Ciudad ciud2){
        return Integer.compare(ciud1.perdida, ciud2.perdida);
    }
}

Clase ComparadorSuperavit implements Comparator<Ciudad>{
    @Override 
    public int compare(Ciudad ciud1, Ciudad ciud2){
        int superavit1 = ciud1.ganancia - ciud2.perdida
        int superavit2 = ciud2.ganancia - ciud2.perdida
        if (superavit1 != superavit2){
            return Integer.compare(superavit1,superavit2)
        } else {
            return Integer.compare(ciud1.id, ciud2.id)
        }
    }
}


// CLASE BESTEFFORT

Class BestEffort{
    Traslado[] trasladosGanancias // heap cuya prioridad es el traslado con mayor ganancia
    Traslado[] trasladosAntiguedad // heap cuya prioridad es el traslado mas antiguo 
    Ciudad[] ciudadesInfo // array con info de ciudades cuyo indice representa la ciudad
    Ciudad[] ciudadesOrden
    int[] ciudadMayorGanancia
    int[] ciudadMayorPerdida
    int ciudadMayorSuperavit
    int gananciaTotal
    int cantTraslados


proc nuevoSistema(cantCiudades: N, in traslados: seq<InfoTraslado>): BestEffort {
    trasladosGanancias = construirHeap(traslados, new ComparadorGananciaNeta())
    trasladosAntiguedad = construirHeap(traslados, new ComparadorAntiguedad())
    Ciudad[] ciudades = construirConurbano(cantCiudades)
    ciudadesInfo = construirHeap(ciudades, new ComparadorID())
    ciudadesOrden = construirHeap(ciudades, new ComparadorID{})
    ciudadMayorGanancia = new ArrayList<T>
    ciudadMayorPerdida = new ArrayList<T>
    ciudadMayorSuperavit = -1
    gananciaTotal = 0
    cantTraslados = 0
}

proc registrarTraslados(inout sistema: BestEffort, in traslados: seq<InfoTraslado>) {
    for (int i = 0, traslados.length - 1, i++) {
        sistema.trasladosGanancias.insertar(traslados[i])
        sistema.trasladosAntiguedad.insertar(traslados[i])
    }
}

proc despacharMasRedituables(inout sistema: BestEffort, in n: N): seq<N> {
    m = sistema.trasladosGanancias.length
    if (n > m) {
        n = m
    }
    int[] despachos = new int[n]
    for (int i = 0, n - 1, i++) {
        Traslado despacho = extraerMax(sistema.trasladosGanancias)
        despachos[i] = despacho.id
        ciudadesInfo.actualizar(despacho)
        gananciaTotal += despacho.gananciaNeta
        cantTraslados++
    }
    ciudadMayorGanancia.agregarFinal(reordenarHeap(ciudadesOrden)[0].id)
    ciudadMayorPerdida.agregarFinal(reordenarHeap(ciudadesOrden)[0].id)
    ciudadMayorSuperavit = reordenarHeap(ciudadesOrden)[0].id
}

proc despacharMasAntiguos(inout sistema: BestEffort, in n: N): seq<N>{
    m = sistema.trasladosAntiguedad.length
    if (n > m) {
        n = m
    }
    int[] despachos = new int[n]
    for(int i = 0, n -1, i++){
        Traslado despacho = extraerMax(sistema.trasladosAntiguedad)
        despachos[i] = despacho.id
        ciudadesInfo.actualizar(despacho)
        gananciaTotal += despacho.gananciaNeta
        cantDeTraslados ++
    }
    ciudadMayorGanancia.agregarFinal(reordenarHeap(ciudadesOrden)[0].id)
    ciudadMayorPerdida.agregarFinal(reordenarHeap(ciudadesOrden)[0].id)
    ciudadMayorSuperavit = reordenarHeap(ciudadesOrden)[0].id
}

proc actualizar(inout ciudadesInfo : Heap<Ciudad>, in despacho : Traslado){
    int origen = despacho.origen
    int destino = despacho.destino
    ciudadesInfo[origen].ganancia += despacho.gananciaNeta
    ciudadesInfo[destino].perdida -= despacho.gananciaNeta
    
}
}