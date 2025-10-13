package eft_s9_roberto_sandoval_prueba;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EFT_S9_roberto_sandoval_prueba {

    //Validación de la edad
    private static final int EDAD_MINIMA_REQUERIDA = 5;
    private static final int EDAD_TOPE = 120;

    //Precios base por ubicación
    private static final int PRECIO_VIP = 30000;
    private static final int PRECIO_PALCO = 25000;
    private static final int PRECIO_PLATEA_BAJA = 20000;
    private static final int PRECIO_PLATEA_ALTA = 15000;
    private static final int PRECIO_GALERIA = 10000;
    
    //Descuentos por tipo de usuario
    private static final double DSCTO_NINO = 0.05;
    private static final double DSCTO_MUJER = 0.07;
    private static final double DSCTO_ESTUDIANTE = 0.25;
    private static final double DSCTO_TERCERA_EDAD = 0.30;
    private static final double DSCTO_ADICIONAL = 0.10;
    
    //Estructuras de datos
    private static ArrayList<Integer> ids = new ArrayList<>();
    private static ArrayList<String> nombres = new ArrayList<>();
    private static ArrayList<Integer> edades = new ArrayList<>();
    private static ArrayList<Character> generos = new ArrayList<>();
    private static ArrayList<String> ubicaciones = new ArrayList<>();
    private static ArrayList<Integer> preciosBase = new ArrayList<>();
    private static ArrayList<Double> descuentos = new ArrayList<>();
    private static ArrayList<Integer> preciosFinales = new ArrayList<>();
    private static ArrayList<Boolean> esEstudiante = new ArrayList<>();
    
    private static int contadorId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 5) {
            mostrarMenu();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        registrarVenta(scanner);
                        break;
                    case 2:
                        mostrarVentas();
                        break;
                    case 3:
                        actualizarVenta(scanner);
                        break;
                    case 4:
                        eliminarVenta(scanner);
                        break;
                    case 5:
                        System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opcion no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error de entrada. Ingrese un número válido para la opción.");
                scanner.nextLine();
                opcion = 0;
            }
            System.out.println("==================================================");
        }
        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n--- Sistema de Venta de Entradas Teatro Moro ---");
        System.out.println("1. Registrar nueva venta");
        System.out.println("2. Ver listado de ventas y totales");
        System.out.println("3. Actualizar venta");
        System.out.println("4. Cancelar venta");
        System.out.println("5. Salir del sistema");
        System.out.print("Seleccione una opción: ");
    }


    public static int obtenerPrecioBasePorUbicacion(String ubicacion) {
        
        switch (ubicacion) {
            case "VIP":
                return PRECIO_VIP;
            case "PALCO":
                return PRECIO_PALCO;
            case "PLATEA BAJA":
                return PRECIO_PLATEA_BAJA;
            case "PLATEA ALTA":
                return PRECIO_PLATEA_ALTA;
            case "GALERIA":
            default:
                return PRECIO_GALERIA;
        }
    }
    //Metodo para determinar el % total de dcto
    public static double calcularDescuento(int edad, char genero, boolean esEstudiante) {
        double descuento = 0.0;
        
        if (edad <= 12) {
            descuento += DSCTO_NINO;
        }
        if (genero == 'F') {
            descuento += DSCTO_MUJER;
        }
        if (esEstudiante) {
            descuento += DSCTO_ESTUDIANTE;
        }
        if (edad >= 60) {
            descuento += DSCTO_TERCERA_EDAD;
        }

        if (descuento > 0.30 && !esEstudiante) {
            descuento += DSCTO_ADICIONAL;
        }
        
        return Math.min(descuento, 1.0);
    }
    
    //=== Metodo para CRUD ===
    
    //Metodo para crear
    public static void registrarVenta(Scanner scanner) {
        System.out.println("\n--- REGISTRAR NUEVA VENTA ---");
        
        String nombre = "";
        int edad = 0;
        char genero = ' ';
        boolean esEstudianteInput = false;
        String ubicacion = "";
        boolean ubicacionValida = false;
        
        try {
            System.out.print("Ingrese nombre del cliente: ");
            nombre = scanner.nextLine().trim();
            
            if (nombre.isEmpty()) {
                System.out.println("ERROR: El nombre del cliente no puede estar vacío o solo contener espacios.");
                return;
            }

            System.out.print("Ingrese edad del cliente (Mínimo " + EDAD_MINIMA_REQUERIDA + " años, Máximo " + EDAD_TOPE + " años): ");
            edad = scanner.nextInt();
            scanner.nextLine();
            
            //Edad mínima requerida
            if (edad < EDAD_MINIMA_REQUERIDA) {
                System.out.println("Venta rechazada. La edad mínima para comprar es " + EDAD_MINIMA_REQUERIDA + " años.");
                return;
            }
            
            //Edad tope
            if (edad > EDAD_TOPE) {
                System.out.println("ERROR: La edad ingresada (" + edad + " años) supera la edad tope permitida: " + EDAD_TOPE + ".");
                return;
            }

           
            boolean generoValido = false;
            do {
                System.out.print("Ingrese género (F para Femenino, M para Masculino): ");
                String inputGenero = scanner.nextLine().toUpperCase();
                if (inputGenero.length() > 0 && (inputGenero.charAt(0) == 'F' || inputGenero.charAt(0) == 'M')) {
                    genero = inputGenero.charAt(0);
                    generoValido = true;
                } else {
                    System.out.println("Género no válido. Ingrese 'F' o 'M'.");
                }
            } while (!generoValido);
            
            System.out.print("¿Es estudiante? (S/N): ");
            esEstudianteInput = scanner.nextLine().toUpperCase().equals("S");

            String[] ubicacionesValidas = {"VIP", "PALCO", "PLATEA BAJA", "PLATEA ALTA", "GALERIA"};
            do {
                System.out.print("Ingrese ubicación (VIP, Palco, Platea Baja, Platea Alta, Galería): ");
                ubicacion = scanner.nextLine().toUpperCase();
                
                for (String u : ubicacionesValidas) {
                    if (u.equals(ubicacion)) {
                        ubicacionValida = true;
                        break;
                    }
                }
                if (!ubicacionValida) {
                    System.out.println("Ubicación no válida. Intente de nuevo.");
                }
            } while (!ubicacionValida);
            
        } catch (InputMismatchException e) {
            System.out.println("ERROR: Debe ingresar un número para la edad.");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            return;
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("ERROR: Entrada de género inválida o vacía.");
            return;
        }

        //Calculo
        int precioBase = obtenerPrecioBasePorUbicacion(ubicacion);
        double descuento = calcularDescuento(edad, genero, esEstudianteInput);
        
        double dsctoSinAdicional = (edad <= 12 ? DSCTO_NINO : 0.0) +
                                   (genero == 'F' ? DSCTO_MUJER : 0.0) +
                                   (edad >= 60 ? DSCTO_TERCERA_EDAD : 0.0);

        if (descuento > dsctoSinAdicional && !esEstudianteInput) {
             System.out.println("Descuento Adicional aplicado! (10%)");
        }
        
        int precioFinal = (int) Math.round(precioBase * (1 - descuento));
        
        //Almacenar
        ids.add(contadorId++);
        nombres.add(nombre);
        edades.add(edad);
        generos.add(genero);
        ubicaciones.add(ubicacion);
        preciosBase.add(precioBase);
        descuentos.add(descuento);
        preciosFinales.add(precioFinal);
        esEstudiante.add(esEstudianteInput);
        
        System.out.println("\nVenta registrada con éxito.");
        imprimirBoleta(ids.size() - 1);
    }
    
    //Metodo para leer
    public static void mostrarVentas() {
        System.out.println("\n--- LISTADO DE VENTAS REGISTRADAS ---");
        if (ids.isEmpty()) {
            System.out.println("No hay ventas registradas aún.");
            return;
        }

        long totalVentas = 0;
        long totalDescuentos = 0;

        for (int i = 0; i < ids.size(); i++) {
            imprimirBoleta(i);
            totalVentas += preciosFinales.get(i);
            totalDescuentos += preciosBase.get(i) - preciosFinales.get(i);
        }
        
        System.out.println("--------------------------------------------------");
        System.out.println("TOTAL de Ventas (Precio Final): $" + totalVentas);
        System.out.println("Monto total de Descuentos aplicados: $" + totalDescuentos);
        System.out.println("--------------------------------------------------");
    }
    
    //Metodo para actualizar
    public static void actualizarVenta(Scanner scanner) {
        System.out.println("\n--- ACTUALIZAR VENTA ---");
        if (ids.isEmpty()) {
            System.out.println("No hay ventas para actualizar.");
            return;
        }

        try {
            System.out.print("Ingrese el ID de la venta a actualizar: ");
            int idBuscar = scanner.nextInt();
            scanner.nextLine();
            
            if (idBuscar <= 0) {
                System.out.println("ERROR: El ID de la venta debe ser un número positivo.");
                return;
            }

            int indice = -1;
            
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i) == idBuscar) {
                    indice = i;
                    break;
                }
            }
            
            if (indice == -1) {
                System.out.println("Venta con ID " + idBuscar + " no encontrada.");
                return;
            }
            
            System.out.println("Venta encontrada:");
            imprimirBoleta(indice);

            System.out.println("\n¿Qué desea actualizar?");
            System.out.println("1. Edad");
            System.out.println("2. Estado de Estudiante (S/N)");
            System.out.print("Opción: ");
            int opcionActualizar = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcionActualizar) {
                case 1:
                    System.out.print("Nueva edad (Mínimo " + EDAD_MINIMA_REQUERIDA + " años, Máximo " + EDAD_TOPE + " años): ");
                    try {
                        int nuevaEdad = scanner.nextInt();
                        scanner.nextLine();
                        
                        //Validación edad mínima
                        if (nuevaEdad < EDAD_MINIMA_REQUERIDA) {
                            System.out.println("ERROR: La edad mínima para comprar es " + EDAD_MINIMA_REQUERIDA + " años. Venta no actualizada.");
                            return;
                        }
                        
                        //Validación edad máxima
                        if (nuevaEdad > EDAD_TOPE) {
                            System.out.println("ERROR: La nueva edad ingresada (" + nuevaEdad + " años) supera la edad tope permitida: " + EDAD_TOPE + ". Venta no actualizada.");
                            return;
                        }
                        
                        edades.set(indice, nuevaEdad);
                    } catch (InputMismatchException e) {
                        System.out.println("ERROR: La nueva edad debe ser un número entero.");
                        scanner.nextLine();
                        return;
                    }
                    break;
                case 2:
                    System.out.print("Es estudiante ahora? (S/N): ");
                    boolean nuevoEstudiante = scanner.nextLine().toUpperCase().equals("S");
                    esEstudiante.set(indice, nuevoEstudiante);
                    break;
                default:
                    System.out.println("Opción de actualización no válida. Solo 1 o 2.");
                    return;
            }
            
            //Recalcula los descuentos y precio final
            int edadActual = edades.get(indice);
            char generoActual = generos.get(indice);
            boolean esEstudianteActual = esEstudiante.get(indice);
            int precioBaseActual = preciosBase.get(indice);
            
            double nuevoDescuento = calcularDescuento(edadActual, generoActual, esEstudianteActual);
            
            double dsctoSinAdicional = (edadActual <= 12 ? DSCTO_NINO : 0.0) +
                                       (generoActual == 'F' ? DSCTO_MUJER : 0.0) +
                                       (edadActual >= 60 ? DSCTO_TERCERA_EDAD : 0.0);

            if (nuevoDescuento > dsctoSinAdicional && !esEstudianteActual) {
                 System.out.println("Descuento Adicional aplicado (10%)");
            }

            int nuevoPrecioFinal = (int) Math.round(precioBaseActual * (1 - nuevoDescuento));
            
            descuentos.set(indice, nuevoDescuento);
            preciosFinales.set(indice, nuevoPrecioFinal);
            
            System.out.println("Venta ID " + idBuscar + " actualizada correctamente.");
            System.out.println("Nuevos detalles:");
            imprimirBoleta(indice);

        } catch (InputMismatchException e) {
            System.out.println("ERROR: Debe ingresar un número para el ID o la opción.");
            scanner.nextLine();
        }
    }
    
    //Metodo para eliminar
    public static void eliminarVenta(Scanner scanner) {
        System.out.println("\n--- CANCELAR VENTA ---");
        if (ids.isEmpty()) {
            System.out.println("No hay ventas para cancelar.");
            return;
        }

        try {
            System.out.print("Ingrese el ID de la venta a cancelar: ");
            int idBuscar = scanner.nextInt();
            scanner.nextLine();

            if (idBuscar <= 0) {
                System.out.println("ERROR: El ID de la venta debe ser un número positivo.");
                return;
            }
            
            int indice = -1;
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i) == idBuscar) {
                    indice = i;
                    break;
                }
            }

            if (indice != -1) {
                System.out.println("Venta a cancelar:");
                imprimirBoleta(indice);
                //Eliminación de todas las listas
                ids.remove(indice);
                nombres.remove(indice);
                edades.remove(indice);
                generos.remove(indice);
                ubicaciones.remove(indice);
                preciosBase.remove(indice);
                descuentos.remove(indice);
                preciosFinales.remove(indice);
                esEstudiante.remove(indice);
                
                System.out.println("\nVenta ID " + idBuscar + " cancelada con éxito.");
            } else {
                System.out.println("Venta con ID " + idBuscar + " no encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ERROR: Debe ingresar un número entero para el ID.");
            scanner.nextLine();
        }
    }
    
    // Método para imprimir boleta
    
   public static void imprimirBoleta(int indice) {
    System.out.println("\n--- BOLETA DE VENTA ---");
    System.out.println("ID: " + ids.get(indice));
    System.out.println("Cliente: " + nombres.get(indice));
    System.out.println("Edad: " + edades.get(indice));
    System.out.println("Género: " + generos.get(indice));
    System.out.println("Asiento: " + ubicaciones.get(indice));
    System.out.println("Descuento: " + String.format("%.2f%%", descuentos.get(indice) * 100));
    System.out.println("Precio Base: $" + preciosBase.get(indice));
    System.out.println("Precio Final: $" + preciosFinales.get(indice));
    System.out.println("----------------------\n");
}
   
   
}