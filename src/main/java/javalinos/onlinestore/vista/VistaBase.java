package javalinos.onlinestore.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static javalinos.onlinestore.utils.Utilidades.listToStr;

public abstract class VistaBase {

    private String cabecera;
    private List<String> listaMenu;

    /**
     * Constructor de una vista.
     */
    public VistaBase(String cabecera, List<String> listaMenu) {
        this.cabecera = cabecera;
        this.listaMenu = listaMenu;
    }

    /**
     * Constructor de una vista vacío.
     */
    public VistaBase() {
        cabecera = "";
        listaMenu = null;
    }

    /**
     * Establece la cabecera.
     * @param cabecera recibe la cabecera.
     */
    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    /**
     * Muestra la cabecera.
     */
    public void showCabecera(){
        showMensaje(cabecera,true);
    }

    /**
     * Muestra el menú.
     * @param retorno recibe el retorno.
     */
    public void showMenu (int retorno){
        showOptions(listaMenu,retorno, false, true, false);
    }

    /**
     * Establece la lista de menú.
     * @param listaMenu recibe la lista de menú.
     */
    public void setListaMenu(List<String> listaMenu) {
        this.listaMenu = listaMenu;
    }

    /**
     * Pide un entero.
     * @param mensaje recibe el mensaje.
     * @param min recibe el mínimo.
     * @param max recibe el máximo.
     * @param reintentar recibe si se reintentará.
     * @param sumIntentos recibe si hay máximo de intentos.
     * @return int devuelve el entero.
     */
    public int askInt(String mensaje, int min, int max, boolean reintentar, boolean sumIntentos) {
        Scanner scanner = new Scanner(System.in);
        int integer, intentos = 0;
        if(!reintentar) intentos = 2;
        while(intentos < 3) {
            try {
                showMensaje(mensaje + " (entre " + min + " y " + max + "): ", true);
                integer = scanner.nextInt();
                if (integer >= min && integer <= max) return integer;
                else if(reintentar) showMensajePausa("Error. Entrada fuera de rango. Introduce un número del " + min + " al " + max + ".", true);
                if (sumIntentos || !reintentar) intentos++;
            }
            catch (InputMismatchException e) {
                if(reintentar) showMensajePausa("Error. Entrada inválida. Introduce un número del " + min + " al " + max + ".", true);
                if (sumIntentos || !reintentar) intentos++;
                scanner.next();
            }
        }
        if(reintentar) showMensajePausa("Error. Has sobrepasado el número de intentos. Volviendo...",true);
        return -99999;
    }

    /**
     * Pide un flaot
     * @param mensaje recibe el mensaje.
     * @param min recibe el mínimo.
     * @param max recibe el máximo.
     * @param reintentar recibe si se reintentará.
     * @param sumIntentos recibe si hay máximo de intentos.
     * @return float devuelve el float.
     */
    public Float askFloat(String mensaje, float min, float max, boolean reintentar, boolean sumIntentos) {
        Scanner scanner = new Scanner(System.in);
        float _float;
        int intentos = 0;
        if(!reintentar) intentos = 2;
        while(intentos < 3) {
            try {
                showMensaje(mensaje + " (entre " + min + " y " + max + "): ", true);
                _float = scanner.nextFloat();
                if (_float >= min && _float <= max) return _float;
                else if(reintentar) showMensajePausa("Error. Entrada fuera de rango. Introduce un número del " + min + " al " + max + ".", true);
                if (sumIntentos || !reintentar) intentos++;
            }
            catch (InputMismatchException e) {
                if(reintentar) showMensajePausa("Error. Entrada inválida. Introduce un número del " + min + " al " + max + ".", true);
                if (sumIntentos || !reintentar) intentos++;
                scanner.next();
            }
        }
        if(reintentar) showMensajePausa("Error. Has sobrepasado el número de intentos. Volviendo...",true);
        return -99999f;
    }

    /**
     * Pide una cadena.
     * @param mensaje recibe el mensaje.
     * @param longitud recibe la longitud.
     * @return String devuelve la cadena.
     */
    public String askString(String mensaje, int longitud) {
        int intentos = 0;
        Scanner scanner = new Scanner(System.in);
        while(intentos < 3) {
            showMensaje(mensaje,true);
            try {
                String entrada = scanner.nextLine();
                if (entrada.length() > longitud) {
                    showMensajePausa("Error. Entrada excedida. No puedes sobrepasar los " + longitud + " carácteres.", true);
                    intentos++;
                }
                else return entrada;
            }
            catch (Exception e) {
                showMensajePausa("Error. Entrada inválida. Introduce una cadena de texto.", true);
                intentos++;
            }
        }
        showMensajePausa("Error. Demasiados intentos fallidos. Volviendo...", true);
        return null;
    }

    /**
     * Pide una respuesta sí o no y devuelve true o false
     * @param mensaje se le pasa el mensaje a mostrar
     * @param reintentar se indica si se desea que haya reintentos
     * @param maxIntentos se indica si se desea que haya un máximo de intentos
     * @return devuelve el valor de la respuesta.
     */
    public Boolean askBoolean(String mensaje, boolean reintentar, boolean maxIntentos) {
        showMensaje(mensaje,true);
        showMensaje("1. Sí", true);
        showMensaje("0. No", true);
        int respuesta = askInt("Introduce una opción", 0, 1, reintentar, maxIntentos);
        return respuesta != 0;
    }

    /**
     * Muestra un mensaje.
     * @param mensaje recibe el mensaje.
     * @param salto recibe si hay salto.
     */
    public void showMensaje(String mensaje, boolean salto){
        if (salto) {
            System.out.println(mensaje);
        }
        else {
            System.out.print(mensaje);
        }
    }

    /**
     * Muestra un listado con opciones.
     * @param lista recibe la lista.
     * @param tipoRetorno recibe el tipo de retorno.
     * @param encuadre recibe si hay encuadre.
     * @param numeracion recibe si hay numeración.
     */
    public void showOptions(List<String> lista, int tipoRetorno, Boolean encuadre, Boolean numeracion, Boolean opcion){
        int i = 1;
        for(String entrada: lista) {
            if(encuadre) showMensaje("--------------------------------------", true);
            if(opcion) showMensaje("Opción " + i + ": ", true);
            if(numeracion && !opcion) showMensaje(i + ".", false);
            showMensaje(entrada, true);
            if(encuadre) showMensaje("--------------------------------------", true);
            ++i;
        }
        switch (tipoRetorno) {
            case 1:
                showMensaje("0.Salir",true);
                break;
            case 2:
                showMensaje("0.Volver",true);
                break;
            case 3:
                showMensaje("0.Cancelar",true);
                break;
            case 0:
            default:
                break;
        }
    }

    /**
     * Muestra una lista genérica.
     * @param lista recibe la lista.
     * @param titulo recibe el título.
     * @param <T> recibe el tipo genérico.
     * @param encuadre recibe si hay encuadre.
     * @param numeracion recibe si hay numeración.
     */
    public <T> void showListGenerica(List<T> lista, String titulo, boolean encuadre, boolean numeracion) {
        showMensaje("******** " + titulo + " ********", true);
        showOptions(listToStr(lista), 0, encuadre, numeracion, false);
        showMensaje("********************************************", true);
    }

    /**
     * Pide una fecha.
     * @param mensaje recibe el mensaje.
     * @return LocalDate devuelve la fecha.
     */
    public LocalDate askFecha(String mensaje) {
        int intentos = 0;
        while (intentos < 3) {
            try {
                return LocalDate.parse(askString("Introduce la fecha "+ mensaje + " (dd-mm-aaaa): ", 10), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (Exception e) {
                showMensajePausa("Error. Fecha incorrecta. Introduce la fecha en el formato dd-mm-aaaa.", true);
                intentos++;
            }
        }
        showMensajePausa("Error. Demasiados intentos fallidos. Volviendo...", true);
        return null;
    }

    /**
     * Muestra un mensaje de pausa y espera a que se pulse enter.
     * @param mensaje recibe el mensaje.
     * @param salto recibe si hay salto.
     */
    public void showMensajePausa(String mensaje, boolean salto) {
        if(mensaje.isEmpty()) showMensaje("Pulsa enter para continuar.", salto);
        else showMensaje(mensaje + " Pulsa enter para continuar.", salto);
        Scanner scanner = new Scanner(System.in);
        try{
            scanner.nextLine();
        }
        catch(Exception _)
        {}
    }

    /**
     * Pide una cadena opcional (ENTER para mantener valor actual).
     * @param mensaje mensaje mostrado al usuario.
     * @param maxLongitud longitud máxima permitida.
     * @return String nueva entrada o null si se mantiene el valor actual.
     */
    public String askStringOpcional(String mensaje, int maxLongitud) {
        Scanner scanner = new Scanner(System.in);
        int intentos = 0;

        while (intentos < 3) {
            showMensaje(mensaje + " (ENTER para mantener actual): ", false);
            try {
                String entrada = scanner.nextLine();
                if (entrada.isEmpty()) return null;
                if (entrada.length() > maxLongitud) {
                    showMensajePausa("Error. Máximo " + maxLongitud + " caracteres.", true);
                    intentos++;
                } else {
                    return entrada;
                }
            } catch (Exception e) {
                showMensajePausa("Error. Entrada inválida. Intenta de nuevo.", true);
                intentos++;
            }
        }

        showMensajePausa("Demasiados intentos fallidos. Se mantendrá el valor actual.", true);
        return null;
    }

    /**
     * Pide un float opcional (ENTER para mantener valor actual).
     * @param mensaje mensaje mostrado al usuario.
     * @param min mínimo permitido.
     * @param max máximo permitido.
     * @return Float nueva entrada o null si se mantiene el valor actual.
     */
    public Float askFloatOpcional(String mensaje, float min, float max) {
        Scanner scanner = new Scanner(System.in);
        int intentos = 0;

        while (intentos < 3) {
            showMensaje(mensaje + " (ENTER para mantener actual): ", false);
            String _float = scanner.nextLine().trim();

            if (_float.isEmpty()) return null;
            try {
                float valor = Float.parseFloat(_float);
                if (valor >= min && valor <= max) return valor;
                else showMensajePausa("Error. Introduce un número entre " + min + " y " + max + ".", true);
            } catch (NumberFormatException e) {
                showMensajePausa("Error. Formato inválido. Introduce un número decimal.", true);
            }

            intentos++;
        }

        showMensajePausa("Demasiados intentos fallidos. Se mantendrá el valor actual.", true);
        return null;
    }

    /**
     * Pide un precio opcional (ENTER para mantener valor actual).
     * @param mensaje mensaje mostrado al usuario.
     * @param min mínimo permitido.
     * @param max máximo permitido.
     * @return Float nueva entrada o null si se mantiene el valor actual.
     */
    public Float askPrecioOpcional(String mensaje, float min, float max) {
        return askFloatOpcional(mensaje, min, max);
    }

    /**
     * Pide un entero opcional (ENTER para mantener valor actual).
     * @param mensaje mensaje mostrado al usuario.
     * @param min mínimo permitido.
     * @param max máximo permitido.
     * @return Integer nueva entrada o null si se mantiene el valor actual.
     */
    public Integer askIntOpcional(String mensaje, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int intentos = 0;

        while (intentos < 3) {
            showMensaje(mensaje + " (ENTER para mantener actual): ", false);
            String entero = scanner.nextLine().trim();

            if (entero.isEmpty()) return null;
            try {
                int valor = Integer.parseInt(entero);
                if (valor >= min && valor <= max) return valor;
                else showMensajePausa("Error. Introduce un número entre " + min + " y " + max + ".", true);
            } catch (NumberFormatException e) {
                showMensajePausa("Error. Formato inválido. Introduce un número entero.", true);
            }

            intentos++;
        }

        showMensajePausa("Demasiados intentos fallidos. Se mantendrá el valor actual.", true);
        return null;
    }
}
