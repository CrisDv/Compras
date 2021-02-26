package udproject.compras.menubar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import udproject.compras.R;

public class ComportamientoPresupuestos extends AppCompatActivity {

    private LineChart lineChart;
    private LineDataSet lineDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comportamiento_presupuestos);

        lineChart=findViewById(R.id.lineMenuData);
        data();
    }

    private void data()
    {
        // Creamos un set de datos
        ArrayList<Entry> lineEntries = new ArrayList<Entry>();

        String meses[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (int i = 0; i<11; i++){
            float y = (int) (Math.random() * (80000 - 20000 + 1) + 20000);
            lineEntries.add(new Entry((float)i,y ));
        }
//        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(meses));


// Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, "Dinero en Compras");

// Asociamos al gráfico
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);
    }
}