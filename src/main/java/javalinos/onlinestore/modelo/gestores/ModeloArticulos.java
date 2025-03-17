package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModeloArticulos {

    private List<String> opciones;
    private List<Articulo> articulos;

    public ModeloArticulos(List<String> opciones, List<Articulo> articulos) {
        this.opciones = opciones;
        this.articulos = articulos;
    }

    public ModeloArticulos() {
        this.opciones = null;
        this.articulos = null;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Articulo makeArticulo (String descripcion, Float precio, Float preparacion) {
        String codigo = "";
        if (articulos == null) articulos = new ArrayList<Articulo>();
        if (articulos.isEmpty()) codigo = "ART000";
        else {
            codigo = articulos.getLast().getCodigo();
            codigo = "ART" + String.format("%03d", Integer.parseInt(codigo.substring(3)) + 1);
        }
        return new Articulo(codigo, descripcion, precio, preparacion);
    }

    public void addArticulo(Articulo articulo) {
        this.articulos.add(articulo);
    }

    public void removeArticulo(Articulo articulo) {
        this.articulos.remove(articulo);
    }

    public boolean loadArticulos(int configuracion) {

        if (configuracion == 0) {

            try {
                this.articulos.clear();

                addArticulo(makeArticulo("Guitarra española de juguete.", 6f, 0.05f));
                addArticulo(makeArticulo("Exin Castillos - Set de construcción.", 12.5f, 0.08f));
                addArticulo(makeArticulo("Scalextric - Circuito de coches eléctricos.", 25f, 0.10f));
                addArticulo(makeArticulo("Cinexin - Proyector de cine infantil.", 18f, 0.07f));
                addArticulo(makeArticulo("Telesketch - Pizarra mágica para dibujar.", 10f, 0.06f));
                addArticulo(makeArticulo("Muñeca Nancy - Famosa.", 20f, 0.06f));
                addArticulo(makeArticulo("Madelman - Figura de acción articulada.", 15f, 0.05f));
                addArticulo(makeArticulo("Operación - Juego de mesa de precisión.", 8.5f, 0.04f));
                addArticulo(makeArticulo("Simon - Juego electrónico de memoria.", 14f, 0.08f));
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean checkArticulo(String codigo) {
        return false;
    }
}
