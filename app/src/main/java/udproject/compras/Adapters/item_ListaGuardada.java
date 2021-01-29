package udproject.compras.Adapters;

public class item_ListaGuardada {
    private String TituloLista;
    private int TotalProductos;
    private int TotalPrecio;
    private String IDproducto;

    public item_ListaGuardada(String tituloLista, int totalPrecio, String IDproducto) {
        TituloLista = tituloLista;
        TotalPrecio = totalPrecio;
        this.IDproducto = IDproducto;
    }

    public String getTituloLista() {
        return TituloLista;
    }

    public void setTituloLista(String tituloLista) {
        TituloLista = tituloLista;
    }

    public int getTotalPrecio() {
        return TotalPrecio;
    }

    public void setTotalPrecio(int totalPrecio) {
        TotalPrecio = totalPrecio;
    }

    public String getIDproducto() {
        return IDproducto;
    }

    public void setIDproducto(String IDproducto) {
        this.IDproducto = IDproducto;
    }
}
