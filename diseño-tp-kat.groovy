// CLASES AUXILIARES

Clase Traslado{
    int idGanancia
    int idAntiguedad
    int id
    int origen
    int destino
    int gananciaNeta
    int timestamp

    public traslado (int id, int origen, int destino, gananciaNeta, timestamp)
        this.idGanancia = -1
        this.idAntiguedad = -1
        this.id = id
        ...
}

Clase Ciudad {
    int id
    int ganancia
    int perdida 

    proc Ciudad(in id: int){
        this.id = id
        this.ganancia = 0
        this.perdida = 0
    }
        
}

Clase Heap<T extends Comparable<T>>{
    ArrayList<T> datos
    proc construirHeap(in elems: ArrayList<T>, in comparador: Comparador<T>): Heap<T>{ // O(n)
        // construye el heap utilizando el comparador pasado como parametro
        // algoritmo de floyd?

    proc extraerMax(inout h: Heap<T>): T { // O(log n)
        // extrae la raiz y luego acomoda el heap 
    }
    proc insertar(inout h: Heap<T>, in e: T){ // O(log n)
        // inserta un elemento y luego acomoda el heap 
    }
    proc reordenarHeap(ionut h: Heap<T>, in nuevoComparador : Comparador<T>){ // O(n) ?
        // ordena el heap segun el nuevo comparador que le pasemos
    }

    proc eliminarEnIndice(inout h: Heap<T>, in indice: int){ // O(log n)
        // elimina un indice en especifico y ordena el heap 
    }
}


// COMPARADORES
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
        int superavit1 = ciud1.ganancia - ciud1.perdida
        int superavit2 = ciud2.ganancia - ciud2.perdida
        if (superavit1 != superavit2){
            return Integer.compare(superavit1,superavit2)
        } else {
            return Integer.compare(ciud1.id, ciud2.id)
        }
    }
}


// CLASE BESTEFFORT

Clase BestEffort{
    Heap<Traslado> trasladosGanancias // heap cuya prioridad es el traslado con mayor ganancia
    Heap<Traslado> trasladosAntiguedad // heap cuya prioridad es el traslado mas antiguo 
    Ciudad[] ciudadesInfo // array con info de ciudades cuyo indice representa la ciudad
    Heap<Ciudad> ciudadesOrden // heap de ciudades
    ArrayList<int> ciudadMayorGanancia // arraylist que almacena ciudades con mayor ganancia
    ArrayList<int> ciudadMayorPerdida // arraylist que almacena ciudades con mayor perdida
    int ciudadMayorSuperavit // guarda ciudad con mayor superavit
    int gananciaTotal // contador ganancia
    int cantTraslados // contador traslados


proc nuevoSistema(cantCiudades: N, in traslados: Traslado[]): BestEffort {
    Traslado[] trasladosIdentificados = new Traslado[|traslados|]
    for (int i = 0, |traslados| - 1, i++) {
        Traslado nuevoTraslado = new Traslado(traslado[i])
        trasladosIdentificados[i] = nuevoTraslado 
    }
    trasladosGanancias = new construirHeap(trasladosIdentificados, new ComparadorGananciaNeta())
    trasladosAntiguedad = new construirHeap(trasladosIdentificados, new ComparadorAntiguedad())
    Ciudad[] ciudades = construirConurbano(cantCiudades)
    ciudadesOrden = construirHeap(ciudades, new ComparadorID())
    ciudadMayorGanancia = new int[cantCiudades]
    ciudadMayorPerdida = new int[cantCiudades]
    ciudadMayorSuperavit = -1
    gananciaTotal = 0
    cantTraslados = 0
}
proc construirConurbano(in cantCiudades: int) : Ciudad[]{
    res = new Ciudad[cantCiudades]
    for (int i = 0, cantCiudades - 1, i++){
        res[i] = new Ciudad(i)
    }
}
proc registrarTraslados(inout sistema: BestEffort, in traslados: Traslado[]) {
    for (int i = 0, traslados.length - 1, i++) {
        sistema.trasladosGanancias.insertar(traslados[i])
        sistema.trasladosAntiguedad.insertar(traslados[i])
    }
}

proc despacharMasRedituables(inout sistema: BestEffort, in n: int): int[] {
    m = sistema.trasladosGanancias.datos.size()
    if (n > m) {
        n = m
    }
    int[] despachos = new int[n]
    for (int i = 0, n - 1, i++) {
        Traslado despacho = extraerMax(sistema.trasladosGanancias)
        despachos[i] = despacho.id
        int indice = despacho.idAntiguedad
        ciudadesInfo.actualizar(despacho)
        sistema.trasladosAntiguedad[indice] = sistema.trasladosAntiguedad[|sistema.trasladosAntiguedad| - 1]
        sistema.trasladosAntiguedad[indice].idAntiguedad = indice
        istema.trasladosAntiguedad.eliminarUltimo()
        istema.trasladosAntiguedad[indice].heapBajar()
        gananciaTotal += despacho.gananciaNeta
        cantTraslados++
    }
    ciudadMayorGanancia.agregarFinal(reordenarHeap(ciudadesOrden, ComparadorGanancia)[0].id)
    ciudadMayorPerdida.agregarFinal(reordenarHeap(ciudadesOrden,ComparadorPerdida)[0].id)
    ciudadMayorSuperavit = reordenarHeap(ciudadesOrden, ComparadorSuperavit)[0].id
}

proc despacharMasAntiguos(inout sistema: BestEffort, in n: N): int[]{
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
    ciudadMayorGanancia.agregarFinal(reordenarHeap(ciudadesOrden, ComparadorGanancia)[0].id)
    ciudadMayorPerdida.agregarFinal(reordenarHeap(ciudadesOrden, ComparadorPerdida)[0].id)
    ciudadMayorSuperavit = reordenarHeap(ciudadesOrden, ComparadorSuperavit)[0].id
}

proc actualizar(inout ciudadesInfo : Ciudad[], in despacho : Traslado){ // O(1)
    int origen = despacho.origen
    int destino = despacho.destino
    ciudadesInfo[origen].ganancia += despacho.gananciaNeta
    ciudadesInfo[destino].perdida += despacho.gananciaNeta
    
}

proc ciudadConMayorSuperavit(in sistema: BestEffort) : int{ // O(1)
    return sistema.ciudadMayorSuperavit
}

proc ciudadesMayorGanancia(in sistema: BestEffort) : ArrayList<int>{ // O(1)
    return sistema.ciudadMayorGanancia
}

proc ciudadesConMayorPerdida(in sistema: BestEffort) : ArrayList<int>{ // O(1)
    return sistema.ciudadMayorPerdida
}

proc gananciaPromedioPorTraslado(in sistema: BestEffort): int{ // O(1)
    return gananciaTotal / cantTraslados
}   

}


