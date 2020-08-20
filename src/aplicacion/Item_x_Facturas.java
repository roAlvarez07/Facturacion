package aplicacion;

public class Item_x_Facturas {
    private int id_factura;
    private int id_item;
    private int cantidad;
    
    public Item_x_Facturas(int id_factura, int id_item, int cantidad){
        this.id_factura = id_factura;
        this.id_item = id_item;
        this.cantidad = cantidad;
    }
}
