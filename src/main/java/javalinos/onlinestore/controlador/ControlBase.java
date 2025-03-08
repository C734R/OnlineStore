package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloStore;

public abstract class ControlBase {

    private ModeloStore mStore;

    public ControlBase(ModeloStore mStore) {
        this.mStore = mStore;
    }

    public ControlBase() {
        this.mStore = null;
    }

    public ModeloStore getmStore() {
        return mStore;
    }

    public void setmStore(ModeloStore mStore) {
        this.mStore = mStore;
    }
}
