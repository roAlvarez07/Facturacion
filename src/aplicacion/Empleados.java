package aplicacion;

public class Empleados {
    private Sucursal sucursal;
    private String nombre;
    private String apellido;
    private String dni;
    private int id;
    //necesito preguntarle a la sucursal si soy gerente o no
    public Empleados(int idEmp, int id_sucu, String nombre, String apellido, String dni){
        this.id = idEmp;
        this.sucursal = Sucursal.obtenerSucursal(id_sucu);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    //metodopara saber si soy gerente o no que lo hago con un metodo de la sucursalç
    //le poregunto a la sucu si soy gerente o no 
    public boolean esGerente() {
        return sucursal.esGerente(id);
    }
}
