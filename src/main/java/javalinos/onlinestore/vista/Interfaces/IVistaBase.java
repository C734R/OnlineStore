package javalinos.onlinestore.vista.Interfaces;

import java.time.LocalDate;
import java.util.List;

public interface IVistaBase {

    void setCabecera(String cabecera);
    void showCabecera();
    void showMenu (int retorno);
    void setListaMenu(List<String> listaMenu);
    int askInt(String mensaje, int min, int max, boolean reintentar, boolean sinFin, boolean error);
    Float askFloat(String mensaje, float min, float max, boolean reintentar, boolean sinFin);
    String askString(String mensaje,int longitudMin, int longitudMax, boolean reintentar, boolean sinFin, boolean error);
    String askStringListado(List<String> lista, String mensaje, int longitudMin, int longitudMax, boolean reintentar, boolean sinFin, boolean error);
    Boolean askBoolean(String mensaje, boolean reintentar, boolean maxIntentos);
    void showMensaje(String mensaje, boolean salto);
    void showOptions(List<String> lista, int tipoRetorno, Boolean encuadre, Boolean numeracion, Boolean opcion);
    <T> void showListGenerica(List<T> lista, String titulo, boolean encuadre, boolean numeracion) ;
    LocalDate askFecha(String mensaje);
    void showMensajePausa(String mensaje, boolean salto);

    String askStringOpcional(String mensaje, int minLongitud, int maxLongitud);
    Float askFloatOpcional(String mensaje, float min, float max);
    Float askPrecioOpcional(String mensaje, float min, float max);
    Integer askIntOpcional(String mensaje, int min, int max);


}
