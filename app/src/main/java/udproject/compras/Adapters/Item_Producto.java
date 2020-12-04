package udproject.compras.Adapters;

public class Item_Producto {
    String Nombre, Descripcion;
    int ID, Precio;

    public Item_Producto(int ID, String Nombre, int Precio, String Descripcion)
    {
        this.ID=ID;
        this.Nombre=Nombre;
        this.Descripcion=Descripcion;
        this.Precio=Precio;
    }

    public Item_Producto()
    {

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }
}
