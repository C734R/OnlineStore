import javalinos.onlinestore.modelo.gestores.ModeloArticulos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModeloArticulosTest {

    ModeloArticulos mArticulos;

//    @BeforeEach
//    public void setUp() {
//        mArticulos = new ModeloArticulos();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        mArticulos = null;
//    }

//    @Test
//    public void cargarArticulo() {
//        mArticulos.loadArticulos(1);
//        assertEquals(9, mArticulos.getArticulos().size());
//    }

    @Test
    public void testAddArticulo() {
        mArticulos = new ModeloArticulos();
        mArticulos.addArticulo(mArticulos.makeArticulo("Prueba.",10f,0.5f, 10));
        assertEquals(1, mArticulos.getArticulos().size());
    }


}
