package aplicacion;

public class Sucursal {

    static Sucursal obtenerSucursal(int id_sucu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private int id_gerente;
    private String direccion;
    
    public Sucursal(int id_gerente, String direccion){
        this.id_gerente = id_gerente;
        this.direccion = direccion;
    }
    
    public boolean esGerente(int idEmpleado) {
        return id_gerente == idEmpleado;
    }
}
