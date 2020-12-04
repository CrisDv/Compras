package udproject.compras.Adapters;

public class item_ListaGuardada {
    private String TituloLista;
    private int TotalProductos;
    private String TotalPrecio;

    public item_ListaGuardada(String TituloLista, String TotalPrecio, int TotalProductos)
    {
        this.TituloLista=TituloLista;
        this.TotalPrecio=TotalPrecio;
        this.TotalProductos=TotalProductos;
    }

    public item_ListaGuardada()
    {

    }

    public String getTituloLista() {
        return TituloLista;
    }

    public void setTituloLista(String tituloLista) {
        TituloLista = tituloLista;
    }

    public int getTotalProductos() {
        return TotalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        TotalProductos = totalProductos;
    }

    public String getTotalPrecio() {
        return TotalPrecio;
    }

    public void setTotalPrecio(String totalPrecio) {
        TotalPrecio = totalPrecio;
    }
}
