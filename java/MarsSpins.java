// =====================================================
// PROYECTO INTEGRADOR: MARS SPINS
// Estructura de Datos - Cuatrimestre 4
// 
// INTEGRANTES:
// - Barron Flores Luis Gerardo
// - Apreza Gutierrez Carlos
// - Chavez Garcia Miguel
// - Hernandez Tranquilino Angel Jesus
// - Velez Reyes Gael Ixaya
// - Hurtado Hernandez Jose Elias
//
// MarsSpins.java
// Implementacion y demostracion en consola de las Estructuras de Datos:
//   1. Arreglos (Arrays): Casillas de la Ruleta (tamano fijo 37, acceso O(1))
//   2. Pilas (Stacks - LIFO): Mazo de cartas de Blackjack (Push / Pop / Peek)
//   3. TAD_CuentaUsuario: Encapsulamiento y proteccion del saldo ficticio
// =====================================================

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class MarsSpins {

    // Helper para lectura segura de lineas
    public static String leer(Scanner sc) {
        if (sc.hasNextLine()) {
            return sc.nextLine().trim();
        }
        return "";
    }

    // =========================================================================
    // 1. ESTRUCTURA: ARREGLO (ARRAY)
    // Datos que almacena: Casillas fijas de la ruleta (0 al 36 con sus colores).
    // Justificacion: Tamano constante (37), acceso O(1) directo mediante indices.
    // =========================================================================
    static String[] ruleta = new String[37];

    static void inicializarRuleta() {
        int[] rojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        ruleta[0] = "0 - Verde";
        for (int i = 1; i <= 36; i++) {
            boolean esRojo = false;
            for (int r : rojos) {
                if (r == i) {
                    esRojo = true;
                    break;
                }
            }
            ruleta[i] = i + (esRojo ? " - Rojo" : " - Negro");
        }
    }

    // =========================================================================
    // 2. ESTRUCTURA: PILA (STACK - LIFO)
    // Datos que almacena: Cartas de la baraja para Blackjack.
    // Justificacion: Modela fisicamente un mazo. Se mezclan y apilan con Push(),
    // y al repartir se extrae siempre la carta de la cima con Pop().
    // =========================================================================
    static Pila crearMazoMezclado() {
        String[] palos = {"Picas", "Corazones", "Diamantes", "Treboles"};
        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        ArrayList<String> barajaAux = new ArrayList<>();
        for (String p : palos) {
            for (String v : valores) {
                barajaAux.add(v + " de " + p);
            }
        }

        // Se mezclan las cartas
        Collections.shuffle(barajaAux);

        // Se apilan una a una en la Pila mediante push()
        Pila mazoPila = new Pila();
        for (String c : barajaAux) {
            mazoPila.push(c);
        }

        return mazoPila;
    }

    static int obtenerValorNumerico(String carta) {
        String[] partes = carta.split(" de ");
        String val = partes[0].trim();
        if (val.equals("A")) {
            return 11;
        }
        if (val.equals("J") || val.equals("Q") || val.equals("K")) {
            return 10;
        }
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return 10;
        }
    }

    static int calcularPuntajeMano(ArrayList<String> mano) {
        int total = 0;
        int ases = 0;
        for (String c : mano) {
            int v = obtenerValorNumerico(c);
            total += v;
            if (c.startsWith("A de ")) {
                ases++;
            }
        }
        // Ajuste del valor del As (pasa de 11 a 1 si se pasa de 21)
        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }
        return total;
    }

    // =========================================================================
    // MODULO: JUEGO DE BLACKJACK (PILA + TAD_CUENTA)
    // =========================================================================
    static void jugarBlackjack(TAD_CuentaUsuario cuenta, Scanner sc) {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|              MARS SPINS - BLACKJACK VIP             |");
        System.out.println("|  [Demostracion de Estructura: Pila Dinamica LIFO]   |");
        System.out.println("+-----------------------------------------------------+");

        // 1. Creacion de la Pila con 52 cartas
        Pila mazo = crearMazoMezclado();
        System.out.println("-> Mazo creado: 52 cartas apiladas con push().");
        System.out.println("-> Cima actual de la pila (peek()): " + mazo.peek());
        System.out.println("-> Cartas en pila: " + mazo.getTamanio());

        // 2. Apuesta a traves del TAD_CuentaUsuario
        System.out.printf("%nSaldo disponible: $%.2f ficticios%n", cuenta.obtenerSaldo());
        System.out.print("Ingresa el monto a apostar: $");
        double apuesta = 0;
        try {
            apuesta = Double.parseDouble(leer(sc));
        } catch (Exception e) {
            System.out.println("Monto invalido.");
            return;
        }

        // TAD_CuentaUsuario realiza la validacion y resta protegida
        if (!cuenta.realizarApuesta(apuesta)) {
            System.out.println("[Error] Fondos insuficientes. Tu saldo actual es: $" + cuenta.obtenerSaldo());
            return;
        }
        System.out.println("[TAD] Apuesta aceptada. Saldo restante: $" + cuenta.obtenerSaldo());

        // 3. Repartir 2 cartas a jugador y 2 al dealer usando pop()
        ArrayList<String> manoJugador = new ArrayList<>();
        ArrayList<String> manoDealer = new ArrayList<>();

        System.out.println("\n--- Repartiendo cartas iniciales usando pop() de la cima ---");
        manoJugador.add(mazo.pop()); // pop() 1
        manoDealer.add(mazo.pop());  // pop() 2
        manoJugador.add(mazo.pop()); // pop() 3
        manoDealer.add(mazo.pop());  // pop() 4

        System.out.println("-> Cartas restantes en pila tras repartir: " + mazo.getTamanio());
        System.out.println("-> Tu mano       : " + manoJugador + " [Puntos: " + calcularPuntajeMano(manoJugador) + "]");
        System.out.println("-> Mano del Dealer: [" + manoDealer.get(0) + ", <Carta Oculta>]");

        // 4. Turno del Jugador
        boolean turnoActivo = true;
        while (turnoActivo && calcularPuntajeMano(manoJugador) < 21) {
            System.out.println("\n¿Que accion deseas tomar?");
            System.out.println("  1. Pedir carta (pop() de la Pila)");
            System.out.println("  2. Plantarse");
            System.out.print("Selecciona (1 o 2): ");
            String op = leer(sc);

            if (op.equals("1")) {
                String cartaNueva = mazo.pop();
                manoJugador.add(cartaNueva);
                System.out.println("\n[POP] Carta extraida de la cima: " + cartaNueva);
                System.out.println("-> Tu mano actual: " + manoJugador + " [Puntos: " + calcularPuntajeMano(manoJugador) + "]");
                System.out.println("-> Cartas restantes en la pila: " + mazo.getTamanio());
            } else {
                turnoActivo = false;
            }
        }

        int ptosJugador = calcularPuntajeMano(manoJugador);

        // 5. Turno del Dealer
        System.out.println("\n--- Turno del Crupier / Dealer ---");
        System.out.println("-> Mano completa del Dealer: " + manoDealer);
        while (calcularPuntajeMano(manoDealer) < 17 && ptosJugador <= 21) {
            String cartaDealer = mazo.pop();
            manoDealer.add(cartaDealer);
            System.out.println("[Dealer POP] Extrae de la cima: " + cartaDealer);
        }
        int ptosDealer = calcularPuntajeMano(manoDealer);

        // 6. Evaluacion del resultado y actualizacion mediante el TAD
        System.out.println("\n==========================================");
        System.out.println("           RESULTADO DE LA RONDA          ");
        System.out.println("==========================================");
        System.out.println("Puntaje Jugador : " + ptosJugador);
        System.out.println("Puntaje Dealer  : " + ptosDealer);

        if (ptosJugador > 21) {
            System.out.println("Te pasaste de 21 puntos. Pierdes la apuesta.");
        } else if (ptosJugador == 21 && manoJugador.size() == 2) {
            double premio = apuesta * 2.5;
            System.out.printf("¡BLACKJACK NATURAL! Ganaste $%.2f ficticios (pago 2.5x).%n", premio);
            cuenta.registrarResultado(premio);
        } else if (ptosDealer > 21) {
            double premio = apuesta * 2.0;
            System.out.printf("¡El Dealer se paso de 21! Ganaste $%.2f ficticios.%n", premio);
            cuenta.registrarResultado(premio);
        } else if (ptosJugador > ptosDealer) {
            double premio = apuesta * 2.0;
            System.out.printf("¡Tu mano supera a la del Dealer! Ganaste $%.2f ficticios.%n", premio);
            cuenta.registrarResultado(premio);
        } else if (ptosJugador == ptosDealer) {
            System.out.println("Empate. Se devuelve tu apuesta integra.");
            cuenta.registrarResultado(apuesta);
        } else {
            System.out.println("El Dealer gana la mano.");
        }

        cuenta.mostrarEstado();
    }

    // =========================================================================
    // MODULO: JUEGO DE RULETA (ARREGLO ruleta[37] + TAD_CUENTA)
    // =========================================================================
    static void jugarRuleta(TAD_CuentaUsuario cuenta, Scanner sc) {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|               MARS SPINS - RULETA                   |");
        System.out.println("|  [Demostracion de Estructura: Arreglo Fijo O(1)]   |");
        System.out.println("+-----------------------------------------------------+");

        System.out.println("Arreglo ruleta[37] con casillas fijas:");
        System.out.println("  Indice [0] : " + ruleta[0]);
        System.out.println("  Indice [1] : " + ruleta[1]);
        System.out.println("  ...");
        System.out.println("  Indice [36]: " + ruleta[36]);

        System.out.printf("%nSaldo disponible: $%.2f ficticios%n", cuenta.obtenerSaldo());
        System.out.print("¿A que color deseas apostar? (rojo / negro): ");
        String colorApuesta = leer(sc).toLowerCase();

        if (!colorApuesta.equals("rojo") && !colorApuesta.equals("negro")) {
            System.out.println("Color no valido. Debe ser 'rojo' o 'negro'.");
            return;
        }

        System.out.print("Ingresa el monto a apostar: $");
        double monto = 0;
        try {
            monto = Double.parseDouble(leer(sc));
        } catch (Exception e) {
            System.out.println("Monto invalido.");
            return;
        }

        if (!cuenta.realizarApuesta(monto)) {
            System.out.println("[Error] Saldo insuficiente.");
            return;
        }

        // Generacion aleatoria del indice y acceso en complejidad O(1)
        Random rnd = new Random();
        int indice_ganador = rnd.nextInt(37);
        String casilla_ganadora = ruleta[indice_ganador]; // Acceso O(1) por indice

        System.out.println("\nGirando la ruleta...");
        System.out.println("-> Indice aleatorio generado: " + indice_ganador);
        System.out.println("-> Casilla ganadora obtenida ruleta[" + indice_ganador + "]: " + casilla_ganadora);

        boolean gano = casilla_ganadora.toLowerCase().contains(colorApuesta);
        if (gano) {
            double ganancia = monto * 2.0;
            System.out.printf("¡Acertaste! Se te abonan $%.2f ficticios.%n", ganancia);
            cuenta.registrarResultado(ganancia);
        } else {
            System.out.println("No acertaste el color en esta tirada.");
        }

        cuenta.mostrarEstado();
    }

    // =========================================================================
    // MODULO: DEMOSTRACION PASO A PASO DE LA PILA (PARA EL VIDEO)
    // Cumple con los puntos requeridos de la rubrica:
    // 1. Que datos almacena
    // 2. Como interviene (Push, Pop, Peek, estaVacia)
    // 3. Por que fue seleccionada (justificacion LIFO)
    // =========================================================================
    static void demostracionPasoAPasoPila() {
        System.out.println("\n=======================================================");
        System.out.println("     DEMOSTRACION DE ESTRUCTURA DE DATOS: PILA (LIFO)  ");
        System.out.println("             (Para el Video de Entrega)                ");
        System.out.println("=======================================================");

        System.out.println("\n[1. EXPLICACION TEORICA Y JUSTIFICACION]");
        System.out.println("  * Que datos almacena: Las cartas de la baraja para Blackjack.");
        System.out.println("  * Principio de operacion: LIFO (Last In, First Out).");
        System.out.println("  * Justificacion: Una pila replica con exactitud el comportamiento");
        System.out.println("    fisico de un mazo de cartas: al mezclar se apilan (push),");
        System.out.println("    y al repartir, la carta entregada es siempre la que esta");
        System.out.println("    en la cima del mazo (pop), asegurando que ninguna carta");
        System.out.println("    se repita hasta volver a barajar.");

        System.out.println("\n[2. CREACION DE LA PILA]");
        Pila demoPila = new Pila();
        System.out.println("  -> Pila creada. ¿Esta vacia? " + demoPila.estaVacia());

        System.out.println("\n[3. OPERACION PUSH (Apilar cartas)]");
        System.out.println("  -> push(\"10 de Diamantes\")");
        demoPila.push("10 de Diamantes");
        System.out.println("  -> push(\"Reina de Corazones\")");
        demoPila.push("Reina de Corazones");
        System.out.println("  -> push(\"As de Picas\")");
        demoPila.push("As de Picas");

        System.out.println("\n  Contenido actual de la pila tras 3 push():");
        demoPila.mostrar();

        System.out.println("\n[4. OPERACION PEEK (Consultar la cima sin extraer)]");
        System.out.println("  -> peek(): " + demoPila.peek());
        System.out.println("  -> Cantidad de elementos (tamanio): " + demoPila.getTamanio());

        System.out.println("\n[5. OPERACION POP (Desapilar / Repartir carta)]");
        String cartaExtraida1 = demoPila.pop();
        System.out.println("  -> Se ejecuta pop(). Carta extraida de la cima: " + cartaExtraida1);
        System.out.println("     (Observe que salio 'As de Picas', la ultima en entrar)");

        System.out.println("\n  Contenido de la pila despues del primer pop():");
        demoPila.mostrar();

        String cartaExtraida2 = demoPila.pop();
        System.out.println("\n  -> Segundo pop(). Carta extraida: " + cartaExtraida2);
        System.out.println("  -> Nueva cima (peek()): " + demoPila.peek());
        System.out.println("  -> Cantidad restante: " + demoPila.getTamanio());

        System.out.println("\n=======================================================");
        System.out.println("     FIN DE LA DEMOSTRACION DE LA PILA                 ");
        System.out.println("=======================================================");
    }

    // =========================================================================
    // MENU PRINCIPAL
    // =========================================================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        inicializarRuleta();

        System.out.println("+-----------------------------------------------------+");
        System.out.println("|           PROYECTO INTEGRADOR: MARS SPINS           |");
        System.out.println("|         Simulador de Casino con Saldo Ficticio      |");
        System.out.println("+-----------------------------------------------------+");

        System.out.print("Ingresa tu nombre de usuario para iniciar: ");
        String nombre = leer(sc);
        if (nombre.isEmpty()) {
            nombre = "Carlos Apreza";
        }

        // Instanciacion del TAD_CuentaUsuario
        TAD_CuentaUsuario cuenta = new TAD_CuentaUsuario(1, nombre, 1000.0);
        System.out.println("\n¡Bienvenido al sistema, " + nombre + "!");
        cuenta.mostrarEstado();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("|                     LOBBY PRINCIPAL                 |");
            System.out.println("+-----------------------------------------------------+");
            System.out.println("  1. Jugar BlackJack (Estructura: Pila LIFO)");
            System.out.println("  2. Jugar Ruleta (Estructura: Arreglo O(1))");
            System.out.println("  3. Demostracion paso a paso de la Pila (Para Video)");
            System.out.println("  4. Consultar Saldo / TAD_CuentaUsuario");
            System.out.println("  0. Salir");
            System.out.print("Selecciona una opcion: ");

            String opcion = leer(sc);
            if (opcion.isEmpty()) {
                break;
            }

            switch (opcion) {
                case "1":
                    jugarBlackjack(cuenta, sc);
                    break;
                case "2":
                    jugarRuleta(cuenta, sc);
                    break;
                case "3":
                    demostracionPasoAPasoPila();
                    break;
                case "4":
                    cuenta.mostrarEstado();
                    break;
                case "0":
                    salir = true;
                    System.out.println("\nGracias por jugar en Mars Spins. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta nuevamente.");
                    break;
            }
        }

        sc.close();
    }
}
