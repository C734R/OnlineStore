package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.vista.VistaClientes;

import java.util.List;

public class ControlClientes extends ControlBase {

    private VistaClientes vClientes;
    private ModeloClientes mClientes;

    /**
     * Constructor del controlador de clientes.
     * @param mStore recibe el modelo de la tienda.
     * @param vClientes recibe la vista de clientes.
     */
    public ControlClientes(ModeloStore mStore, VistaClientes vClientes) {
        super(mStore);
        this.vClientes = vClientes;
        mClientes = mStore.getModeloClientes();
    }

    //*************************** Menu gestión clientes ***************************//

    /**
     * Inicia el menú de clientes.
     */
    public void iniciar() {
        int opcion;
        while (true) {
            vClientes.showCabecera();
            vClientes.showMenu(2);
            opcion = vClientes.askInt("Seleccione una opción", 0, 7, false, false);
            switch (opcion) {
                case 1:
                    addCliente();
                    break;
                case 2:
                    removeCliente();
                    break;
                case 3:
                    showCliente();
                    break;
                case 4:
                    modCliente();
                    break;
                case 5:
                    showListClientes();
                    break;
                case 6:
                    showListClientesCategoria();
                    break;
                case 0:
                    vClientes.showMensaje("Volviendo al menú principal...", true);
                    return;
                default:
                    vClientes.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.", true);
            }
        }
    }

    //*************************** CRUD Clientes ***************************//

    /**
     * Añadir un cliente.
     */
    public void addCliente() {
        vClientes.showMensaje("******** Añadir Cliente ********", true);
        String nombre = vClientes.askString("Ingrese el nombre del cliente:", 250);
        if (nombre == null ) return;
        String domicilio = vClientes.askString("Ingrese el domicilio del cliente:", 250);
        if (domicilio == null ) return;
        String nif = vClientes.askString("Ingrese el NIF del cliente:", 15);
        if (nif == null ) return;
        String email = vClientes.askString("Ingrese el email del cliente:", 250);
        if (email == null ) return;
        Categoria categoria = getCategoria(vClientes.askCategoriaCliente());
        if (categoria == null) return;
        Cliente nuevoCliente = mClientes.makeCliente(nombre, domicilio, nif, email, categoria);
        mClientes.addCliente(nuevoCliente);
        vClientes.showMensaje("Cliente registrado con éxito.", true);
    }

    /**
     * Elimina un cliente.
     */
    public void removeCliente() {
        int opcion = vClientes.askMetodoEliminar();
        switch (opcion) {
            case 1:
                removeClienteNif();
                break;
            case 2:
                removeClienteEmail();
                break;
        }
    }
    public void removeClienteNif() {
        String nif = vClientes.askString("Ingrese el NIF del cliente a eliminar:", 9);
        if (nif == null) return;
        Cliente cliente = mClientes.getClienteNif(nif);
        if (cliente == null){
            vClientes.showMensaje("El NIF introducido no corresponde a ningún usuario.", true);
            return;
        }
        mClientes.removeCliente(cliente);
        vClientes.showMensaje("Cliente eliminado correctamente.", true);
    }
    public void removeClienteEmail() {
        String email = vClientes.askString("Ingrese el Email del cliente a eliminar:", 9);
        if (email == null) return;
        Cliente cliente = mClientes.getClienteEmail(email);
        if (cliente == null) {
            vClientes.showMensaje("El Email introducido no corresponde a ningún usuario.", true);
            return;
        }
        mClientes.removeCliente(cliente);
        vClientes.showMensaje("Cliente eliminado correctamente.", true);
    }

    //*************************** Modificar datos cliente ***************************//

    /**
     * Muestra menú de modificaciones.
     */
    public void modCliente() {
        int opcion ;
        while (true){
            vClientes.showMensaje("******** Menú de Modificación de Clientes ********", true);
            vClientes.showMods();
            opcion = vClientes.askInt("Introduce el tipo de modificación que deseas realizar", 0, 5, false, false);
            Cliente cliente = askClienteModificar();
            if(cliente == null) continue;
            switch (opcion) {
                case 1:
                    modClienteNombre();
                    break;
                case 2:
                    modClienteDomicilio();
                    break;
                case 3:
                    modClienteNIF();
                    break;
                case 4:
                    modClienteEmail();
                    break;
                case 5:
                    modClienteCategoria();
                    break;
                case 0:
                    vClientes.showMensajePausa("Volviendo al menú de gestión de clientes...", true);
                    return;
                default:
                    vClientes.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.",true);
            }
        }
    }

    public void modClienteNombre(){
        String oldNameCliente,newNamecliente;
        Cliente clienteOld,clienteNew;
        clienteOld = askClienteModificar();
        if (clienteOld == null) return;
        clienteNew = clienteOld;
        oldNameCliente = clienteOld.getNombre();
        newNamecliente = vClientes.askString("Introduce el nuevo nombre del usuario: ", 50);
        clienteNew.setNombre(newNamecliente);
        setCliente(clienteOld, clienteNew);
        showExitoMod("nombre",oldNameCliente,clienteNew.getNombre());
    }
    public void modClienteDomicilio(){}
    public void modClienteNIF(){}
    public void modClienteEmail(){}
    public void modClienteCategoria(){}

    //*************************** Peticiones especializadas ***************************//

    public Cliente askClienteModificar(){
        String datoCliente = "";
        int intentos = 0;
        Cliente cliente = null;
        while (true) {
            showListClientes();
            datoCliente = vClientes.askString("Introduce el NIF o el Email del cliente a modificar", 250);
            if (mClientes.getClienteNif(datoCliente) == null) mClientes.getClienteEmail(datoCliente);
            else cliente = mClientes.getClienteNif(datoCliente);
            intentos++;
            if (cliente == null) {
                vClientes.showMensajePausa("Error. El socio indicado no existe. Vuelve a intentarlo.", true);
                break;
            }
            vClientes.showMensaje("******** Datos del cliente a modificar ********", true);
            vClientes.showMensaje(cliente.toString(), true);
            vClientes.showMensaje("***********************************************", true);
            vClientes.showMensajePausa("", true);
            if (intentos > 2) {
                vClientes.showMensajePausa("Error. Intentos máximos superados. Volviendo al programa principal...", true);
                return null;
            }
        }
        return cliente;
    }

    //*************************** Obtener datos singulares ***************************//

    public int getIndexCliente(Boolean last) {
        if (last) return mClientes.getLastIndexCliente();
        else return mClientes.getFirstIndexCliente();
    }

    public void setCliente (Cliente clienteOld, Cliente clienteNew){
        mClientes.setCliente(clienteOld, clienteNew);
    }

    /**
     * Obtiene una categoría en función de la opción seleccionada.
     * @param opcion se le pasa la opción seleccionada.
     * @return Categoria devuelve la categoría correspondiente a la opción seleccionada.
     */
    public Categoria getCategoria (int opcion){
        return mClientes.getCategoria(opcion);
    }

    //*************************** Registrar datos singulares ***************************//

    public Cliente getCliente (int indexCliente) {
        return mClientes.getClienteIndex(-1);
    }

    //*************************** Mostrar mensajes vista ***************************//

    public <VO,VN> void showExitoMod (String mensaje, VO valorOld, VN valorNew ){
        vClientes.showMensaje("El " + mensaje + " del cliente se ha cambiado de forma exitosa de " + valorOld + " a " + valorNew + ".",true);
        vClientes.showMensajePausa("",true);
    }

    public void showCliente() {
        String nif = vClientes.askString("Ingrese el NIF del cliente:", 9);
        Cliente cliente = mClientes.getClienteNif(nif);
        if (cliente != null) {
            vClientes.showMensaje(cliente.toString(), true);
        } else {
            vClientes.showMensaje("Cliente no encontrado.", true);
        }
    }

    public void showListClientes() {
        vClientes.showListClientes(listClientes());
    }

    public void showListClientesCategoria(int categoria) {
        vClientes.showListClientesCategoria(listClientesCategoria(categoria));
    }

    //*************************** Obtener listas ***************************//

    public List<Cliente> listClientes() {
        return mClientes.getClientes();
    }

    public List<Cliente> listClientesCategoria(int categoria) {
        return mClientes.getClientesCategoria(categoria);
    }

    //*************************** Carga de datos ***************************//

    public boolean loadClientes(int configuracion) {
        if (configuracion == 0) {
            return mClientes.loadClientes(configuracion);
        }
        else {
            return false;
        }
    }
}
