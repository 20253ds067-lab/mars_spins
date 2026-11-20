// =====================================================
// PROYECTO INTEGRADOR: MARS SPINS
// Estructura de Datos - Cuatrimestre 4
// 
// Pila.java
// Implementacion de una Pila dinamica (LIFO: Last In, First Out)
// Simula el mazo de cartas para el juego de BlackJack.
//
// Operaciones:
//   - push(carta): Apila una carta en la cima del mazo.
//   - pop(): Extrae y retorna la carta de la cima.
//   - peek(): Consulta la carta de la cima sin extraerla.
//   - estaVacia(): Verifica si el mazo no contiene cartas.
//   - mostrar(): Recorre e imprime todas las cartas de la pila.
// =====================================================

public class Pila {
    Nodo cima;      // Apuntador a la cima de la pila (LIFO)
    int tamanio;    // Contador de elementos en la pila

    // Constructor de la pila vacia
    public Pila() {
        this.cima = null;
        this.tamanio = 0;
    }

    // Verifica si la pila esta vacia
    public boolean estaVacia() {
        return this.cima == null;
    }

    // push: Inserta un nuevo elemento en la cima
    // En BlackJack equivale a apilar las cartas al barajar el mazo
    public void push(String carta) {
        Nodo nuevo = new Nodo(carta);
        nuevo.siguiente = this.cima;
        this.cima = nuevo;
        this.tamanio++;
    }

    // pop: Extrae y devuelve el elemento de la cima
    // En BlackJack equivale a repartir la carta superior al jugador o crupier
    public String pop() {
        if (estaVacia()) {
            return null;
        }
        String cartaExtraida = this.cima.carta;
        this.cima = this.cima.siguiente;
        this.tamanio--;
        return cartaExtraida;
    }

    // peek: Retorna el elemento en la cima sin retirarlo
    public String peek() {
        if (estaVacia()) {
            return null;
        }
        return this.cima.carta;
    }

    // mostrar: Imprime el contenido actual de la pila desde la cima hasta el fondo
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("  [La pila esta vacia]");
            return;
        }
        Nodo actual = this.cima;
        int posicion = 1;
        while (actual != null) {
            String etiqueta = (posicion == 1) ? " (CIMA)" : "";
            System.out.println("  [" + posicion + "] " + actual.carta + etiqueta);
            actual = actual.siguiente;
            posicion++;
        }
    }

    // Retorna la cantidad de elementos actuales
    public int getTamanio() {
        return this.tamanio;
    }
}
