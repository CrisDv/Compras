package udproject.compras.Adapters;

import android.widget.TextView;

public class Item_Texto_Scanner {
    private boolean Check;
    private String Texto;

    public Item_Texto_Scanner(boolean check, String texto) {
        Check = check;
        Texto = texto;
    }

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }
}
