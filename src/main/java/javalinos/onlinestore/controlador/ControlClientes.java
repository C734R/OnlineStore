package javalinos.onlinestore.controlador;

import javalinos.onlinestore.modelo.DTO.CategoriaDTO;
import javalinos.onlinestore.modelo.gestores.Interfaces.IModeloClientes;
import javalinos.onlinestore.modelo.gestores.ModeloStore;
import javalinos.onlinestore.modelo.DTO.ClienteDTO;
import javalinos.onlinestore.vista.VistaClientes;

import java.util.List;

import static javalinos.onlinestore.utils.Utilidades.checkEmail;
import static javalinos.onlinestore.utils.Utilidades.checkNIF;

/**
 * Controlador para gestionar la lógica del módulo de clientes.
 */
public class ControlClientes extends ControlBase
{

    private final VistaClientes vClientes;
    private final IModeloClientes mClientes;

    /**
     * Constructor del controlador de clientes.
     * @param mStore modelo principal de la tienda
     * @param vClientes vista asociada a los clientes
     */
    public ControlClientes(ModeloStore mStore, VistaClientes vClientes)
    {
        super(mStore);
        this.vClientes = vClientes;
        mClientes = mStore.getModeloClientes();
    }

    //*************************** Menu gestión clientes ***************************//

    /**
     * Inicia el menú de gestión de clientes.
     */
    public void iniciar()
    {
        int opcion;
        try
        {
            while (true)
            {
                vClientes.showCabecera();
                vClientes.showMenu(2);
                opcion = vClientes.askInt("Seleccione una opción", 0, 6, false, false, true);
                switch (opcion)
                {
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
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al ejecutar la operación: \n" + e, true);
        }
    }

    //*************************** CRUD Clientes ***************************//

    /**
     * Añade un nuevo cliente a la colección.
     */
    public void addCliente()
    {
        String nif;
        String email;
        vClientes.showMensaje("******** Añadir Cliente ********", true);
        String nombre = vClientes.askString("Ingrese el nombre del cliente:", 1,250, true, false, true);
        if (nombre == null ) return;
        String domicilio = vClientes.askString("Ingrese el domicilio del cliente:", 1, 250, true, false, true);
        if (domicilio == null ) return;
        while(true) {
            nif = vClientes.askNIF(false, true, false);
            if (nif == null) return;
            else if (getClienteNIF(nif, false) != null) {
                vClientes.showMensajePausa("Ya existe un cliente con este NIF registrado.", true);
                boolean reintentar = vClientes.askBoolean("¿Quieres introducir otro?", true, true);
                if (!reintentar) return;
            } else break;
        }
        while(true) {
            email = vClientes.askEmail(false, true, false);
            if (email == null ) return;
            else if (getClienteEmail(email, false) != null){
                vClientes.showMensajePausa("Ya existe un cliente con este email registrado.", true);
                boolean reintentar = vClientes.askBoolean("¿Quieres introducir otro?", true, true);
                if (!reintentar) return;
            }
            else break;
        }
        CategoriaDTO categoriaDTO = getCategoria(vClientes.askCategoriaCliente());
        if (categoriaDTO == null) return;
        ClienteDTO nuevoClienteDTO = mClientes.makeCliente(nombre, domicilio, nif, email, categoriaDTO);
        try
        {
            mClientes.addCliente(nuevoClienteDTO);
            vClientes.showMensajePausa("Cliente registrado con éxito.", true);
        }
        catch (Exception e) {
            vClientes.showMensajePausa("Error al registrar el cliente: " + e, true);
        }

    }

    private ClienteDTO getClienteNIF(String nif, boolean error)
    {
        try
        {
            return mClientes.getClienteDTONif(nif);
        }
        catch (Exception e)
        {
            if (error) vClientes.showMensajePausa("Error al obtener el cliente seleccionado por NIF: " + e,true);
        }
        return null;
    }

    /**
     * Reemplaza los datos de un cliente antiguo por uno nuevo.
     * @param clienteDTOOld cliente original
     * @param clienteDTONew cliente actualizado
     */
    public void updateCliente(ClienteDTO clienteDTOOld, ClienteDTO clienteDTONew) throws Exception {
        try
        {
            mClientes.updateCliente(clienteDTOOld, clienteDTONew);
        }
        catch (Exception e)
        {
            throw new Exception (e);
        }
    }

    /**
     * Inicia el proceso de eliminación de un cliente según el modo elegido.
     */
    public void removeCliente()
    {
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
    public void removeClienteNif()
    {
        String nif = vClientes.askNIF(false, false, false);
        if (nif == null) return;
        ClienteDTO clienteDTO;
        try
        {
            clienteDTO = mClientes.getClienteDTONif(nif);
            if (clienteDTO == null){
                vClientes.showMensajePausa("Error. El NIF introducido no corresponde a ningún usuario.", true);
                return;
            }
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al eliminar el cliente: " + e.getMessage(), true);
            return;
        }
        try
        {
            mClientes.removeCliente(clienteDTO);
            vClientes.showMensajePausa("Cliente eliminado correctamente.", true);
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al eliminar el cliente: " + e.getMessage(), true);
        }
    }

    /**
     * Elimina un cliente introduciendo su email.
     */
    public void removeClienteEmail()
    {
        String email = vClientes.askEmail(false, false, false);
        if (email == null) return;
        ClienteDTO clienteDTO = null;
        try
        {
            clienteDTO = mClientes.getClienteDTOEmail(email);
            if (clienteDTO == null) {
                vClientes.showMensajePausa("Error. El Email introducido no corresponde a ningún usuario.", true);
                return;
            }
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al obtener el cliente: " + e.getMessage(), true);
        }

        try
        {
            mClientes.removeCliente(clienteDTO);
            vClientes.showMensajePausa("Cliente eliminado correctamente.", true);
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al eliminar el cliente: " + e.getMessage(), true);
        }
    }

    //*************************** Modificar datos cliente ***************************//

    /**
     * Inicia el menú de modificación de datos del cliente.
     */
    public void modCliente()
    {
        int opcion ;
        while (true){
            ClienteDTO clienteDTO = null;
            vClientes.showMensaje("******** Menú de Modificación de Clientes ********", true);
            vClientes.showMods();
            opcion = vClientes.askInt("Introduce el tipo de modificación que deseas realizar", 0, 5, false, false, true);
            if (opcion != 0) {
                clienteDTO = askClienteModificar();
                if (clienteDTO == null) continue;
            }
            switch (opcion) {
                case 1:
                    modClienteNombre(clienteDTO);
                    break;
                case 2:
                    modClienteDomicilio(clienteDTO);
                    break;
                case 3:
                    modClienteNIF(clienteDTO);
                    break;
                case 4:
                    modClienteEmail(clienteDTO);
                    break;
                case 5:
                    modClienteCategoria(clienteDTO);
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
     * @param clienteDTOOld cliente a modificar
     */
    public void modClienteNombre(ClienteDTO clienteDTOOld)
    {
        String oldNameCliente,newNameCliente;
        ClienteDTO clienteDTONew;
        clienteDTONew = new ClienteDTO(clienteDTOOld);
        oldNameCliente = clienteDTOOld.getNombre();
        newNameCliente = vClientes.askString("Introduce el nuevo nombre del usuario: ", 1, 250, true, false, true);
        if (newNameCliente == null) return;
        clienteDTONew.setNombre(newNameCliente);
        try
        {
            updateCliente(clienteDTOOld, clienteDTONew);
            showExitoMod("nombre",oldNameCliente, clienteDTONew.getNombre());
        }
        catch (Exception e) {
            vClientes.showMensajePausa("Error al actualizar el nombre: " + e, true);
        }
    }
    /**
     * Modifica el domicilio del cliente.
     * @param clienteDTOOld cliente a modificar
     */
    public void modClienteDomicilio(ClienteDTO clienteDTOOld) {
        String oldDomicilio, newDomicilio;
        ClienteDTO ClienteDTONew;
        ClienteDTONew = new ClienteDTO(clienteDTOOld);
        oldDomicilio = clienteDTOOld.getDomicilio();
        newDomicilio = vClientes.askString("Introduce el nuevo domicilio: ", 1, 250, true, false, true);
        if (newDomicilio == null) return;
        ClienteDTONew.setDomicilio(newDomicilio);
        try {
            updateCliente(clienteDTOOld, ClienteDTONew);
            showExitoMod("domicilio", oldDomicilio, ClienteDTONew.getDomicilio());
        } catch (Exception e) {
            vClientes.showMensajePausa("Error al actualizar el domicilio: " + e, true);
        }
    }
    /**
     * Modifica el NIF del cliente.
     * @param clienteDTOOld cliente a modificar
     */
    public void modClienteNIF(ClienteDTO clienteDTOOld) {
        String oldNIF, newNIF;
        ClienteDTO clienteDTONew;
        clienteDTONew = new ClienteDTO(clienteDTOOld);
        oldNIF = clienteDTOOld.getNif();
        newNIF = vClientes.askNIF(true, true, false);
        if (newNIF == null) return;
        clienteDTONew.setNif(newNIF);
        try {
            updateCliente(clienteDTOOld, clienteDTONew);
            showExitoMod("NIF", oldNIF, clienteDTONew.getNif());
        } catch (Exception e) {
            vClientes.showMensajePausa("Error al actualizar el nif: " + e, true);
        }
    }
    /**
     * Modifica el email del cliente.
     * @param clienteDTOOld cliente a modificar
     */
    public void modClienteEmail(ClienteDTO clienteDTOOld)
    {
        String oldEmail,newEmail;
        ClienteDTO clienteDTONew;
        clienteDTONew = new ClienteDTO(clienteDTOOld);
        oldEmail = clienteDTOOld.getEmail();
        newEmail = vClientes.askEmail(true, true, false);
        if(newEmail == null) return;
        clienteDTONew.setEmail(newEmail);
        try {
            updateCliente(clienteDTOOld, clienteDTONew);
            showExitoMod("email",oldEmail, clienteDTONew.getEmail());
        } catch (Exception e) {
            vClientes.showMensajePausa("Error al actualizar el email: " + e, true);
        }
    }
    /**
     * Modifica la categoría del cliente.
     * @param clienteDTOOld cliente a modificar
     */
    public void modClienteCategoria(ClienteDTO clienteDTOOld)
    {
        CategoriaDTO categoriaDTOOld, categoridaDTONew;
        ClienteDTO clienteDTONew;
        clienteDTONew = new ClienteDTO(clienteDTOOld);
        categoriaDTOOld = clienteDTOOld.getCategoria();
        try
        {
            categoridaDTONew = mClientes.getCategoriaDTOOpcion(vClientes.askCategoriaCliente());
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al obtener la categoría del cliente: " + e,true);
            return;
        }
        if(categoridaDTONew == null) return;
        clienteDTONew.setCategoria(categoridaDTONew);
        try {
            updateCliente(clienteDTOOld, clienteDTONew);
            showExitoMod("categoría", categoriaDTOOld.getNombre(), clienteDTONew.getCategoria().getNombre());
        } catch (Exception e) {
            vClientes.showMensajePausa("Error al actualizar la categoría: " + e, true);
        }
    }

    //*************************** Peticiones especializadas ***************************//

    /**
     * Pide al usuario seleccionar un cliente a modificar.
     * @return cliente seleccionado o null si se canceló
     */
    public ClienteDTO askClienteModificar()
    {
        enum modos {nif, email, nulo};
        modos modo = modos.nulo;
        ClienteDTO clienteDTO;
        String datoCliente;
        int intentos = 0;
        while (true) {
            showListClientes();
            datoCliente = vClientes.askString("Introduce el NIF o el Email del cliente a modificar: ", 1, 250, false, false, false);
            if (datoCliente != null) {
                if (checkNIF(datoCliente)) modo = modos.nif;
                else if (checkEmail(datoCliente)) modo = modos.email;
            }
            if(datoCliente == null || modo == modos.nulo) {
                vClientes.showMensajePausa("NIF o Email inválido.", true);
                intentos++;
                continue;
            }
            try {
                if (modo == modos.email) clienteDTO = mClientes.getClienteDTOEmail(datoCliente);
                else clienteDTO = mClientes.getClienteDTONif(datoCliente);
                if (clienteDTO != null) {
                    vClientes.showMensaje("******** Datos del cliente a modificar ********", true);
                    vClientes.showMensaje(clienteDTO.toString(), true);
                    vClientes.showMensaje("***********************************************", true);
                    break;
                }
                if (intentos > 2) {
                    vClientes.showMensajePausa("Error. Intentos máximos superados. Volviendo al programa principal...", true);
                    return null;
                }
                vClientes.showMensajePausa("Error. El cliente indicado no existe. Vuelve a intentarlo.", true);

            }
            catch (Exception e)
            {
                vClientes.showMensajePausa("Error al obtener la cliente a modificar: " + e,true);
            }
        }
        return clienteDTO;
    }

    //*************************** Obtener datos singulares ***************************//

    /**
     * Devuelve el índice del primer o último cliente registrado.
     * @param last true para último, false para primero
     * @return índice correspondiente
     */
    public Integer getIndexCliente(Boolean last)
    {
        try {
            if (last) return mClientes.getLastIndexCliente();
            else return mClientes.getFirstIndexCliente();
        }
        catch (Exception e) {
            vClientes.showMensajePausa("Error al obtener el índice: " + e,true);
            return null;
        }
    }
    /**
     * Obtiene una categoría según la opción seleccionada.
     * @param opcion número de opción seleccionada
     * @return categoría correspondiente
     */
    public CategoriaDTO getCategoria (int opcion)
    {
        try
        {
            return mClientes.getCategoriaDTOOpcion(opcion);
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al obtener la categoría seleccionada: " + e,true);
        }
        return null;
    }

    /**
     * Obtiene un cliente según su índice en la lista.
     * @param indexCliente índice del cliente
     * @return cliente correspondiente
     */
    public ClienteDTO getCliente(int indexCliente)
    {
        try {
            return mClientes.getClienteDTOIndex(indexCliente);
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al obtener el cliente seleccionado por índice: " + e,true);
        }
        return null;
    }

    /**
     * Obtiene un cliente según su email.
     * @param email email del cliente
     * @return cliente correspondiente
     */
    public ClienteDTO getClienteEmail(String email, boolean error)
    {
        try
        {
            return mClientes.getClienteDTOEmail(email);
        }
        catch (Exception e)
        {
            if (error) vClientes.showMensajePausa("Error al obtener el cliente seleccionado por email: " + e,true);
        }
        return null;
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
    public <VO,VN> void showExitoMod (String datoModificar, VO valorOld, VN valorNew )
    {
        vClientes.showMensaje("El " + datoModificar + " del cliente se ha cambiado de forma exitosa de " + valorOld + " a " + valorNew + ".",true);
        vClientes.showMensajePausa("",true);
    }
    /**
     * Muestra los datos de un cliente introduciendo su NIF o email.
     */
    public void showCliente()
    {
        enum modos {nif, email, nulo};
        modos modo = modos.nulo;
        ClienteDTO clienteDTO;
        String datoCliente = vClientes.askString("Introduce el NIF o el Email del cliente a mostrar: ", 1, 250, false, false, false);
        if (datoCliente != null) {
            if (checkNIF(datoCliente)) modo = modos.nif;
            else if (checkEmail(datoCliente)) modo = modos.email;
        }
        if(datoCliente == null || modo == modos.nulo) {
            vClientes.showMensajePausa("NIF o Email inválido.", true);
            return;
        }
        try
        {
            if (modo == modos.email) clienteDTO = mClientes.getClienteDTOEmail(datoCliente);
            else clienteDTO = mClientes.getClienteDTONif(datoCliente);
            if (clienteDTO == null)
            {
                vClientes.showMensajePausa("Error. El cliente indicado no existe.", true);
                return;
            }
            vClientes.showCliente(clienteDTO);
            vClientes.showMensajePausa("", true);
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al mostrar el cliente: " + e,true);
        }
    }

    /**
     * Muestra la lista completa de clientes sin filtros.
     */
    public void showListClientes()
    {
        List<ClienteDTO> clientes = getListaClientes();
        if(clientes.isEmpty()) return;
        vClientes.showListClientes(clientes);
    }
    /**
     * Muestra la lista de clientes numerada.
     */
    public void showListClientesNumerada()
    {
        vClientes.showListClientesNumerada(getListaClientes());
    }

    /**
     * Muestra la lista de clientes filtrada por categoría.
     */
    public void showListClientesCategoria()
    {
        CategoriaDTO categoriaDTO = getCategoria(vClientes.askCategoriaCliente());
        vClientes.showListClientesCategoria(getListClientesCategoria(categoriaDTO), categoriaDTO);
    }

    //*************************** Obtener listas ***************************//

    /**
     * Devuelve la lista de todos los clientes registrados.
     * @return lista de clientes
     */
    public List<ClienteDTO> getListaClientes()
    {
        try
        {
            return mClientes.getClientesDTO();
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al obtener la lista de clientes: " + e,true);
            return null;
        }
    }

    /**
     * Devuelve la lista de clientes que pertenecen a una categoría.
     * @param categoriaDTO categoría a filtrar
     * @return lista de clientes
     */
    public List<ClienteDTO> getListClientesCategoria(CategoriaDTO categoriaDTO)
    {
        try
        {
            return mClientes.getClientesCategoria(categoriaDTO);
        }
        catch (Exception e)
        {
            vClientes.showMensajePausa("Error al obtener la lista de clientes por categoria: " + e,true);
            return null;
        }
    }

    //*************************** Carga de datos ***************************//

    /**
     * Carga la colección de clientes en memoria desde configuración.
     */
    public void loadClientes() throws Exception {
        try
        {
            mClientes.loadClientes();
        }
        catch (Exception e)
        {
            throw new Exception("Error al precargar clientes.", e);
        }
    }
}
