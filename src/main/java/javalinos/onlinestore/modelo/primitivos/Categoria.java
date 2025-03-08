package javalinos.onlinestore.modelo.primitivos;

public enum Categoria {

    ESTANDAR("Estándar", 0.0f, 0.0f), PREMIUM("Premium", 30.0f, 0.20f);

    private final String nombre;
    private final Float cuota;
    private final Float descuento;

    Categoria(String nombre, Float cuota, Float descuento) {
        this.nombre = nombre;
        this.cuota = cuota;
        this.descuento = descuento;
    }

    public String getNombre() {
        return nombre;
    }

    public Float getCuota() {
        return cuota;
    }

    public Float getDescuento() {
        return descuento;
    }

    @Override
    public String toString() {
        return  "Categoría: " + nombre + "\n" +
                "Cuota: " + cuota + "\n" +
                "Descuento: " + descuento;
    }
}
