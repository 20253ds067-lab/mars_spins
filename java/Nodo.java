// =====================================================
// PROYECTO INTEGRADOR: MARS SPINS
// Estructura de Datos - Cuatrimestre 4
// 
// Nodo.java
// Representa un nodo individual de la Pila (Mazo de cartas)
// Patron de diseno visto en clase (Practica - pilas / practica1 y 2)
// =====================================================

public class Nodo {
    String carta;       // Dato que almacena el nodo (ej: "As de Picas", "Rey de Corazones")
    Nodo siguiente;     // Referencia o apuntador al siguiente nodo de la pila

    // Constructor del nodo
    public Nodo(String carta) {
        this.carta = carta;
        this.siguiente = null;
    }
}
