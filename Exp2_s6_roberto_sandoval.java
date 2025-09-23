package exp2_s6_roberto_sandoval;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Exp2_s6_roberto_sandoval {

    static String nombreTeatro = "Teatro Moro";

    static boolean A1 = false, A2 = false, A3 = false;
    static boolean B1 = false, B2 = false, B3 = false;
    static boolean C1 = false, C2 = false, C3 = false;

    static String reservaPendienteCodigo = null;
    static Timer temporizador = null;
    static long reservaPendienteLimite = 0;
    static int totalAPagar = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            // Mostrar menú principal
            System.out.println("\n=== " + nombreTeatro + " ===");
            System.out.println("1. Reserva de asiento");
            System.out.println("2. Confirmar reserva");
            System.out.println("3. Comprar entrada");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Modificar Reserva");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // Lógica para reservar asiento
                    mostrarMapaAsientos();

                    if (reservaPendienteCodigo != null) {
                        System.out.println("Ya existe una reserva pendiente (" + reservaPendienteCodigo + "). Debe confirmarla o modificarla en el menú principal.");
                        break;
                    }

                    System.out.print("Ingrese el asiento a reservar (ej: A1, b1, c1): ");
                    String codigo = scanner.nextLine().trim().toUpperCase();

                    if (codigo.length() < 2) {
                        System.out.println("Código Inválido.");
                        break;
                    }

                    char fila = codigo.charAt(0);
                    String numTxt = codigo.substring(1);
                    int nro;

                    try {
                        nro = Integer.parseInt(numTxt);
                    } catch (NumberFormatException e) {
                        System.out.println("Número del Asiento es inválido.");
                        break;
                    }

                    if (fila < 'A' || fila > 'C' || nro < 1 || nro > 3) {
                        System.out.println("Asientos fuera de rango (fila A-C, Columnas 1-3).");
                        break;
                    }

                    boolean ocupado = false;
                    switch (fila) {
                        case 'A':
                            if (nro == 1) ocupado = A1;
                            else if (nro == 2) ocupado = A2;
                            else if (nro == 3) ocupado = A3;
                            break;
                        case 'B':
                            if (nro == 1) ocupado = B1;
                            else if (nro == 2) ocupado = B2;
                            else if (nro == 3) ocupado = B3;
                            break;
                        case 'C':
                            if (nro == 1) ocupado = C1;
                            else if (nro == 2) ocupado = C2;
                            else if (nro == 3) ocupado = C3;
                            break;
                    }

                    if (ocupado) {
                        System.out.println("El asiento " + codigo + " ya está ocupado. Por favor, elija otro.");
                        break;
                    }

                    int precio = 0;
                    switch (fila) {
                        case 'A': precio = 20000; break;
                        case 'B': precio = 18000; break;
                        case 'C': precio = 16000; break;
                    }

                    reservaPendienteCodigo = codigo;
                    reservaPendienteLimite = System.currentTimeMillis() + 5 * 60 * 1000;

                    try {
                        if (temporizador != null) {
                            temporizador.cancel();
                        }
                    } catch (Exception e) {}

                    temporizador = new Timer(true);
                    temporizador.schedule(new TimerTask() {
                        public void run() {
                            if (reservaPendienteCodigo != null && System.currentTimeMillis() >= reservaPendienteLimite) {
                                System.out.println("\n*** Tiempo Agotado: la reserva de " + reservaPendienteCodigo + " Expiró. ***");
                                reservaPendienteCodigo = null;
                            }
                        }
                    }, 5 * 60 * 1000);

                    System.out.println("Asiento " + codigo + " reservado temporalmente por $" + precio + ".");
                    System.out.println("Tienes 5 minutos para confirmar la reserva en el menú principal (opción 2).");
                    break;

                case 2:
                    // Lógica para confirmar reserva
                    if (reservaPendienteCodigo == null) {
                        System.out.println("No hay reservas pendientes para confirmar.");
                        break;
                    }

                    if (System.currentTimeMillis() > reservaPendienteLimite) {
                        System.out.println("La reserva ya expiró. Por favor, vuelva a reservar.");
                        reservaPendienteCodigo = null;
                        if (temporizador != null) temporizador.cancel();
                        break;
                    }

                    char filaConfirmar = reservaPendienteCodigo.charAt(0);
                    int nroConfirmar = Integer.parseInt(reservaPendienteCodigo.substring(1));

                    switch (filaConfirmar) {
                        case 'A':
                            if (nroConfirmar == 1) A1 = true;
                            else if (nroConfirmar == 2) A2 = true;
                            else if (nroConfirmar == 3) A3 = true;
                            break;
                        case 'B':
                            if (nroConfirmar == 1) B1 = true;
                            else if (nroConfirmar == 2) B2 = true;
                            else if (nroConfirmar == 3) B3 = true;
                            break;
                        case 'C':
                            if (nroConfirmar == 1) C1 = true;
                            else if (nroConfirmar == 2) C2 = true;
                            else if (nroConfirmar == 3) C3 = true;
                            break;
                    }

                    int precioConfirmar = 0;
                    switch (filaConfirmar) {
                        case 'A': precioConfirmar = 20000; break;
                        case 'B': precioConfirmar = 18000; break;
                        case 'C': precioConfirmar = 16000; break;
                    }
                    totalAPagar += precioConfirmar;

                    System.out.println("Reserva del asiento " + reservaPendienteCodigo + " confirmada con éxito.");
                    reservaPendienteCodigo = null;
                    if (temporizador != null) temporizador.cancel();
                    break;

                case 3:
                    // Lógica para comprar entrada
                    boolean comprado = false;
                    do {
                    mostrarMapaAsientos();

                    System.out.print("Ingrese el código del asiento que desea comprar (ej: A1): ");
                    String codigoComprar = scanner.nextLine().trim().toUpperCase();

                    char filaComprar = codigoComprar.charAt(0);
                    String numTxtComprar = codigoComprar.substring(1);
                    int nroComprar;

                    try {
                        nroComprar = Integer.parseInt(numTxtComprar);
                    } catch (NumberFormatException e) {
                        System.out.println("Número de asiento inválido.");
                        break;
                    }

                    if (filaComprar < 'A' || filaComprar > 'C' || nroComprar < 1 || nroComprar > 3) {
                        System.out.println("Asiento fuera de rango.");
                        break;
                    }
                    
                    // VALIDACIÓN: Verificar si el asiento está ocupado o reservado temporalmente
                    boolean estaOcupado = false;
                    switch (filaComprar) {
                        case 'A':
                            if (nroComprar == 1) estaOcupado = A1;
                            else if (nroComprar == 2) estaOcupado = A2;
                            else estaOcupado = A3;
                            break;
                        case 'B':
                            if (nroComprar == 1) estaOcupado = B1;
                            else if (nroComprar == 2) estaOcupado = B2;
                            else estaOcupado = B3;
                            break;
                        case 'C':
                            if (nroComprar == 1) estaOcupado = C1;
                            else if (nroComprar == 2) estaOcupado = C2;
                            else if (nroComprar == 3) estaOcupado = C3;
                            break;
                    }

                    if (estaOcupado || codigoComprar.equals(reservaPendienteCodigo)) {
                        System.out.println("El asiento " + codigoComprar + " no está disponible. Por favor, elija otro.");
                        break;
                    }
                    // Fin de la validación

                    switch (filaComprar) {
                        case 'A':
                            if (nroComprar == 1) A1 = true;
                            else if (nroComprar == 2) A2 = true;
                            else if (nroComprar == 3) A3 = true;
                            break;
                        case 'B':
                            if (nroComprar == 1) B1 = true;
                            else if (nroComprar == 2) B2 = true;
                            else if (nroComprar == 3) B3 = true;
                            break;
                        case 'C':
                            if (nroComprar == 1) C1 = true;
                            else if (nroComprar == 2) C2 = true;
                            else if (nroComprar == 3) C3 = true;
                            break;
                    }

                    int precioComprar = 0;
                    switch (filaComprar) {
                        case 'A': precioComprar = 20000; break;
                        case 'B': precioComprar = 18000; break;
                        case 'C': precioComprar = 16000; break;
                    }
                    totalAPagar += precioComprar;

                    System.out.println("Entrada para el asiento " + codigoComprar + " comprada. Precio: $" + precioComprar);
                    
                    
                    comprado = true; 
                    } while (!comprado);
                    break;
                    
                    

                case 4:
                    // Lógica para imprimir boleta
                    System.out.println("\n==== BOLETA de Reserva(S) Confirmadas ====");
                    int subtotal = 0;

                    if (A1) { System.out.println("Asiento: A1 - $20.000"); subtotal += 20000; }
                    if (A2) { System.out.println("Asiento: A2 - $20.000"); subtotal += 20000; }
                    if (A3) { System.out.println("Asiento: A3 - $20.000"); subtotal += 20000; }
                    if (B1) { System.out.println("Asiento: B1 - $18.000"); subtotal += 18000; }
                    if (B2) { System.out.println("Asiento: B2 - $18.000"); subtotal += 18000; }
                    if (B3) { System.out.println("Asiento: B3 - $18.000"); subtotal += 18000; }
                    if (C1) { System.out.println("Asiento: C1 - $16.000"); subtotal += 16000; }
                    if (C2) { System.out.println("Asiento: C2 - $16.000"); subtotal += 16000; }
                    if (C3) { System.out.println("Asiento: C3 - $16.000"); subtotal += 16000; }

                    System.out.println("-------------------------------");
                    System.out.println("Total a pagar: $" + subtotal);
                    System.out.println("===============================");
                    break;

                case 5:
                    // Lógica para modificar la reserva
                    if (reservaPendienteCodigo == null) {
                        System.out.println("No hay una reserva pendiente para modificar.");
                        break;
                    }

                    if (System.currentTimeMillis() > reservaPendienteLimite) {
                        System.out.println("La reserva ya expiró. Por favor, vuelva a reservar.");
                        reservaPendienteCodigo = null;
                        if (temporizador != null) temporizador.cancel();
                        break;
                    }
                    
                    // Mostrar mapa de asientos antes de solicitar el nuevo asiento
                    mostrarMapaAsientos();

                    System.out.println("Tu reserva actual es para el asiento " + reservaPendienteCodigo + ".");
                    System.out.print("Ingresa el nuevo asiento que deseas reservar: ");
                    String nuevoCodigo = scanner.nextLine().trim().toUpperCase();

                    // Validar y cambiar el nuevo asiento
                    char nuevaFila = nuevoCodigo.charAt(0);
                    int nuevoNro;
                    try {
                        nuevoNro = Integer.parseInt(nuevoCodigo.substring(1));
                    } catch (NumberFormatException e) {
                        System.out.println("Número de asiento inválido.");
                        break;
                    }

                    if (nuevaFila < 'A' || nuevaFila > 'C' || nuevoNro < 1 || nuevoNro > 3) {
                        System.out.println("El nuevo asiento está fuera de rango.");
                        break;
                    }

                    boolean nuevoOcupado = false;
                    switch (nuevaFila) {
                        case 'A':
                            if (nuevoNro == 1) nuevoOcupado = A1;
                            else if (nuevoNro == 2) nuevoOcupado = A2;
                            else if (nuevoNro == 3) nuevoOcupado = A3;
                            break;
                        case 'B':
                            if (nuevoNro == 1) nuevoOcupado = B1;
                            else if (nuevoNro == 2) nuevoOcupado = B2;
                            else if (nuevoNro == 3) nuevoOcupado = B3;
                            break;
                        case 'C':
                            if (nuevoNro == 1) nuevoOcupado = C1;
                            else if (nuevoNro == 2) nuevoOcupado = C2;
                            else if (nuevoNro == 3) nuevoOcupado = C3;
                            break;
                    }

                    if (nuevoOcupado) {
                        System.out.println("El asiento " + nuevoCodigo + " ya está ocupado. No se puede modificar la reserva.");
                        break;
                    }
                    
                    // Asegurar que el nuevo asiento no sea el mismo que el actual
                    if (nuevoCodigo.equals(reservaPendienteCodigo)) {
                        System.out.println("El asiento ingresado es el mismo que ya tienes reservado. No se realizó ninguna modificación.");
                        break;
                    }

                    // Se cancela la reserva anterior y se crea la nueva
                    reservaPendienteCodigo = nuevoCodigo;
                    reservaPendienteLimite = System.currentTimeMillis() + 5 * 60 * 1000;

                    // Se reinicia el temporizador
                    if (temporizador != null) {
                        temporizador.cancel();
                    }
                    temporizador = new Timer(true);
                    temporizador.schedule(new TimerTask() {
                        public void run() {
                            if (reservaPendienteCodigo != null && System.currentTimeMillis() >= reservaPendienteLimite) {
                                System.out.println("\n*** Tiempo Agotado: la reserva de " + reservaPendienteCodigo + " Expiró. ***");
                                reservaPendienteCodigo = null;
                            }
                        }
                    }, 5 * 60 * 1000);

                    System.out.println("Reserva modificada exitosamente. Tu nuevo asiento es " + nuevoCodigo + ".");
                    break;

                case 0:
                    System.out.println("¡Gracias por ocupar nuestro sistema!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
        scanner.close();
    }
    
    // Método para mostrar el mapa de asientos
    private static void mostrarMapaAsientos() {
        System.out.println("\n=== ..:: RESERVA de ASIENTOS ::.. ===");
        System.out.println("Leyenda: [ ] libre | [R] reservado (pendiente) |[X] ocupado");

        System.out.print("  ");
        for (int i = 1; i <= 3; i++) {
            System.out.print("  " + i + " ");
        }
        System.out.println("");

        System.out.println("A | "
                + (A1 ? "[X]" : ("A1".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " "
                + (A2 ? "[X]" : ("A2".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " "
                + (A3 ? "[X]" : ("A3".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " ($20.000)");
        System.out.println("B | "
                + (B1 ? "[X]" : ("B1".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " "
                + (B2 ? "[X]" : ("B2".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " "
                + (B3 ? "[X]" : ("B3".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " ($18.000)");
        System.out.println("C | "
                + (C1 ? "[X]" : ("C1".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " "
                + (C2 ? "[X]" : ("C2".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " "
                + (C3 ? "[X]" : ("C3".equals(reservaPendienteCodigo) ? "[R]" : "[ ]")) + " ($16.000)");
    } 
}