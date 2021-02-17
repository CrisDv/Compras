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
import udproject.compras.Adapters.item_ListaGuardada;
import udproject.compras.mainfragments.HomeFragment;
import udproject.compras.recycler.RecyclerProductAdapter;

import static android.content.Context.MODE_PRIVATE;
import static androidx.camera.core.CameraX.getContext;

public class LocalDB extends SQLiteOpenHelper {

    private static final String Nombre_BD="BDListaLocal ";
    private static final int Version_BD=3;

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
            "    Precio_Producto INT ," +
            "    PrecioUnitario INT NOT NULL,"+
            "    Cantidad INT NOT NULL,"+
            "    Marca varchar(20)"+
            ");";

    private static final String TABLA_GUARDADA="CREATE TABLE ListaGuardada" +
            "("+
            "    Id_Guardada VARCHAR(40) PRIMARY KEY NOT NULL," +
            "    Id_Lista VARCHAR(40),"+
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
        //sqLiteDatabase.execSQL(TABLA_HISTORIAL);
        sqLiteDatabase.execSQL(TABLA_GUARDADA);
        /*sqLiteDatabase.execSQL("insert into Productos (Id_Producto, Name_Producto, PrecioUnitario,Cantidad)" +
                "VALUES (3, 'CAFE', 2000, 1)");*/


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_LISTA);
        sqLiteDatabase.execSQL(TABLA_LISTA);
        onCreate(sqLiteDatabase);
    }

    public void AgregarProducto(int id_product, String Nombre, int Precio, int Cantidad)
    {
       /* SharedPreferences sharedPref = context.getSharedPreferences("CREDENCIALES",Context.MODE_PRIVATE);
        String vid=sharedPref.getString("IDlista", "NO HAY NADA");*/
        SQLiteDatabase BDAgregar=getWritableDatabase();
        if (BDAgregar!=null)
        {
            BDAgregar.execSQL("INSERT INTO Productos (Id_Producto, Name_Producto, PrecioUnitario, Cantidad, Precio_Producto) VALUES ("+id_product+",'"+Nombre+"', "+Precio+", '"+Cantidad +"', "+Precio+");");
            //BDAgregar.execSQL("INSERT INTO MiLista (Id_Producto) VALUES ("+id_product+") WHERE Id_Lista='"+vid+"'");

            BDAgregar.close();
        }
    }

    public List<Item_Producto> ListaProducto(String idLista)
    {
        SQLiteDatabase BDReadProducto=getReadableDatabase();
        Cursor cr=BDReadProducto.rawQuery("SELECT p.Id_Producto, p.Name_Producto,P.Precio_Producto , P.Cantidad, P.PrecioUnitario FROM Productos p, MiLista ML WHERE ML.Id_Lista='"+idLista+"';", null);
        //Cursor cr=BDReadProducto.rawQuery("SELECT p.Id_Producto, p.Name_Producto,P.Precio_Producto , P.Cantidad, P.PrecioUnitario FROM Productos p, MiLista ML WHERE ML.Id_Lista='A';", null);

        List<Item_Producto> productoList=new ArrayList<>();

        try{

                while (cr.moveToNext()){
                    productoList.add(new Item_Producto(cr.getInt(0), cr.getString(1), cr.getInt(2), cr.getInt(3), cr.getInt(4)));

                }

        }catch (Exception e){
            System.out.println("LISTA PRODUCTO LOCAL BD "+e);
        }

        BDReadProducto.close();
        return productoList;
    }

    public void GuardarLista(String IDLista)
    {
        String IDGUARDADA=UUID.randomUUID().toString();
        SQLiteDatabase BDAgregar=getWritableDatabase();
        if (BDAgregar!=null)
        {
            BDAgregar.execSQL("INSERT INTO ListaGuardada (Id_Guardada, Id_Lista) VALUES ('"+IDGUARDADA+"', '"+IDLista+"')");

            BDAgregar.close();
        }
    }

    public List<item_ListaGuardada> ListaGuardada(){

        SQLiteDatabase insertarLista=getReadableDatabase();
        //Cursor cursor=insertarLista.rawQuery("SELECT L.Name_Lista, L.PresupuestoInicial, L.Id_Lista FROM MiLista L, ListaGuardada G where G.Id_Guardada ="+idguardada+"; ", null);
        Cursor cursor=insertarLista.rawQuery("SELECT L.Name_Lista, L.PresupuestoInicial, L.Id_Lista FROM MiLista L, ListaGuardada G ; ", null);
        List<item_ListaGuardada> guardada=new ArrayList<>();
        try{
            while (cursor.moveToNext()){
                guardada.add(new item_ListaGuardada(cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
            }
        }catch (Exception e){
            System.out.println("ERROR BD LISTAGUARDAD: "+e);
        }

        return guardada;
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

    public int PrecioUnitario(int ID){
        SQLiteDatabase BDCrear=getReadableDatabase();
        Cursor cr=BDCrear.rawQuery("SELECT PrecioUnitario FROM Productos WHERE Id_Producto="+ID+";", null);
        int precio=0;
        while (cr.moveToNext()){
            precio=cr.getInt(0);
        }
        return precio;
    }

    public void EliminarProductoDeLista(int ID){

        SQLiteDatabase deleteProduct=getWritableDatabase();
        String SQL="DELETE FROM Productos WHERE Id_Producto="+ID+";";

        if (deleteProduct!=null)
        {
            deleteProduct.execSQL(SQL);
        }
        deleteProduct.close();
    }
}
