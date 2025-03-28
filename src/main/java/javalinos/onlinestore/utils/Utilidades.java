package javalinos.onlinestore.utils;

import java.util.ArrayList;
import java.util.List;

public class Utilidades {


    /**
     * Comprueba si un NIF es válido.
     * @param nif recibe el NIF a comprobar.
     * @return boolean devuelve true si el NIF es válido, false en caso contrario.
     */
    public static boolean checkNIF(String nif) {
        String regex = "^([0-9]{8}[A-Z])|([XYZ][0-9]{7}[A-Z])$";
        return nif.matches(regex);
    }

    /**
     * Convierte una lista de objetos genéricos en una lista de cadenas.
     * @param lista recibe la lista de objetos genéricos.
     * @param <T> recibe el tipo de objeto genérico.
     * @return List<String> devuelve la lista de cadenas.
     */
    public static <T> List<String> listToStr(List<T> lista) {
        List<String> listaString = new ArrayList<>();
        for (T t : lista) {
            listaString.add(t.toString());
        }
        return listaString;
    }

    /**
     * Comprueba la validez de un correo electrónico recibido como parámetro"
     * @param email recibe el correo electrónico a comprobar.
     * @return devuelve el resultado de la validación del correo electrónico.
     */
    public static boolean checkEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

}
