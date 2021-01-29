package udproject.compras.Adapters;

public class Item_Producto {
    String Nombre;
    int ID, Precio, Cantidad, Preciou;

    public Item_Producto(int ID, String Nombre, int Precio, int Cantidad, int Preciou)
    {
        this.ID=ID;
        this.Nombre=Nombre;
        this.Cantidad=Cantidad;
        this.Precio=Precio;
        this.Preciou=Preciou;
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

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public int getPreciou() {
        return Preciou;
    }

    public void setPreciou(int preciou) {
        Preciou = preciou;
    }
}
