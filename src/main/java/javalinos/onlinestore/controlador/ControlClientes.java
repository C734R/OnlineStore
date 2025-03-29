package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.gestores.ModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.primitivos.Categoria;
import javalinos.onlinestore.modelo.primitivos.Cliente;
import javalinos.onlinestore.vista.VistaClientes;

import java.util.List;

/**
 * Controlador para gestionar la lógica del módulo de clientes.
 */
public class ControlClientes extends ControlBase {

    private final VistaClientes vClientes;
    private final ModeloClientes mClientes;

    /**
     * Constructor del controlador de clientes.
     * @param mStore modelo principal de la tienda
     * @param vClientes vista asociada a los clientes
     */
    public ControlClientes(ModeloStore mStore, VistaClientes vClientes) {
        super(mStore);
        this.vClientes = vClientes;
        mClientes = mStore.getModeloClientes();
    }

    //*************************** Menu gestión clientes ***************************//

    /**
     * Inicia el menú de gestión de clientes.
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
                    vClientes.showMensajePausa("", true);
                    break;
                case 4:
                    modCliente();
                    break;
                case 5:
                    showListClientes();
                    vClientes.showMensajePausa("", true);
                    break;
                case 6:
                    showListClientesCategoria();
                    vClientes.showMensajePausa("", true);
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
     * Añade un nuevo cliente a la colección.
     */
    public void addCliente() {
        vClientes.showMensaje("******** Añadir Cliente ********", true);
        String nombre = vClientes.askString("Ingrese el nombre del cliente:", 250);
        if (nombre == null ) return;
        String domicilio = vClientes.askString("Ingrese el domicilio del cliente:", 250);
        if (domicilio == null ) return;
        String nif = vClientes.askNIF();
        if (nif == null ) return;
        String email = vClientes.askEmail(false);
        if (email == null ) return;
        Categoria categoria = getCategoria(vClientes.askCategoriaCliente());
        if (categoria == null) return;
        Cliente nuevoCliente = mClientes.makeCliente(nombre, domicilio, nif, email, categoria);
        mClientes.addCliente(nuevoCliente);
        vClientes.showMensajePausa("Cliente registrado con éxito.", true);
    }

    /**
     * Reemplaza los datos de un cliente antiguo por uno nuevo.
     * @param clienteOld cliente original
     * @param clienteNew cliente actualizado
     */
    public void setCliente (Cliente clienteOld, Cliente clienteNew){
        mClientes.updateCliente(clienteOld, clienteNew);
    }

    /**
     * Inicia el proceso de eliminación de un cliente según el modo elegido.
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
     * Elimina un cliente introduciendo su NIF.
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
     * Elimina un cliente introduciendo su email.
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
     * Inicia el menú de modificación de datos del cliente.
     */
    public void modCliente() {
        int opcion ;
        while (true){
            Cliente cliente = null;
            vClientes.showMensaje("******** Menú de Modificación de Clientes ********", true);
            vClientes.showMods();
            opcion = vClientes.askInt("Introduce el tipo de modificación que deseas realizar", 0, 5, false, false);
            if(opcion != 0) cliente = askClienteModificar();
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
     * Modifica el nombre del cliente.
     * @param clienteOld cliente a modificar
     */
    public void modClienteNombre(Cliente clienteOld) {
        String oldNameCliente,newNameCliente;
        Cliente clienteNew;
        clienteNew = new Cliente(clienteOld);
        oldNameCliente = clienteOld.getNombre();
        newNameCliente = vClientes.askString("Introduce el nuevo nombre del usuario: ", 250);
        if (newNameCliente == null) return;
        clienteNew.setNombre(newNameCliente);
        setCliente(clienteOld, clienteNew);
        showExitoMod("nombre",oldNameCliente,clienteNew.getNombre());
    }
    /**
     * Modifica el domicilio del cliente.
     * @param clienteOld cliente a modificar
     */
    public void modClienteDomicilio(Cliente clienteOld){
        String oldDomicilio,newDomicilio;
        Cliente clienteNew;
        clienteNew = new Cliente(clienteOld);
        oldDomicilio = clienteOld.getDomicilio();
        newDomicilio = vClientes.askString("Introduce el nuevo domicilio: ", 250);
        if(newDomicilio == null) return;
        clienteNew.setDomicilio(newDomicilio);
        setCliente(clienteOld, clienteNew);
        showExitoMod("domicilio",oldDomicilio,clienteNew.getDomicilio());
    }
    /**
     * Modifica el NIF del cliente.
     * @param clienteOld cliente a modificar
     */
    public void modClienteNIF(Cliente clienteOld){
        String oldNIF,newNIF;
        Cliente clienteNew;
        clienteNew = clienteOld;
        oldNIF = clienteOld.getNif();
        newNIF = vClientes.askNIF();
        if(newNIF == null) return;
        clienteNew.setNif(newNIF);
        setCliente(clienteOld, clienteNew);
        showExitoMod("NIF",oldNIF,clienteNew.getNif());
    }
    /**
     * Modifica el email del cliente.
     * @param clienteOld cliente a modificar
     */
    public void modClienteEmail(Cliente clienteOld){
        String oldEmail,newEmail;
        Cliente clienteNew;
        clienteNew = new Cliente(clienteOld);
        oldEmail = clienteOld.getEmail();
        newEmail = vClientes.askEmail(true);
        if(newEmail == null) return;
        clienteNew.setEmail(newEmail);
        setCliente(clienteOld, clienteNew);
        showExitoMod("email",oldEmail,clienteNew.getEmail());
    }
    /**
     * Modifica la categoría del cliente.
     * @param clienteOld cliente a modificar
     */
    public void modClienteCategoria(Cliente clienteOld){
        Categoria oldCategoria,newCategoria;
        Cliente clienteNew;
        clienteNew = new Cliente(clienteOld);
        oldCategoria = clienteOld.getCategoria();
        newCategoria = mClientes.getCategoria(vClientes.askCategoriaCliente());
        if(newCategoria == null) return;
        clienteNew.setCategoria(newCategoria);
        setCliente(clienteOld, clienteNew);
        showExitoMod("categoria",oldCategoria.getNombre(),clienteNew.getCategoria().getNombre());
    }

    //*************************** Peticiones especializadas ***************************//

    /**
     * Pide al usuario seleccionar un cliente a modificar.
     * @return cliente seleccionado o null si se canceló
     */
    public Cliente askClienteModificar(){
        String datoCliente;
        int intentos = 0;
        Cliente cliente = null;
        while (true) {
            showListClientes();
            datoCliente = vClientes.askString("Introduce el NIF o el Email del cliente a modificar: ", 250);
            if (mClientes.getClienteNif(datoCliente) == null) cliente = mClientes.getClienteEmail(datoCliente);
            else cliente = mClientes.getClienteNif(datoCliente);
            intentos++;
            if (cliente != null) {
                vClientes.showMensaje("******** Datos del cliente a modificar ********", true);
                vClientes.showMensaje(cliente.toString(), true);
                vClientes.showMensaje("***********************************************", true);
                break;
            }
            if (intentos > 2) {
                vClientes.showMensajePausa("Error. Intentos máximos superados. Volviendo al programa principal...", true);
                return null;
            }
            vClientes.showMensajePausa("Error. El cliente indicado no existe. Vuelve a intentarlo.", true);
        }
        return cliente;
    }

    //*************************** Obtener datos singulares ***************************//

    /**
     * Devuelve el índice del primer o último cliente registrado.
     * @param last true para último, false para primero
     * @return índice correspondiente
     */
    public int getIndexCliente(Boolean last) {
        if (last) return mClientes.getLastIndexCliente();
        else return mClientes.getFirstIndexCliente();
    }
    /**
     * Obtiene una categoría según la opción seleccionada.
     * @param opcion número de opción seleccionada
     * @return categoría correspondiente
     */
    public Categoria getCategoria (int opcion){
        return mClientes.getCategoria(opcion);
    }

    /**
     * Obtiene un cliente según su índice en la lista.
     * @param indexCliente índice del cliente
     * @return cliente correspondiente
     */
    public Cliente getCliente(int indexCliente) { return mClientes.getClienteIndex(indexCliente); }

    /**
     * Obtiene un cliente según su email.
     * @param email email del cliente
     * @return cliente correspondiente
     */
    public Cliente getClienteEmail(String email){
        return mClientes.getClienteEmail(email);
    }

    //*************************** Mostrar mensajes vista ***************************//

    /**
     * Muestra un mensaje informativo al modificar correctamente un campo.
     * @param datoModificar nombre del campo
     * @param valorOld valor anterior
     * @param valorNew nuevo valor
     * @param <VO> tipo del valor antiguo
     * @param <VN> tipo del valor nuevo
     */
    public <VO,VN> void showExitoMod (String datoModificar, VO valorOld, VN valorNew ){
        vClientes.showMensaje("El " + datoModificar + " del cliente se ha cambiado de forma exitosa de " + valorOld + " a " + valorNew + ".",true);
        vClientes.showMensajePausa("",true);
    }
    /**
     * Muestra los datos de un cliente introduciendo su NIF o email.
     */
    public void showCliente() {
        Cliente cliente;
        String datoCliente = vClientes.askString("Introduce el NIF o el Email del cliente a mostrar: ", 250);
        if (mClientes.getClienteNif(datoCliente) == null) cliente = mClientes.getClienteEmail(datoCliente);
        else cliente = mClientes.getClienteNif(datoCliente);
        if (cliente == null) {
            vClientes.showMensajePausa("Error. El cliente indicado no existe.", true);
            return;
        }
        vClientes.showCliente(cliente);
    }

    /**
     * Muestra la lista completa de clientes sin filtros.
     */
    public void showListClientes() {
        vClientes.showListClientes(getListaClientes());
    }
    /**
     * Muestra la lista de clientes numerada.
     */
    public void showListClientesNumerada() {
        vClientes.showListClientesNumerada( getListaClientes());
    }

    /**
     * Muestra la lista de clientes filtrada por categoría.
     */
    public void showListClientesCategoria() {
        Categoria categoria = getCategoria(vClientes.askCategoriaCliente());
        vClientes.showListClientesCategoria(getListClientesCategoria(categoria), categoria);
    }

    //*************************** Obtener listas ***************************//

    /**
     * Devuelve la lista de todos los clientes registrados.
     * @return lista de clientes
     */
    public List<Cliente> getListaClientes() {
        return mClientes.getClientes();
    }

    /**
     * Devuelve la lista de clientes que pertenecen a una categoría.
     * @param categoria categoría a filtrar
     * @return lista de clientes
     */
    public List<Cliente> getListClientesCategoria(Categoria categoria) {
        return mClientes.getClientesCategoria(categoria);
    }

    //*************************** Carga de datos ***************************//

    /**
     * Carga la colección de clientes en memoria desde configuración.
     * @param configuracion valor de configuración para carga
     * @return true si la carga fue exitosa, false si no
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
