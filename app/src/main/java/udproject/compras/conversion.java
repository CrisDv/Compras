package udproject.compras;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class conversion {

    public String SeparadorFormat(String valor)
    {
        final String separador="###,###.###";
        DecimalFormat DFormat=new DecimalFormat(separador);
        NumberFormat NFormat=NumberFormat.getInstance();
        int i =Integer.parseInt(valor);
        String ValorConvertido=DFormat.format(i);
        return ValorConvertido;
    }

}
