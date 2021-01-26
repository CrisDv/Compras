package udproject.compras.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.core.database.sqlite.SQLiteDatabaseKt;

import java.security.PublicKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.mainfragments.HomeFragment;

import static android.content.Context.MODE_PRIVATE;
import static androidx.camera.core.CameraX.getContext;

public class LocalDB extends SQLiteOpenHelper {

    private static final String Nombre_BD="BDLocalCOMPRA ";
    private static final int Version_BD=2;

    private static final String TABLA_LISTA = " CREATE TABLE MiLista" +
            "(" +
            "   Id_Lista VARCHAR(40) PRIMARY KEY NOT NULL,"+
            "   Id_Producto INT,"+
            "   PresupuestoInicial INT NOT NULL,"+
            "   Name_Lista VARCHAR (15)," +
            "   Fecha_Lista DATETIME NOT NULL," +
            "   FOREIGN KEY (Id_Producto) REFERENCES Productos(Id_Producto)"+
            ");";

    private static  final String TABLA_PRODUCTO ="CREATE TABLE Productos" +
            "(" +
            "    Id_Producto INT PRIMARY KEY NOT NULL," +
            "    Name_Producto VARCHAR(50) NOT NULL," +
            "    Precio_Producto INT NOT NULL," +
            "    Cantidad INT NOT NULL,"+
            "    Marca varchar(20)"+
            ");";

    private static final String TABLA_HISTORIAL="CREATE TABLE Historial" +
            "("+
            "    Id_Historial VARCHAR(40) PRIMARY KEY NOT NULL," +
            "    Id_Lista VARCHAR(40),"+
            "    FOREIGN KEY (Id_Lista) REFERENCES MiLista(Id_Lista)"+
            ");";

    private static final String TABLA_GUARDADA="CREATE TABLE ListaGuardada" +
            "("+
            "    Id_Guardada VARCHAR(40) PRIMARY KEY NOT NULL," +
            "    Id_Lista VARCHAR(40),"+
            "    Productos_Totales INT NOT NULL,"+
            "    FOREIGN KEY (Id_Lista) REFERENCES MiLista(Id_Lista)"+
            ");";

    private Context context;

    public LocalDB(Context context) {
        super(context, Nombre_BD, null, Version_BD);

        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_LISTA);
        sqLiteDatabase.execSQL(TABLA_PRODUCTO);
        sqLiteDatabase.execSQL(TABLA_HISTORIAL);
        sqLiteDatabase.execSQL(TABLA_GUARDADA);
        sqLiteDatabase.execSQL("insert into Productos (Id_Producto, Name_Producto, Precio_Producto,Cantidad)" +
                "VALUES (3, 'CAFE', 2000, 0)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_LISTA);
        sqLiteDatabase.execSQL(TABLA_LISTA);
        onCreate(sqLiteDatabase);
    }

    public void AgregarProducto(int id_product, String Nombre, int Precio, int Cantidad)
    {
        SQLiteDatabase BDAgregar=getWritableDatabase();
        if (BDAgregar!=null)
        {
            BDAgregar.execSQL("INSERT INTO Productos (Id_Producto, Name_Producto, Precio_Producto, Cantidad) VALUES ("+id_product+",' "+Nombre+"', "+Precio+", '"+Cantidad +"');");

            BDAgregar.close();
        }
    }

    public List<Item_Producto> ListaProducto(String idLista)
    {
        SQLiteDatabase BDReadProducto=getReadableDatabase();
        Cursor cr=BDReadProducto.rawQuery("SELECT p.* FROM Productos p, MiLista ML WHERE ML.Id_Lista='"+idLista+"';", null);
        //Cursor cr=BDReadProducto.rawQuery("SELECT p.* FROM Productos p, MiLista ML;", null);
        List<Item_Producto> productoList=new ArrayList<>();

        try{
            for(int i=0;i<=50;i++){
                while (cr.moveToNext()){
                    productoList.add(new Item_Producto(cr.getInt(0), cr.getString(1), cr.getInt(2), cr.getInt(3)));

                }
            }
        }catch (Exception e){
            System.out.println("LISTA PRODUCTO LOCAL BD "+e);
        }

        BDReadProducto.close();
        return productoList;
    }

    public int CantidadGastada(){
        SQLiteDatabase read=getReadableDatabase();
        int total=0;
        Cursor cursor=read.rawQuery("SELECT SUM(Precio_Producto) FROM Productos",null);

        while (cursor.moveToNext()){
            total=cursor.getInt(0);
        }

        read.close();
        return total;
    }

    public boolean ifExist()
    {
        SQLiteDatabase bdCheck=getReadableDatabase();
        Cursor cr=bdCheck.rawQuery("SELECT Id_Lista from MiLista;", null);
        boolean is=cr.moveToFirst();
        
        bdCheck.close();
        return is;

    }

    public void AgregarALista(int Presupuesto){
        String IDLista = UUID.randomUUID().toString();
        String IDhistorial=UUID.randomUUID().toString();

        SharedPreferences shared = context.getSharedPreferences("CREDENCIALES", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("IDlista", IDLista);
        editor.commit();

        SQLiteDatabase BDCrear=getWritableDatabase();
        Date thisDate=new Date();
        if (BDCrear!=null){
            BDCrear.execSQL("INSERT INTO MiLista (Id_Lista, PresupuestoInicial, Fecha_Lista) VALUES ('"+IDLista+"', "+Presupuesto+", '"+thisDate+"')");

            BDCrear.close();
        }
        ifExist();
    }
}
