// =====================================================
// PROYECTO INTEGRADOR: MARS SPINS
// Estructura de Datos - Cuatrimestre 4
// 
// TAD_CuentaUsuario.java
// Tipo de Dato Abstracto (TAD) para la gestion segura del saldo ficticio
// Protege el saldo mediante encapsulamiento (privado).
// =====================================================

public class TAD_CuentaUsuario {

    // Atributos privados (encapsulamiento)
    private int id_usuario;
    private String nombre;
    private double saldo_actual;

    // Operacion: iniciarCuenta(id, nombre, saldo_inicial)
    public TAD_CuentaUsuario(int id_usuario, String nombre, double saldo_inicial) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.saldo_actual = saldo_inicial;
    }

    // Metodo alternativo de inicializacion
    public void iniciarCuenta(int id, String nom, double saldo) {
        this.id_usuario = id;
        this.nombre = nom;
        this.saldo_actual = saldo;
    }

    // Operacion: obtenerSaldo()
    public double obtenerSaldo() {
        return this.saldo_actual;
    }

    // Getter del nombre
    public String getNombre() {
        return this.nombre;
    }

    // Getter del id
    public int getIdUsuario() {
        return this.id_usuario;
    }

    // Operacion: realizarApuesta(cantidad)
    // Verifica si cantidad <= saldo_actual.
    // Si es valido, resta la cantidad del saldo y retorna true.
    // Si no es suficiente, retorna false y protege el saldo de valores negativos.
    public boolean realizarApuesta(double cantidad) {
        if (cantidad > 0 && cantidad <= this.saldo_actual) {
            this.saldo_actual -= cantidad;
            return true;
        }
        return false;
    }

    // Operacion: registrarResultado(ganancia_obtenida)
    // Suma la ganancia ficticia obtenida al saldo_actual tras concluir la jugada.
    public void registrarResultado(double ganancia_obtenida) {
        this.saldo_actual += ganancia_obtenida;
    }

    // Muestra el estado actual del TAD en consola
    public void mostrarEstado() {
        System.out.println("+-----------------------------------------+");
        System.out.println("|       TAD_CuentaUsuario (Encapsulado)   |");
        System.out.println("+-----------------------------------------+");
        System.out.println("  ID Usuario     : " + this.id_usuario);
        System.out.println("  Titular        : " + this.nombre);
        System.out.printf ("  Saldo Ficticio : $%.2f%n", this.saldo_actual);
        System.out.println("+-----------------------------------------+");
    }
}
