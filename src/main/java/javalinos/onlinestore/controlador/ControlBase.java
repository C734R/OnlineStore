package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;
/**
 * Clase base abstracta para los controladores.
 * Proporciona acceso común al modelo principal de la aplicación.
 */
public abstract class ControlBase {
    private ModeloStore mStore;
    /**
     * Constructor que recibe un modelo ya instanciado.
     * @param mStore modelo principal
     */
    public ControlBase(ModeloStore mStore) {this.mStore = mStore;
    }
    /**
     * Constructor por defecto que inicializa un nuevo modelo.
     */
    public ControlBase() {this.mStore = new ModeloStore();
    }
    /**
     * Devuelve el modelo principal actual.
     * @return modelo principal
     */
    public ModeloStore getModeloStore() { return mStore;
    }
    /**
     * Establece el modelo principal.
     * @param modeloStore nuevo modelo a utilizar
     */
    public void setModeloStore(ModeloStore modeloStore) {this.mStore = modeloStore;
    }
}