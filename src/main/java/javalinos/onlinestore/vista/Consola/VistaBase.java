package javalinos.onlinestore.vista.Consola;

import javalinos.onlinestore.vista.Interfaces.IVistaBase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static javalinos.onlinestore.utils.Utilidades.listToStr;
/**
 * Clase base abstracta para las vistas de consola del sistema.
 * - Proporciona utilidades comunes para interacción con el usuario.
 * - Métodos para mostrar menús, mensajes, solicitar datos y validar entrada.
 * - Utiliza genéricos para mostrar listas de cualquier tipo.
 */
public abstract class VistaBase implements IVistaBase {

    private String cabecera;
    private List<String> listaMenu;

    /**
     * Constructor principal de la vista.
     * @param cabecera texto de cabecera a mostrar.
     * @param listaMenu opciones del menú.
     */
    public VistaBase(String cabecera, List<String> listaMenu) {
        this.cabecera = cabecera;
        this.listaMenu = listaMenu;
    }

    /**
     * Constructor vacío (por defecto).
     */
    public VistaBase() {
        cabecera = "";
        listaMenu = null;
    }

    /**
     * Establece la cabecera que se mostrará.
     * @param cabecera texto de cabecera.
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
     * Muestra el menú de opciones con un tipo de retorno.
     * @param retorno tipo de opción final (0 = nada, 1 = Salir, 2 = Volver, 3 = Cancelar).
     */
    public void showMenu (int retorno){
        showOptions(listaMenu,retorno, false, true, false);
    }

    /**
     * Establece la lista de opciones del menú.
     * @param listaMenu lista de opciones del menú.
     */
    public void setListaMenu(List<String> listaMenu) {
        this.listaMenu = listaMenu;
    }

    /**
     * Solicita un número entero dentro de un rango.
     * @param mensaje mensaje mostrado al usuario.
     * @param min valor mínimo aceptado.
     * @param max valor máximo aceptado.
     * @param reintentar true para mostrar mensajes de error y permitir reintentos.
     * @param sinFin true para no limitar intentos.
     * @return número entero ingresado o -99999 si se superan los intentos.
     */
    public int askInt(String mensaje, int min, int max, boolean reintentar, boolean sinFin, boolean error) {
        Scanner scanner = new Scanner(System.in);
        int integer, intentos = 0;
        if(!reintentar) intentos = 2;
        while(intentos < 3) {
            try {
                showMensaje(mensaje + " (entre " + min + " y " + max + "): ", true);
                integer = scanner.nextInt();
                if (integer >= min && integer <= max) return integer;
                else if (error) showMensajePausa("Error. Entrada fuera de rango. Introduce un número del " + min + " al " + max + ". " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
            catch (InputMismatchException e) {
                if (error) showMensajePausa("Error. Entrada inválida. Introduce un número del " + min + " al " + max + ". " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
                scanner.next();
            }
        }
        if (reintentar && error) showMensajePausa("Error. Has sobrepasado el número de intentos. Volviendo...",true);
        return -99999;
    }

    /**
     * Solicita un número decimal dentro de un rango.
     * @param mensaje mensaje mostrado al usuario.
     * @param min valor mínimo aceptado.
     * @param max valor máximo aceptado.
     * @param reintentar true para mostrar mensajes de error y permitir reintentos.
     * @param sinFin true para no limitar intentos.
     * @return número float ingresado o -99999f si se superan los intentos.
     */
    public Float askFloat(String mensaje, float min, float max, boolean reintentar, boolean sinFin) {
        Scanner scanner = new Scanner(System.in);
        float _float;
        int intentos = 0;
        if(!reintentar) intentos = 2;
        while(intentos < 3) {
            try {
                showMensaje(mensaje + " (entre " + min + " y " + max + "): ", true);
                _float = scanner.nextFloat();
                if (_float >= min && _float <= max) return _float;
                else showMensajePausa("Error. Entrada fuera de rango. Introduce un número del " + min + " al " + max + ". " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
            catch (InputMismatchException e) {
                showMensajePausa("Error. Entrada inválida. Introduce un número del " + min + " al " + max + ". " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                if (!sinFin) intentos++;
                scanner.next();
            }
        }
        if (reintentar) showMensajePausa("Error. Has sobrepasado el número de intentos. Volviendo...",true);
        return -99999f;
    }

    /**
     * Solicita una cadena de texto con longitud máxima.
     * @param mensaje mensaje mostrado al usuario.
     * @return texto ingresado o null si se superan los intentos.
     */
    public String askString(String mensaje,int longitudMin, int longitudMax, boolean reintentar, boolean sinFin, boolean error) {
        int intentos = 0;
        if(!reintentar) intentos = 2;
        Scanner scanner = new Scanner(System.in);
        while(intentos < 3) {
            showMensaje(mensaje,true);
            try {
                String entrada = scanner.nextLine();
                if (entrada.length() <= longitudMax && entrada.length() >= longitudMin) return entrada;
                else if (entrada.length() > longitudMax) {
                    if (error) showMensajePausa("Error. Entrada excedida. No puedes sobrepasar los " + longitudMax + " carácteres. " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                    if (!sinFin) intentos++;
                }
                else {
                    if (error) showMensajePausa("Error. Entrada insuficiente. La longitud de la entrada no puede ser inferior a " + longitudMin + " caracteres. " + (reintentar ? "Vuelve a intentarlo." : "Volviendo..."), true);
                    if (!sinFin) intentos++;
                }
            }
            catch (Exception e) {
                if (error) showMensajePausa("Error. Entrada inválida. " + (reintentar ? "Introduce una cadena de texto." : "Volviendo..."), true);
                if (!sinFin) intentos++;
            }
        }
        if (reintentar && error) showMensajePausa("Error. Demasiados intentos fallidos. Volviendo...", true);
        return null;
    }

    public String askStringListado(List<String> lista, String mensaje, int longitudMin, int longitudMax, boolean reintentar, boolean sinFin, boolean error) {
        return null;
    }

    /**
     * Solicita al usuario una opción tipo sí/no.
     * @param mensaje mensaje mostrado al usuario.
     * @param reintentar true para permitir reintentos.
     * @param maxIntentos true para limitar a 3 intentos.
     * @return true si elige "sí", false si elige "no".
     */
    public Boolean askBoolean(String mensaje, boolean reintentar, boolean maxIntentos) {
        showMensaje(mensaje,true);
        showMensaje("1. Sí", true);
        showMensaje("0. No", true);
        int respuesta = askInt("Introduce una opción", 0, 1, reintentar, maxIntentos, true);
        return respuesta != 0;
    }

    /**
     * Muestra un mensaje.
     * @param mensaje texto a mostrar.
     * @param salto true para salto de línea, false para línea continua.
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
     * Muestra una lista de opciones.
     * @param lista lista de texto.
     * @param tipoRetorno tipo de salida (0-3).
     * @param encuadre true para mostrar separadores.
     * @param numeracion true para numerar.
     * @param opcion true para mostrar "Opción X".
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
     * Muestra una lista genérica con título.
     * @param lista lista a mostrar.
     * @param titulo título a mostrar.
     * @param <T> tipo genérico.
     * @param encuadre si se muestra encuadre.
     * @param numeracion si se numera.
     */
    public <T> void showListGenerica(List<T> lista, String titulo, boolean encuadre, boolean numeracion, boolean opcion) {
        showMensaje("******** " + titulo + " ********", true);
        showOptions(listToStr(lista), 0, encuadre, numeracion, opcion);
        showMensaje("********************************************", true);
    }

    /**
     * Solicita una fecha en formato dd-MM-yyyy.
     * @param mensaje mensaje a mostrar.
     * @return fecha introducida o null si falla.
     */
    public LocalDate askFecha(String mensaje) {
        int intentos = 0;
        while (intentos < 3) {
            try {
                return LocalDate.parse(askString("Introduce la fecha "+ mensaje + " (dd-mm-aaaa): ", 10, 10, false,false, false), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (Exception e) {
                showMensajePausa("Error. Fecha incorrecta. Introduce la fecha en el formato dd-mm-aaaa.", true);
                intentos++;
            }
        }
        showMensajePausa("Error. Demasiados intentos fallidos. Volviendo...", true);
        return null;
    }

    /**
     * Muestra un mensaje y pausa hasta que el usuario presione ENTER.
     * @param mensaje texto a mostrar.
     * @param salto si se debe usar salto de línea.
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
     * Solicita una cadena de texto opcional (ENTER para mantener valor actual).
     * @param mensaje mensaje mostrado al usuario.
     * @param maxLongitud longitud máxima aceptada.
     * @return nueva cadena o null si se mantiene actual.
     */
    public String askStringOpcional(String mensaje, int minLongitud, int maxLongitud) {
        Scanner scanner = new Scanner(System.in);
        int intentos = 0;

        while (intentos < 3) {
            showMensaje(mensaje + " (ENTER para mantener actual): ", false);
            try {
                String entrada = scanner.nextLine();
                if (entrada.isEmpty()) return null;
                if (entrada.length() > maxLongitud || entrada.length() < minLongitud) {
                    showMensajePausa("Error. Mínimo " + minLongitud + ", máximo " + maxLongitud + " caracteres.", true);
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
     * Solicita un número decimal opcional (ENTER para mantener valor actual).
     * @param mensaje mensaje mostrado al usuario.
     * @param min valor mínimo.
     * @param max valor máximo.
     * @return valor introducido o null.
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
     * Solicita un precio decimal opcional.
     * @param mensaje mensaje mostrado al usuario.
     * @param min valor mínimo.
     * @param max valor máximo.
     * @return valor introducido o null.
     */
    public Float askPrecioOpcional(String mensaje, float min, float max) {
        return askFloatOpcional(mensaje, min, max);
    }

    /**
     * Solicita un número entero opcional (ENTER para mantener actual).
     * @param mensaje mensaje mostrado al usuario.
     * @param min valor mínimo permitido.
     * @param max valor máximo permitido.
     * @return valor entero o null si se mantiene actual.
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
