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

    public VistaBase(String cabecera, List<String> listaMenu) {
        this.cabecera = cabecera;
        this.listaMenu = listaMenu;
    }

    public VistaBase() {
        cabecera = "";
        listaMenu = null;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public void showCabecera(){
        showMensaje(cabecera,true);
    }

    public void showMenu (int retorno){
        showOptions(listaMenu,retorno, false, true);
    }

    public void setListaMenu(List<String> listaMenu) {
        this.listaMenu = listaMenu;
    }

    // MÉTODO ORIGINAL
    public int askInt(String mensaje, int min, int max, boolean reintentar, boolean maxIntentos) {
        Scanner scanner = new Scanner(System.in);
        int integer, intentos = 0;
        if(!reintentar) intentos = 2;
        while(intentos < 3) {
            try {
                showMensaje(mensaje + " (entre " + min + " y " + max + "): ", true);
                integer = scanner.nextInt();
                if (integer >= min && integer <= max) return integer;
                else if(reintentar) showMensajePausa("Error. Entrada fuera de rango. Introduce un número del " + min + " al " + max + ".", true);
                if (maxIntentos) intentos++;
            }
            catch (InputMismatchException e) {
                if(reintentar) showMensajePausa("Error. Entrada inválida. Introduce un número del " + min + " al " + max + ".", true);
                if (maxIntentos) intentos++;
                scanner.next();
            }
        }
        if(reintentar) showMensajePausa("Error. Has sobrepasado el número de intentos. Volviendo...",true);
        return -99999;
    }

    // SOBRE CARGA OPCIONAL
    public int askInt(String mensaje, int min, int max, boolean reintentar) {
        return askInt(mensaje, min, max, reintentar, true);
    }

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

    // CORRECCIÓN AQUÍ: añadido el 5º parámetro
    public Boolean askBoolean(String mensaje, boolean reintentar) {
        int respuesta = askInt(mensaje, 0, 1, reintentar, true);
        return respuesta != 0;
    }

    public void showMensaje(String mensaje, boolean salto){
        if (salto) {
            System.out.println(mensaje);
        }
        else {
            System.out.print(mensaje);
        }
    }

    public void showOptions(List<String> lista, int tipoRetorno, Boolean encuadre, Boolean numeracion){
        int i = 1;
        for(String entrada: lista) {
            if(encuadre) showMensaje("--------------------------------------", true);
            if(numeracion) showMensaje(i + ".", false);
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

    public <T> void showListGenerica(List<T> lista, String titulo, boolean encuadre, boolean numeracion) {
        showMensaje("******** " + titulo + " ********", true);
        showOptions(listToStr(lista), 0, encuadre, numeracion);
        showMensaje("********************************************", true);
    }

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
}
