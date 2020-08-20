package aplicacion;

public class Item {
    private String detalle;
    private int precio;
    private int cantidad;
    
    public Item(String detalle, int precio, int cantidad){
        this.detalle = detalle;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
     public int getCantidad (){
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    @Override
    public String toString() {
        return detalle+" - "+precio+" - "+cantidad;
    }
    
}
