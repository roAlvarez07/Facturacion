package aplicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servicios {
    
    //Seleccionar el id del empleado por medio de su nombre y documento
    private final String SELECT_STATEMENT_LOGIN = "SELECT empl_id "
            + " FROM empleados"
            + " WHERE nombre = ? AND dni = ?";
    
    //seleccionar el gerente x el id desde la sucursal --->verificacion del gerente
    
    private final String SELECT_STATEMENT_GERENTE = "SELECT id_gerente "
            + " FROM sucursal"
            + " WHERE id_gerente = ? AND sucu_id = ?";
    
    //Ingreso un nuevo item con su detalle y su precio
    private final String INSERT_STATEMENT_ITEM = "INSERT INTO item"
    		+ "(detalle,precio) VALUES"
    		+ "(?,?)";
    
    //Selecciono el detalle del item por su id
    private final String SELECT_STATEMENT_ITEM = "SELECT item_id "
            + " FROM item"
            + " WHERE detalle = ?";
    
    //Ingreso un id de un item y su cantidad
    private final String INSERT_STATEMENT_ITEM_FACTURA = "INSERT INTO Items_x_Facturas"
    		+ "(id_factura,id_item,cantidad) VALUES"
    		+ "(?,?,?)";
    
    private final String INSERT_STATEMENT_EMPLEADO = "INSERT INTO Empleados"
    		+ "(id_sucu,nombre,apellido,dni) VALUES"
    		+ "(?,?,?,?)";
    
    private final String INSERT_STATEMENT_SUCURSAL = "INSERT INTO Sucursal"
    		+ "(id_gerente,direccion) VALUES"
    		+ "(?,?)";
    
    //Seleccionar todos los items segun el id de la factura
    private final String SELECT_STATEMENT_ITEMS = "SELECT * "
            + " FROM items_x_facturas"
            + " WHERE id_factura = ?";
    
    //Seleccionar el detalle del item por su id
    private final String SELECT_STATEMENT_ITEM_DETALLE = "SELECT detalle "
            + " FROM item"
            + " WHERE item_id = ?";
    
    //Seleccionar el precio del item por su id
    private final String SELECT_STATEMENT_ITEM_PRECIO = "SELECT precio "
            + " FROM item"
            + " WHERE item_id = ?";
    
    //Seleccionar el precio del item por su detalle
    private final String SELECT_STATEMENT_ITEM_PRECIO_DETALLE = "SELECT precio "
            + " FROM item"
            + " WHERE detalle = ?";
    
    //Seleccionar el id del sucursal por su direccion
    private final String SELECT_STATEMENT_ID_SUCURSAL_POR_DIRECCION = "SELECT sucu_id "
            + " FROM sucursal"
            + " WHERE direccion = ?";
    
    //Seleccionar el id del cliente por su nombre
    private final String SELECT_STATEMENT_NOMBRE_CLIENTE = "SELECT clie_id "
            + " FROM cliente"
            + " WHERE nombre = ?";
    
    //Seleccionar todos los items con el id de la factura
    private final String SELECT_STATEMENT_FACTURA = "SELECT id_item "
            + " FROM items_x_facturas"
            + " WHERE id_factura = ?";
    
    private final String SELECT_STATEMENT_FACTURA_PRECIO = "SELECT precio "
            + " FROM item"
            + " WHERE item_id = ?";
    
    private final String SELECT_STATEMENT_FACTURA_CANTIDAD = "SELECT cantidad "
            + " FROM items_x_facturas"
            + " WHERE id_factura = ?";
    //historial de factura
    
    private final String INSERT_STATEMENT_HISTORIAL_FACTURA = "INSERT INTO Factura"
    		+ "(fact_id, id_clie, id_secu, fecha, id_mozo) VALUES"
    		+ "(?,?,?,?,?)";
    //Verifica si hay un empleado con un username(nombre) y una password(documento)
    //Si hay un empleado con ese username y password(por medio del id), 
    //devuelve TRUE sino se le informa del error
    public boolean verificacionLogin(String username, String password){
        
        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int id = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_LOGIN);
            preparedStmtbuscar.setString(1, username);
            preparedStmtbuscar.setString(2, password);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	id = rs.getInt("empl_id");
            } else {
                System.out.println("El username o la password son incorrectas");
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return id != 0;
    }
    
    //verificacion del gerente
    public boolean verificacionGerente(String id_gerente,String sucu_id ){
        
        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int id = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_GERENTE);
            preparedStmtbuscar.setString(1, id_gerente);
            preparedStmtbuscar.setString(2, sucu_id);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	id = rs.getInt("id_gerente");
            } else {
                System.out.println("El username o la password son incorrectas");
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return id != 0;
    }
    
    //Ingresar un item nuevo
    public void insertarItem(String detalle, int precio) {

        Connection conexion = null;
        PreparedStatement preparedStmtInsercion = null;

        try {

            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtInsercion = conexion.prepareStatement(INSERT_STATEMENT_ITEM);
            preparedStmtInsercion.setString(1, detalle);
            preparedStmtInsercion.setInt(2, precio);
            preparedStmtInsercion.execute();

        } catch (ReflectiveOperationException | SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStmtInsercion != null) {
                try {
                    preparedStmtInsercion.close();
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //Seleccionar el id del item por su nombre
    public int getIdItemPorNombre(String nombre_item){

        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int id = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_ITEM);
            preparedStmtbuscar.setString(1, nombre_item);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	id = rs.getInt("item_id");
            } else {
                System.out.println("No existe ese item");
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return id;
    }
    
    //Ingresar un item a la factura
    public void insertarItemFactura(String nombre_item, int cantidad, int id_cliente){
	    	
    Connection conexion = null;
    PreparedStatement preparedStmtInsercion = null;

        try {

        conexion = AdministradorDeConexiones.getConnection();
        preparedStmtInsercion = conexion.prepareStatement(INSERT_STATEMENT_ITEM_FACTURA);
        preparedStmtInsercion.setInt(1, id_cliente);
        preparedStmtInsercion.setInt(2, getIdItemPorNombre(nombre_item));
        preparedStmtInsercion.setInt(3, cantidad);
        preparedStmtInsercion.execute();	

        } catch (ReflectiveOperationException | SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStmtInsercion != null) {
                try {
                    preparedStmtInsercion.close();
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //Seleccionar el detalle de un item por su id
    public String getDetalleItemPorID(int id_item){

        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        String detalle = null;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_ITEM_DETALLE);
            preparedStmtbuscar.setInt(1, id_item);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	detalle = rs.getString("detalle");
            } else {
                System.out.println("No existe ese item");
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return detalle;
    }
    
    //Seleccionar el precio de un item por su id
    public int getPrecioItemPorID(int id_item){

        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int precio = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_ITEM_PRECIO);
            preparedStmtbuscar.setInt(1, id_item);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	precio = rs.getInt("precio");
            } else {
                System.out.println("No existe ese item");
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return precio;
    }
    
    //Seleccionar el id del cliente por el nombre
    public int getIDClientePorNombre(String nombre_cliente){

        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int id = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_NOMBRE_CLIENTE);
            preparedStmtbuscar.setString(1, nombre_cliente);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	id = rs.getInt("clie_id");
            } else {
                System.out.println("No existe ese item");
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return id;
    }
    
    //Seleccionar el precio de un item por su detalle
    public int getPrecioItemPorDetalle(String detalle){

        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int precio = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_ITEM_PRECIO_DETALLE);
            preparedStmtbuscar.setString(1, detalle);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	precio = rs.getInt("precio");
            } else {
                System.out.println("No existe ese item");
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return precio;
    }
    
    public List generarFactura(int id_cliente){
        
        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        List<Integer> listaidfactura = new ArrayList<>();

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_FACTURA);
            preparedStmtbuscar.setInt(1, id_cliente);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            while(rs.next()) {
            	listaidfactura.add(rs.getInt("id_item"));
            }
            
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return listaidfactura;
    }
    
    public void insertarEmpleado(int id_sucursal, String nombre, String apellido, String DNI){
	    	
    Connection conexion = null;
    PreparedStatement preparedStmtInsercion = null;

    try {

        conexion = AdministradorDeConexiones.getConnection();
        preparedStmtInsercion = conexion.prepareStatement(INSERT_STATEMENT_EMPLEADO);
        preparedStmtInsercion.setInt(1, id_sucursal);
        preparedStmtInsercion.setString(2, nombre);
        preparedStmtInsercion.setString(3, apellido);
        preparedStmtInsercion.setString(4, DNI);
        preparedStmtInsercion.execute();	

        } catch (ReflectiveOperationException | SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStmtInsercion != null) {
                try {
                    preparedStmtInsercion.close();
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void insertarSucursal(String id_gerente, String direccion){
	    	
    Connection conexion = null;
    PreparedStatement preparedStmtInsercion = null;

    try {

        conexion = AdministradorDeConexiones.getConnection();
        preparedStmtInsercion = conexion.prepareStatement(INSERT_STATEMENT_SUCURSAL);
        preparedStmtInsercion.setString(1, id_gerente);
        preparedStmtInsercion.setString(2, direccion);
        preparedStmtInsercion.execute();	

        } catch (ReflectiveOperationException | SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStmtInsercion != null) {
                try {
                    preparedStmtInsercion.close();
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //Seleccionar el id de la sucursal por direccion
    public int getIDSucursalPorDireccion(String direccion_sucursal){

        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int id = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_ID_SUCURSAL_POR_DIRECCION);
            preparedStmtbuscar.setString(1, direccion_sucursal);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	id = rs.getInt("sucu_id");
            } else {
                System.out.println("No existe esa sucursal");
            }
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return id;
    }
    
    public int generarPrecioTotal(int listaidfactura){
        
        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int precio = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_FACTURA_PRECIO);
            preparedStmtbuscar.setInt(1, listaidfactura);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	precio = rs.getInt("precio");
            } else {
                System.out.println("");
            }
            
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return precio;
    }
    
    public int generarCantidadTotal(int listaidfactura){
        
        Connection conexion = null;
        PreparedStatement preparedStmtbuscar = null;
        int cantidad = 0;

        try {
            conexion = AdministradorDeConexiones.getConnection();
            preparedStmtbuscar = conexion.prepareStatement(SELECT_STATEMENT_FACTURA_CANTIDAD);
            preparedStmtbuscar.setInt(1, listaidfactura);
            ResultSet rs = preparedStmtbuscar.executeQuery();

            if (rs.next()) {
            	cantidad = rs.getInt("cantidad");
            } else {
                System.out.println("");
            }
            
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexion != null) {
                    preparedStmtbuscar.close();
                    conexion.close();
                }
            } catch (SQLException e) {
            }
        }
        return cantidad;
    }
    
    //Insertrar en tabla factura --> bludezzzzzzzzzz
    public void historialFactura(int fact_id, int id_clie, int total, int id_item){
	    	
    Connection conexion = null;
    PreparedStatement preparedStmtInsercion = null;

    try {

        conexion = AdministradorDeConexiones.getConnection();
        preparedStmtInsercion = conexion.prepareStatement(INSERT_STATEMENT_HISTORIAL_FACTURA);
        preparedStmtInsercion.setInt(1, fact_id);
        preparedStmtInsercion.setInt(2, id_clie);
        preparedStmtInsercion.setInt(3, total);
        preparedStmtInsercion.setInt(4, id_item);
        preparedStmtInsercion.execute();	

        } catch (ReflectiveOperationException | SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStmtInsercion != null) {
                try {
                    preparedStmtInsercion.close();
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
