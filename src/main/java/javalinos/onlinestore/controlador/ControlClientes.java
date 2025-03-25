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
            opcion = vClientes.askInt("Seleccione una opción", 0, 6, false, false);
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
        vClientes.showMensajePausa("Cliente registrado con éxito.", true);
    }

    /**
     * Sustituye los datos de un cliente por los nuevos.
     * @param clienteOld se le pasan los datos del cliente a modificar.
     * @param clienteNew se le pasan los nuevos datos.
     */
    public void setCliente (Cliente clienteOld, Cliente clienteNew){
        mClientes.updateCliente(clienteOld, clienteNew);
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

    /**
     * Eliminar un cliente por NIF.
     */
    public void removeClienteNif() {
        String nif = vClientes.askString("Ingrese el NIF del cliente a eliminar:", 15);
        if (nif == null) return;
        Cliente cliente = mClientes.getClienteNif(nif);
        if (cliente == null){
            vClientes.showMensajePausa("Error. El NIF introducido no corresponde a ningún usuario.", true);
            return;
        }
        mClientes.removeCliente(cliente);
        vClientes.showMensajePausa("Cliente eliminado correctamente.", true);
    }

    /**
     * Eliminar un cliente por Email.
     */
    public void removeClienteEmail() {
        String email = vClientes.askString("Ingrese el Email del cliente a eliminar:", 250);
        if (email == null) return;
        Cliente cliente = mClientes.getClienteEmail(email);
        if (cliente == null) {
            vClientes.showMensajePausa("Error. El Email introducido no corresponde a ningún usuario.", true);
            return;
        }
        mClientes.removeCliente(cliente);
        vClientes.showMensajePausa("Cliente eliminado correctamente.", true);
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
                    modClienteNombre(cliente);
                    break;
                case 2:
                    modClienteDomicilio(cliente);
                    break;
                case 3:
                    modClienteNIF(cliente);
                    break;
                case 4:
                    modClienteEmail(cliente);
                    break;
                case 5:
                    modClienteCategoria(cliente);
                    break;
                case 0:
                    vClientes.showMensajePausa("Volviendo al menú de gestión de clientes...", true);
                    return;
                default:
                    vClientes.showMensajePausa("Error. La opción introducida no existe. Vuelva a intentarlo.",true);
            }
        }
    }

    /**
     * Modificar nombre de cliente.
     * @param clienteOld se le pasa el cliente a modificar.
     */
    public void modClienteNombre(Cliente clienteOld) {
        String oldNameCliente,newNameCliente;
        Cliente clienteNew;
        clienteNew = clienteOld;
        oldNameCliente = clienteOld.getNombre();
        newNameCliente = vClientes.askString("Introduce el nuevo nombre del usuario: ", 250);
        clienteNew.setNombre(newNameCliente);
        setCliente(clienteOld, clienteNew);
        showExitoMod("nombre",oldNameCliente,clienteNew.getNombre());
    }
    /**
     * Modificar domicilio de cliente.
     * @param clienteOld se le pasa el cliente a modificar.
     */
    public void modClienteDomicilio(Cliente clienteOld){
        String oldDomicilio,newDomicilio;
        Cliente clienteNew;
        clienteNew = clienteOld;
        oldDomicilio = clienteOld.getDomicilio();
        newDomicilio = vClientes.askString("Introduce el nuevo domicilio: ", 250);
        clienteNew.setDomicilio(newDomicilio);
        setCliente(clienteOld, clienteNew);
        showExitoMod("domicilio",oldDomicilio,newDomicilio);
    }
    /**
     * Modificar NIF de cliente.
     * @param clienteOld se le pasa el cliente a modificar.
     */
    public void modClienteNIF(Cliente clienteOld){
        String oldNIF,newNIF;
        Cliente clienteNew;
        clienteNew = clienteOld;
        oldNIF = clienteOld.getNif();
        newNIF = vClientes.askNIF();
        clienteNew.setNif(newNIF);
        setCliente(clienteOld, clienteNew);
        showExitoMod("NIF",oldNIF,newNIF);
    }
    /**
     * Modificar Email de cliente.
     * @param clienteOld se le pasa el cliente a modificar.
     */
    public void modClienteEmail(Cliente clienteOld){
        String oldEmail,newEmail;
        Cliente clienteNew;
        clienteNew = clienteOld;
        oldEmail = clienteOld.getEmail();
        newEmail = vClientes.askEmail();
        setCliente(clienteOld, clienteNew);
        showExitoMod("email",oldEmail,newEmail);
    }
    /**
     * Modificar categoría de cliente.
     * @param clienteOld se le pasa el cliente a modificar.
     */
    public void modClienteCategoria(Cliente clienteOld){
        Categoria oldCategoria,newCategoria;
        Cliente clienteNew;
        clienteNew = clienteOld;
        oldCategoria = clienteOld.getCategoria();
        newCategoria = mClientes.getCategoria(vClientes.askCategoriaCliente());
        setCliente(clienteOld, clienteNew);
        showExitoMod("categoria",oldCategoria,newCategoria);
    }

    //*************************** Peticiones especializadas ***************************//

    /**
     * Preguntar el cliente a modificar y mostrar datos.
     * @return Cliente devolver cliente a modificar.
     */
    public Cliente askClienteModificar(){
        String datoCliente;
        int intentos = 0;
        Cliente cliente = null;
        while (true) {
            showListClientes();
            datoCliente = vClientes.askString("Introduce el NIF o el Email del cliente a modificar", 250);
            if (mClientes.getClienteNif(datoCliente) == null) cliente = mClientes.getClienteEmail(datoCliente);
            else cliente = mClientes.getClienteNif(datoCliente);
            intentos++;
            if (cliente == null) {
                vClientes.showMensajePausa("Error. El cliente indicado no existe. Vuelve a intentarlo.", true);
                break;
            }
            else {
                vClientes.showMensaje("******** Datos del cliente a modificar ********", true);
                vClientes.showMensaje(cliente.toString(), true);
                vClientes.showMensaje("***********************************************", true);
                vClientes.showMensajePausa("", true);
            }
            if (intentos > 2) {
                vClientes.showMensajePausa("Error. Intentos máximos superados. Volviendo al programa principal...", true);
                return null;
            }
        }
        return cliente;
    }

    //*************************** Obtener datos singulares ***************************//

    /**
     * Obtener índice inicial o final de la lista de clientes.
     * @param last se le pasa la opción de inicial o final.
     * @return devuelve el índice del primer o último del cliente.
     */
    public int getIndexCliente(Boolean last) {
        if (last) return mClientes.getLastIndexCliente();
        else return mClientes.getFirstIndexCliente();
    }
    /**
     * Obtiene una categoría en función de la opción seleccionada.
     * @param opcion se le pasa la opción seleccionada.
     * @return Categoria devuelve la categoría correspondiente a la opción seleccionada.
     */
    public Categoria getCategoria (int opcion){
        return mClientes.getCategoria(opcion);
    }

    /**
     * Obtiene un cliente a través de su índice en la colección.
     * @param indexCliente se le pasa el índice del cliente en la colección.
     * @return Cliente devuelve el cliente que corresponde al índice pasado.
     */
    public Cliente getCliente(int indexCliente) { return mClientes.getClienteIndex(indexCliente); }

    //*************************** Mostrar mensajes vista ***************************//

    /**
     * Mostrar éxito en la modificación.
     * @param datoModificar se le pasa el dato a modificar.
     * @param valorOld se le pasa el dato antigüo.
     * @param valorNew se le pasa el dato nuevo.
     * @param <VO> tipo de dato antigüo.
     * @param <VN> tipo de dato nuevo.
     */
    public <VO,VN> void showExitoMod (String datoModificar, VO valorOld, VN valorNew ){
        vClientes.showMensaje("El " + datoModificar + " del cliente se ha cambiado de forma exitosa de " + valorOld + " a " + valorNew + ".",true);
        vClientes.showMensajePausa("",true);
    }
    /**
     * Muestra un cliente a través de su NIF o Email.
     */
    public void showCliente() {
        Cliente cliente;
        String datoCliente = vClientes.askString("Introduce el NIF o el Email del cliente a mostrar", 250);
        if (mClientes.getClienteNif(datoCliente) == null) cliente = mClientes.getClienteEmail(datoCliente);
        else cliente = mClientes.getClienteNif(datoCliente);
        if (cliente == null) {
            vClientes.showMensajePausa("Error. El cliente indicado no existe.", true);
            return;
        }
        vClientes.showCliente(cliente);
        vClientes.showMensajePausa("", true);
    }

    /**
     * Mostrar listado de clientes sin filtrar.
     */
    public void showListClientes() {
        vClientes.showListClientes(getListaClientes());
        vClientes.showMensajePausa("", true);
    }

    /**
     * Mostrar listado de clientes por categoría.
     */
    public void showListClientesCategoria() {
        Categoria categoria = getCategoria(vClientes.askCategoriaCliente());
        vClientes.showListClientesCategoria(getListClientesCategoria(categoria), categoria);
        vClientes.showMensajePausa("", true);
    }

    //*************************** Obtener listas ***************************//

    /**
     * Obtener lista de clientes.
     * @return List<Cliente> devuelve colección clientes.
     */
    public List<Cliente> getListaClientes() {
        return mClientes.getClientes();
    }

    /**
     * Obtener listado de clientes por categoría.
     * @param categoria se le pasa la categoría por la que filtrar los clientes.
     * @return List<Cliente> devuelve colección de clientes filtrada por categoría.
     */
    public List<Cliente> getListClientesCategoria(Categoria categoria) {
        return mClientes.getClientesCategoria(categoria);
    }

    //*************************** Carga de datos ***************************//

    /**
     * Reaplizar precarga de colección de clientes.
     * @param configuracion se le pasa la configuración establecida por argumento.
     * @return boolean devuelve resultado de la operación.
     */
    public boolean loadClientes(int configuracion) {
        if (configuracion == 0) {
            return mClientes.loadClientes(configuracion);
        }
        else {
            return false;
        }
    }
}
