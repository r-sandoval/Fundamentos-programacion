package exp3_s7_roberto_sandoval;

import java.util.Scanner;
import java.util.ArrayList;

public class Exp3_s7_roberto_sandoval {

   
static String nombreTeatro = "Teatro Moro";
    static int totalEntradasVendidas = 0;
    static int ingresosTotal = 0;

    static ArrayList<String> ubicacionVendida = new ArrayList<>();
    static ArrayList<Integer> precioBase = new ArrayList<>();
    static ArrayList<Double> descuentosAplicados = new ArrayList<>();
    static ArrayList<Integer> costosFinales = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {

            menu();

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    ventaEntradas(scanner);
                    break;

                case 2:
                    resumenVenta();
                    break;

                case 3:
                    generarTodasLasBoletas();
                    break;

                case 0:
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opcion no válida");
            }

        } while (opcion != 0);
        scanner.close();
    }

    //Metodo para menu
    private static void menu() {
        System.out.println("\n=== Menu Teatro Moro ====");
        // Mostrar menú principal
        System.out.println("\n=== " + nombreTeatro + " ===");
        System.out.println("1. Venta de entradas");
        System.out.println("2. Resumen de ventas");
        System.out.println("3. Generar boletas");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    //Metodo para venta de entrada
    private static void ventaEntradas(Scanner scanner) {
        //Logica de venta de entrada
        String ubicacion = "";
        int valorBase = 0;
        double descuento = 0.0;

        System.out.println("\n=== Comprar entrada ====");
        int opcion = 0;
        boolean validar = false;
        while (!validar) {

            System.out.println("Seleccione ubicacion de entrada");
            System.out.println("1. VIP ($25.000)");
            System.out.println("2. Platea ($18.000)");
            System.out.println("3. Balcón ($12.000)");
            System.out.print("Elija la ubicación (1-3): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                
                switch (opcion) {
                    case 1:
                        ubicacion = "VIP";
                        valorBase = 25000;
                        validar = true;
                        break;
                    case 2:
                        ubicacion = "Platea";
                        valorBase = 18000;
                        validar = true;
                        break;
                    case 3:
                        ubicacion = "Balcón";
                        valorBase = 12000;
                        validar = true;
                        break;
                    default:
                        System.out.println("Ingrese ubicación válida");

                }

            } else {
                System.out.println("Entrada no valida, porfavor ingrese un número");
                scanner.nextLine();
            }

        }

        //Logica para dctos
        String tipoDescuento = "";
        boolean descuentoValido = false;

        while (!descuentoValido) {

            System.out.println("Es Estudiante (e) - Tercera edad (t) - ninguno (n)");
            tipoDescuento = scanner.nextLine().toLowerCase();

            if (tipoDescuento.equals("e")) {
                descuento = 0.10;
                descuentoValido = true;
            } else if (tipoDescuento.equals("t")) {
                descuento = 0.15;
                descuentoValido = true;
            } else if (tipoDescuento.equals("n")) {
                descuento = 0.0;
                descuentoValido = true;
            } else {
                System.out.println("Porfavor ingrese opcion valida 'e', 't' o 'n' ");
            }

        }
        
        int costoFinal = (int) Math.round(valorBase - (valorBase * descuento));
        ubicacionVendida.add(ubicacion);
        
        descuentosAplicados.add(descuento);
        costosFinales.add(costoFinal);
        precioBase.add(valorBase);
        totalEntradasVendidas++;
        ingresosTotal += costoFinal;
        
        System.out.println("Venta realizada con exito. Costo Final $" + costoFinal);
    }

    //Metodo Resumen de venta
    private static void resumenVenta() {
        System.out.println("\n=== Resumen de ventas ====");

        if (ubicacionVendida.isEmpty()) {
            System.out.println("No se han realizado ventas aún");

        } else {
            System.out.println("Total entradas vendidas: " + totalEntradasVendidas);
            System.out.println("Total ingresos totales: " + ingresosTotal);
             System.out.println("-----------------------------------------------");
            for (int i = 0; i<ubicacionVendida.size(); i++) {
                System.out.println("Venta #" + (i +1));
                System.out.println(" Ubicacion " + ubicacionVendida.get(i));
                System.out.println(" Costo final " + costosFinales.get(i));
                System.out.println(" Descuento aplicado: " + (int)(descuentosAplicados.get(i) * 100) + "%");
                System.out.println("-----------------------------------------------");
            
            }

        }

    }
    
    //Metodo Generaar todas las boleta
    private static void generarTodasLasBoletas() {
        System.out.println("\n--- Boletas ---");
        if (ubicacionVendida.isEmpty()) {
            System.out.println("No se han realizado ventas para generar boletas.");
        } else {
            for (int i = 0; i < ubicacionVendida.size(); i++) {
                generarBoleta(i);
                System.out.println("=========================================");
            }
        }
    }

    //Metodo Generar las boleta
    private static void generarBoleta(int indice) {
        System.out.println("\n--- BOLETA DETALLADA ---");
        System.out.println("Ubicación: " + ubicacionVendida.get(indice));
        System.out.println("Costo Base: $" + precioBase.get(indice));
        System.out.println("Descuento: " + (int) (descuentosAplicados.get(indice) * 100) + "%");
        System.out.println("Costo Final: $" + costosFinales.get(indice));
        System.out.println("¡Gracias por su compra! Esperamos que disfrute del evento.");
    }

}
