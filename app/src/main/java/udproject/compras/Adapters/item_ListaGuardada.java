package udproject.compras.Adapters;

public class item_ListaGuardada {
    private String NombreLista, IDLista, AlmacenDeCompra;
    private int PresupuestoInicial, TotalProductos;

    public item_ListaGuardada(String nombreLista, String IDLista, int presupuestoInicial, int totalProductos, String AlmacenDeCompra) {
        NombreLista = nombreLista;
        this.IDLista = IDLista;
        PresupuestoInicial = presupuestoInicial;
        TotalProductos = totalProductos;
        AlmacenDeCompra=AlmacenDeCompra;
    }

    public item_ListaGuardada() {

    }

    public String getNombreLista() {
        return NombreLista;
    }

    public void setNombreLista(String nombreLista) {
        NombreLista = nombreLista;
    }

    public String getIDLista() {
        return IDLista;
    }

    public void setIDLista(String IDLista) {
        this.IDLista = IDLista;
    }

    public int getPresupuestoInicial() {
        return PresupuestoInicial;
    }

    public void setPresupuestoInicial(int presupuestoInicial) {
        PresupuestoInicial = presupuestoInicial;
    }

    public int getTotalProductos() {
        return TotalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        TotalProductos = totalProductos;
    }

    public String getAlmacenDeCompra() {
        return AlmacenDeCompra;
    }

    public void setAlmacenDeCompra(String almacenDeCompra) {
        AlmacenDeCompra = almacenDeCompra;
    }
}
