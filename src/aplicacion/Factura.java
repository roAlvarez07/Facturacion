package aplicacion;

public class Factura {
    private int id_clie;
    private int id_sucu;
    private String fecha;
    private int id_mozo;
    
    private int precio;
    private int cantidad;
    
    public Factura(int id_clie, int id_sucu, String fecha, int id_mozo){
        this.id_clie = id_clie;
        this.id_sucu = id_sucu;
        this.fecha = fecha;
        this.id_mozo = id_mozo;
    }
    
    public Factura(int precio, int cantidad){
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId_clie() {
        return id_clie;
    }

    public void setId_clie(int id_clie) {
        this.id_clie = id_clie;
    }

    public int getId_sucu() {
        return id_sucu;
    }

    public void setId_sucu(int id_sucu) {
        this.id_sucu = id_sucu;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_mozo() {
        return id_mozo;
    }

    public void setId_mozo(int id_mozo) {
        this.id_mozo = id_mozo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
