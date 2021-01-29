package udproject.compras.Recognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.Feature;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.Item_Texto_Scanner;
import udproject.compras.R;
import udproject.compras.firebase.LocalDB;
import udproject.compras.recycler.RecyclerTextoEscaneado;


import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class Camara extends AppCompatActivity implements View.OnClickListener, RecyclerTextoEscaneado.OnItemCheckedListener {

    ImageView imagen;
    Bitmap imgBitmap;
    Button TomarCaptura, analizarCaptura, Guardar;
    CheckBox checkBox;

    private RecyclerView recyclerViewText;
    private RecyclerTextoEscaneado AdapterTextoEscaneado;
    List<Item_Texto_Scanner> texto_scanners_List=new ArrayList<>();

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RequestCameraPermissionID = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

            TomarCaptura=findViewById(R.id.Tomar);
            analizarCaptura=findViewById(R.id.AnalizarImagen);
            Guardar=findViewById(R.id.SaveSelection);

            TomarCaptura.setOnClickListener(this);
            analizarCaptura.setOnClickListener(this);
            Guardar.setOnClickListener(this);
            imagen=findViewById(R.id.imgViewTomar);
            checkBox=findViewById(R.id.check);


            recyclerViewText=findViewById(R.id.RecyclerOpcionesdeTexto);
            recyclerViewText.setLayoutManager(new LinearLayoutManager(this));
            AdapterTextoEscaneado=new RecyclerTextoEscaneado(texto_scanners_List, Camara.this);
            recyclerViewText.setAdapter(AdapterTextoEscaneado);


            ValidarPermisos();
            botones();
    }

    private void botones()
    {
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.5f
        );

        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
        );

        if (texto_scanners_List.size()==0){
            Guardar.setVisibility(View.GONE);
            TomarCaptura.setLayoutParams(param1);
            analizarCaptura.setLayoutParams(param1);
        }
        else{
            Guardar.setVisibility(View.VISIBLE);
            TomarCaptura.setLayoutParams(param2);
            analizarCaptura.setLayoutParams(param2);
        }
    }


    private void ValidarPermisos()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }catch (Exception e){
            System.out.println("ERROR CAMARA: "+e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
             imgBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imgBitmap);
        }
    }


    private void DetectText()
    {
        if (imgBitmap!=null){
            FirebaseVisionImage FirebaseImage=FirebaseVisionImage.fromBitmap(imgBitmap);
            //FirebaseVisionTextRecognizer TextDetector=getInstance()
            FirebaseVisionTextDetector textDetector= FirebaseVision.getInstance().getVisionTextDetector();

            textDetector.detectInImage(FirebaseImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    if (texto_scanners_List.size()!=0){
                        texto_scanners_List.clear();
                    }
                    else{
                        DisplayText(firebaseVisionText);
                    }
                    botones();
                }
            });
        }else {
            Toast.makeText(this, "Primero toma una foto para poder escanear", Toast.LENGTH_LONG).show();
        }
    }


    private void  DisplayText(FirebaseVisionText visionText)
    {
        List<FirebaseVisionText.Block> blockList=visionText.getBlocks();

        for (FirebaseVisionText.Block block: visionText.getBlocks()){
            String text=block.getText();
            System.out.println(text);

            texto_scanners_List.add(new Item_Texto_Scanner(false, text));
            AdapterTextoEscaneado.notifyDataSetChanged();
            AdapterTextoEscaneado.notifyItemRangeInserted(0, texto_scanners_List.size());
        }
    }


    public void InsertarProducto(String Nombre, String Precio){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==REQUEST_IMAGE_CAPTURE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                System.out.println("ja");
            }
            else if (grantResults[0]==PackageManager.PERMISSION_DENIED){
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Tomar:
                dispatchTakePictureIntent();
                break;
            case R.id.AnalizarImagen:
                DetectText();
                break;
            case R.id.SaveSelection:

                break;
        }
    }

    @Override
    public void onItemCheckedListener(int position) {
        Toast.makeText(this, "da "+String.valueOf(position), Toast.LENGTH_LONG).show();
    }
}