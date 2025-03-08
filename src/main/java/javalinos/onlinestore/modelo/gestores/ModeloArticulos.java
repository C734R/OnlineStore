package javalinos.onlinestore.modelo.gestores;

import javalinos.onlinestore.modelo.primitivos.Articulo;

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

    public void addArticulo(Articulo articulo) {
        this.articulos.add(articulo);
    }

    public void removeArticulo(Articulo articulo) {
        this.articulos.remove(articulo);
    }

    public void loadArticulos() {

    }

    public boolean checkArticulo(String codigo) {
        return false;
    }
}
