package javalinos.onlinestore.controlador;
import javalinos.onlinestore.controlador.ControlBase;

import javalinos.onlinestore.modelo.gestores.ModeloStore;

public abstract class ControlBase {
    private ModeloStore mStore;; // Almacena el modelo principal

    public ControlBase(ModeloStore mStore) {this.mStore = mStore;
    }

    public ControlBase() {this.mStore = new ModeloStore(); // Si quieres inicializarlo por defecto
    }

    public ModeloStore getModeloStore() { return mStore;
    }

    public void setModeloStore(ModeloStore modeloStore) {this.mStore = modeloStore;
    }
}