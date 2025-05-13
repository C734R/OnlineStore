package javalinos.onlinestore.vista.JavaFX;

import javalinos.onlinestore.vista.Interfaces.IVistaBase;

import java.time.LocalDate;
import java.util.List;

public class VistaBaseJavaFX implements IVistaBase {
    @Override
    public void setCabecera(String cabecera) {

    }

    @Override
    public void showCabecera() {

    }

    @Override
    public void showMenu(int retorno) {

    }

    @Override
    public void setListaMenu(List<String> listaMenu) {

    }

    @Override
    public int askInt(String mensaje, int min, int max, boolean reintentar, boolean sinFin, boolean error) {
        return 0;
    }

    @Override
    public Float askFloat(String mensaje, float min, float max, boolean reintentar, boolean sinFin) {
        return 0f;
    }

    @Override
    public String askString(String mensaje, int longitudMin, int longitudMax, boolean reintentar, boolean sinFin, boolean error) {
        return "";
    }

    @Override
    public Boolean askBoolean(String mensaje, boolean reintentar, boolean maxIntentos) {
        return null;
    }

    @Override
    public void showMensaje(String mensaje, boolean salto) {

    }

    @Override
    public void showOptions(List<String> lista, int tipoRetorno, Boolean encuadre, Boolean numeracion, Boolean opcion) {

    }

    @Override
    public <T> void showListGenerica(List<T> lista, String titulo, boolean encuadre, boolean numeracion) {

    }

    @Override
    public LocalDate askFecha(String mensaje) {
        return null;
    }

    @Override
    public void showMensajePausa(String mensaje, boolean salto) {

    }

    @Override
    public String askStringOpcional(String mensaje, int maxLongitud) {
        return "";
    }

    @Override
    public Float askFloatOpcional(String mensaje, float min, float max) {
        return 0f;
    }

    @Override
    public Float askPrecioOpcional(String mensaje, float min, float max) {
        return 0f;
    }

    @Override
    public Integer askIntOpcional(String mensaje, int min, int max) {
        return 0;
    }
}
