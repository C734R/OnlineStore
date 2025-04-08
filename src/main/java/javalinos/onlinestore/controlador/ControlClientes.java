package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;
import javalinos.onlinestore.modelo.gestores.Local.ModeloClientesLocal;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.vista.VistaClientes;

import java.util.List;

/**
 * Controlador para gestionar la lógica del módulo de clientes.
 */
public class ControlClientes extends ControlBase {

    private final VistaClientes vClientes;
    private final IModeloClientes mClientes;

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
        try {
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
        catch (Exception e) {
            vClientes.showMensajePausa("Error al ejecutar la operación: \n"+ e.getMessage(), true);
        }
    }

    //*************************** CRUD Clientes ***************************//

    /**
     * Añade un nuevo cliente a la colección.
     */
    public void addCliente() {
        vClientes.showMensaje("******** Añadir ClienteDTO ********", true);
        String nombre = vClientes.askString("Ingrese el nombre del cliente:", 250);
        if (nombre == null ) return;
        String domicilio = vClientes.askString("Ingrese el domicilio del cliente:", 250);
        if (domicilio == null ) return;
        String nif = vClientes.askNIF();
        if (nif == null ) return;
        String email = vClientes.askEmail(false);
        if (email == null ) return;
        CategoriaDTO categoriaDTO = getCategoria(vClientes.askCategoriaCliente());
        if (categoriaDTO == null) return;
        ClienteDTO nuevoClienteDTO = mClientes.makeCliente(nombre, domicilio, nif, email, categoriaDTO);
        mClientes.addCliente(nuevoClienteDTO);
        vClientes.showMensajePausa("ClienteDTO registrado con éxito.", true);
    }

    /**
     * Reemplaza los datos de un cliente antiguo por uno nuevo.
     * @param ClienteDTOOld cliente original
     * @param ClienteDTONew cliente actualizado
     */
    public void setCliente (ClienteDTO ClienteDTOOld, ClienteDTO ClienteDTONew){
        mClientes.updateCliente(ClienteDTOOld, ClienteDTONew);
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
        String nif = vClientes.askString("Ingrese el NIF del clienteDTO a eliminar:", 15);
        if (nif == null) return;
        ClienteDTO ClienteDTO = mClientes.getClienteNif(nif);
        if (ClienteDTO == null){
            vClientes.showMensajePausa("Error. El NIF introducido no corresponde a ningún usuario.", true);
            return;
        }
        mClientes.removeCliente(ClienteDTO);
        vClientes.showMensajePausa("ClienteDTO eliminado correctamente.", true);
    }

    /**
     * Elimina un cliente introduciendo su email.
     */
    public void removeClienteEmail() {
        String email = vClientes.askString("Ingrese el Email del clienteDTO a eliminar:", 250);
        if (email == null) return;
        ClienteDTO ClienteDTO = mClientes.getClienteEmail(email);
        if (ClienteDTO == null) {
            vClientes.showMensajePausa("Error. El Email introducido no corresponde a ningún usuario.", true);
            return;
        }
        mClientes.removeCliente(ClienteDTO);
        vClientes.showMensajePausa("ClienteDTO eliminado correctamente.", true);
    }

    //*************************** Modificar datos cliente ***************************//

    /**
     * Inicia el menú de modificación de datos del cliente.
     */
    public void modCliente() throws Exception {
        int opcion ;
        while (true){
            ClienteDTO ClienteDTO = null;
            vClientes.showMensaje("******** Menú de Modificación de Clientes ********", true);
            vClientes.showMods();
            opcion = vClientes.askInt("Introduce el tipo de modificación que deseas realizar", 0, 5, false, false);
            if(opcion != 0) ClienteDTO = askClienteModificar();
            switch (opcion) {
                case 1:
                    modClienteNombre(ClienteDTO);
                    break;
                case 2:
                    modClienteDomicilio(ClienteDTO);
                    break;
                case 3:
                    modClienteNIF(ClienteDTO);
                    break;
                case 4:
                    modClienteEmail(ClienteDTO);
                    break;
                case 5:
                    modClienteCategoria(ClienteDTO);
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
     * @param ClienteDTOOld cliente a modificar
     */
    public void modClienteNombre(ClienteDTO ClienteDTOOld) {
        String oldNameCliente,newNameCliente;
        ClienteDTO ClienteDTONew;
        ClienteDTONew = new ClienteDTO(ClienteDTOOld);
        oldNameCliente = ClienteDTOOld.getNombre();
        newNameCliente = vClientes.askString("Introduce el nuevo nombre del usuario: ", 250);
        if (newNameCliente == null) return;
        ClienteDTONew.setNombre(newNameCliente);
        setCliente(ClienteDTOOld, ClienteDTONew);
        showExitoMod("nombre",oldNameCliente, ClienteDTONew.getNombre());
    }
    /**
     * Modifica el domicilio del cliente.
     * @param ClienteDTOOld cliente a modificar
     */
    public void modClienteDomicilio(ClienteDTO ClienteDTOOld){
        String oldDomicilio,newDomicilio;
        ClienteDTO ClienteDTONew;
        ClienteDTONew = new ClienteDTO(ClienteDTOOld);
        oldDomicilio = ClienteDTOOld.getDomicilio();
        newDomicilio = vClientes.askString("Introduce el nuevo domicilio: ", 250);
        if(newDomicilio == null) return;
        ClienteDTONew.setDomicilio(newDomicilio);
        setCliente(ClienteDTOOld, ClienteDTONew);
        showExitoMod("domicilio",oldDomicilio, ClienteDTONew.getDomicilio());
    }
    /**
     * Modifica el NIF del cliente.
     * @param ClienteDTOOld cliente a modificar
     */
    public void modClienteNIF(ClienteDTO ClienteDTOOld){
        String oldNIF,newNIF;
        ClienteDTO ClienteDTONew;
        ClienteDTONew = ClienteDTOOld;
        oldNIF = ClienteDTOOld.getNif();
        newNIF = vClientes.askNIF();
        if(newNIF == null) return;
        ClienteDTONew.setNif(newNIF);
        setCliente(ClienteDTOOld, ClienteDTONew);
        showExitoMod("NIF",oldNIF, ClienteDTONew.getNif());
    }
    /**
     * Modifica el email del cliente.
     * @param ClienteDTOOld cliente a modificar
     */
    public void modClienteEmail(ClienteDTO ClienteDTOOld){
        String oldEmail,newEmail;
        ClienteDTO ClienteDTONew;
        ClienteDTONew = new ClienteDTO(ClienteDTOOld);
        oldEmail = ClienteDTOOld.getEmail();
        newEmail = vClientes.askEmail(true);
        if(newEmail == null) return;
        ClienteDTONew.setEmail(newEmail);
        setCliente(ClienteDTOOld, ClienteDTONew);
        showExitoMod("email",oldEmail, ClienteDTONew.getEmail());
    }
    /**
     * Modifica la categoría del cliente.
     * @param ClienteDTOOld cliente a modificar
     */
    public void modClienteCategoria(ClienteDTO ClienteDTOOld){
        CategoriaDTO oldCategoriaDTO, newCategoriaDTO;
        ClienteDTO ClienteDTONew;
        ClienteDTONew = new ClienteDTO(ClienteDTOOld);
        oldCategoriaDTO = ClienteDTOOld.getCategoria();
        newCategoriaDTO = mClientes.getCategoria(vClientes.askCategoriaCliente());
        if(newCategoriaDTO == null) return;
        ClienteDTONew.setCategoria(newCategoriaDTO);
        setCliente(ClienteDTOOld, ClienteDTONew);
        showExitoMod("categoria", oldCategoriaDTO.getNombre(), ClienteDTONew.getCategoria().getNombre());
    }

    //*************************** Peticiones especializadas ***************************//

    /**
     * Pide al usuario seleccionar un cliente a modificar.
     * @return cliente seleccionado o null si se canceló
     */
    public ClienteDTO askClienteModificar() throws Exception {
        String datoCliente;
        int intentos = 0;
        ClienteDTO ClienteDTO = null;
        while (true) {
            showListClientes();
            datoCliente = vClientes.askString("Introduce el NIF o el Email del clienteDTO a modificar: ", 250);
            if (mClientes.getClienteNif(datoCliente) == null) ClienteDTO = mClientes.getClienteEmail(datoCliente);
            else ClienteDTO = mClientes.getClienteNif(datoCliente);
            intentos++;
            if (ClienteDTO != null) {
                vClientes.showMensaje("******** Datos del clienteDTO a modificar ********", true);
                vClientes.showMensaje(ClienteDTO.toString(), true);
                vClientes.showMensaje("***********************************************", true);
                break;
            }
            if (intentos > 2) {
                vClientes.showMensajePausa("Error. Intentos máximos superados. Volviendo al programa principal...", true);
                return null;
            }
            vClientes.showMensajePausa("Error. El clienteDTO indicado no existe. Vuelve a intentarlo.", true);
        }
        return ClienteDTO;
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
    public CategoriaDTO getCategoria (int opcion){
        return mClientes.getCategoria(opcion);
    }

    /**
     * Obtiene un cliente según su índice en la lista.
     * @param indexCliente índice del cliente
     * @return cliente correspondiente
     */
    public ClienteDTO getCliente(int indexCliente) { return mClientes.getClienteIndex(indexCliente); }

    /**
     * Obtiene un cliente según su email.
     * @param email email del cliente
     * @return cliente correspondiente
     */
    public ClienteDTO getClienteEmail(String email){
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
        ClienteDTO ClienteDTO;
        String datoCliente = vClientes.askString("Introduce el NIF o el Email del clienteDTO a mostrar: ", 250);
        if (mClientes.getClienteNif(datoCliente) == null) ClienteDTO = mClientes.getClienteEmail(datoCliente);
        else ClienteDTO = mClientes.getClienteNif(datoCliente);
        if (ClienteDTO == null) {
            vClientes.showMensajePausa("Error. El clienteDTO indicado no existe.", true);
            return;
        }
        vClientes.showCliente(ClienteDTO);
    }

    /**
     * Muestra la lista completa de clientes sin filtros.
     */
    public void showListClientes() throws Exception {
        vClientes.showListClientes(getListaClientes());
    }
    /**
     * Muestra la lista de clientes numerada.
     */
    public void showListClientesNumerada() throws Exception {
        vClientes.showListClientesNumerada( getListaClientes());
    }

    /**
     * Muestra la lista de clientes filtrada por categoría.
     */
    public void showListClientesCategoria() {
        CategoriaDTO categoriaDTO = getCategoria(vClientes.askCategoriaCliente());
        vClientes.showListClientesCategoria(getListClientesCategoria(categoriaDTO), categoriaDTO);
    }

    //*************************** Obtener listas ***************************//

    /**
     * Devuelve la lista de todos los clientes registrados.
     * @return lista de clientes
     */
    public List<ClienteDTO> getListaClientes() throws Exception {
        return mClientes.getClientes();
    }

    /**
     * Devuelve la lista de clientes que pertenecen a una categoría.
     * @param categoriaDTO categoría a filtrar
     * @return lista de clientes
     */
    public List<ClienteDTO> getListClientesCategoria(CategoriaDTO categoriaDTO) {
        return mClientes.getClientesCategoria(categoriaDTO);
    }

    //*************************** Carga de datos ***************************//

    /**
     * Carga la colección de clientes en memoria desde configuración.
     * @return true si la carga fue exitosa, false si no
     */
    public boolean loadClientes() {
        if (configuracion == 0) {
            return mClientes.loadClientes();
        }
        else {
            return false;
        }
    }
}
