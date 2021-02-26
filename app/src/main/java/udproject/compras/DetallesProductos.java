package udproject.compras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;

import udproject.compras.BD.LocalDB;

public class DetallesProductos extends AppCompatActivity {


    BarChart barChart;
    TextView Marca;
    String Nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_productos);

        barChart=findViewById(R.id.GraficaProductos);
        Marca=findViewById(R.id.TipoDeMarca);
        Nombre=getIntent().getStringExtra("NombreProducto");
        //InfoProducto(Nombre);
        showBarChart(Nombre);
    }



    /*public void InfoProducto(String ChildItem){

        DatabaseReference mReference= FirebaseDatabase.getInstance().getReference();
        System.out.println(ChildItem+"hmm");

            mReference.child("Productos").child(ChildItem).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> Productos=new ArrayList<>();
                    ArrayList<String> Valores=new ArrayList<>();

                    if (snapshot.exists()){
                        for (DataSnapshot ds : snapshot.getChildren()){
                            //String childName=ds.getKey().toString();
                            Productos.add(ds.getKey());
                            Valores.add(String.valueOf(ds.getValue(Integer.class)));

                            //String text=String.valueOf(ds.getValue(Integer.class));
                            //System.out.println("PRODUCTO: "+Productos.+" Valor: "+text+" ");
                        }

                        showBarChart(Productos, Valores, ChildItem);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }*/

    private void showBarChart( String Titulo){
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Title";

        LocalDB localDB=new LocalDB(this);
        ArrayList<String> precios=localDB.PrecioProductoNombre(Titulo);
        ArrayList<String> Almacenes=new ArrayList<>();

        for (int i=0;i<precios.size();i++){
            Almacenes.add(localDB.NombredeLista(Titulo));
        }

        Marca.setText(Titulo+" de acuerdo a tus listas");
        //input data


        //fit the data into a bar
        for (int i = 0; i < precios.size(); i++) {
            BarEntry barEntry = new BarEntry(i,Float.parseFloat(precios.get(i)));
            entries.add(barEntry);
        }



        BarDataSet barDataSet = new BarDataSet(entries, title);

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Almacenes));

        BarData data = new BarData(barDataSet);
        barChart.setData(data);

        barChart.invalidate();
    }

}