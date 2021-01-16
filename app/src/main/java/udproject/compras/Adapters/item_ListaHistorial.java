package udproject.compras.Adapters;

public class item_ListaHistorial {
    String IDhistorial, IDlista;

    public item_ListaHistorial(String IDhistorial, String IDlista) {
        this.IDhistorial = IDhistorial;
        this.IDlista = IDlista;
    }

    public item_ListaHistorial()
    {

    }

    public String getIDhistorial() {
        return IDhistorial;
    }

    public void setIDhistorial(String IDhistorial) {
        this.IDhistorial = IDhistorial;
    }

    public String getIDlista() {
        return IDlista;
    }

    public void setIDlista(String IDlista) {
        this.IDlista = IDlista;
    }
}
