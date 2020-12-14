package udproject.compras.firebase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import udproject.compras.Adapters.Item_Producto;

public class LocalDB extends SQLiteOpenHelper {

    private static final String Nombre_BD="BDLocal";
    private static final int Version_BD=1;

    private static final String TABLA_LISTA = " CREATE TABLE MiLista" +
            "(" +
            "   Id_Lista INT PRIMARY KEY NOT NULL,"+
            "   Id_Producto INT,"+
            "   PresupuestoInicial INT,"+
            "   Name_Lista VARCHAR (15) NOT NULL," +
            "   Fecha_Lista DATETIME NOT NULL," +
            "   FOREIGN KEY (Id_Producto) REFERENCES Productos(Id_Producto)"+
            ");";

    private static  final String TABLA_PRODUCTO ="CREATE TABLE Productos" +
            "(" +
            "    Id_Producto INT PRIMARY KEY NOT NULL," +
            "    Name_Producto VARCHAR(50) NOT NULL," +
            "    Precio_Producto INT NOT NULL," +
            "    Descripcion_Producto varchar (50) NOT NULL" +
            ");";

    private static final String TABLA_HISTORIAL="CREATE TABLE Historial" +
            "("+
            "    Id_Historial INT PRIMARY KEY NOT NULL," +
            "    Id_Lista INT,"+
            "    FOREIGN KEY (Id_Lista) REFERENCES MiLista(Id_Lista)"+
            ");";

    private Context context;

    public LocalDB(Context context) {
        super(context, Nombre_BD, null, Version_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_LISTA);
        sqLiteDatabase.execSQL(TABLA_PRODUCTO);
        sqLiteDatabase.execSQL(TABLA_HISTORIAL);
        sqLiteDatabase.execSQL("insert into Productos (Id_Producto, Name_Producto, Precio_Producto, Descripcion_Producto)" +
                "VALUES (3, 'CAFE', 2000, 'UN CAFECITO')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_LISTA);
        sqLiteDatabase.execSQL(TABLA_LISTA);
        onCreate(sqLiteDatabase);
    }

    public void AgregarProducto(int id_product, String Nombre, int Precio, String Descripcion)
    {
        SQLiteDatabase BDAgregar=getWritableDatabase();
        if (BDAgregar!=null)
        {
            BDAgregar.execSQL("INSERT INTO Productos (Id_Producto, Name_Producto, Precio_Producto, Descripcion_Producto) VALUES ("+id_product+",' "+Nombre+"', "+Precio+", '"+Descripcion+"');");

            BDAgregar.close();
        }
    }

    public List<Item_Producto> ListaProducto()
    {
        SQLiteDatabase BDReadProducto=getReadableDatabase();

        Cursor cr=BDReadProducto.rawQuery("SELECT * FROM Productos;", null);
        List<Item_Producto> productoList=new ArrayList<>();


        if (cr.moveToFirst())
        {
            do {
                productoList.add(new Item_Producto(cr.getInt(0), cr.getString(1), cr.getInt(2), cr.getString(3)));
            }while (cr.moveToNext());
        }

        BDReadProducto.close();
        return productoList;
    }

    public boolean ifExist()
    {
        SQLiteDatabase bdCheck=getReadableDatabase();
        Cursor cr=bdCheck.rawQuery("SELECT Id_Lista from MiLista;", null);

        return cr.moveToFirst();

    }
}
