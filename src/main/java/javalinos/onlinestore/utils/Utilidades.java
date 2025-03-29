package javalinos.onlinestore.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase de utilidades estáticas para validación y conversión.
 */
public class Utilidades {


    /**
     * Comprueba si un NIF es válido.
     * @param nif NIF a comprobar.
     * @return true si el NIF es válido, false en caso contrario.
     */
    public static boolean checkNIF(String nif) {
        String regex = "^([0-9]{8}[A-Z])|([XYZ][0-9]{7}[A-Z])$";
        return nif.matches(regex);
    }

    /**
     * Convierte una lista de objetos genéricos en una lista de cadenas.
     * @param lista lista de objetos genéricos.
     * @param <T> tipo de los objetos.
     * @return lista de strings convertida desde los objetos.
     */
    public static <T> List<String> listToStr(List<T> lista) {
        List<String> listaString = new ArrayList<>();
        for (T t : lista) {
            listaString.add(t.toString());
        }
        return listaString;
    }

    /**
     * Comprueba si un correo electrónico es válido.
     * @param email correo electrónico a validar.
     * @return true si es válido, false en caso contrario.
     */
    public static boolean checkEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

}
