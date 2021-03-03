package udproject.compras.BD;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import udproject.compras.Adapters.Item_Producto;
import udproject.compras.Adapters.item_ListaGuardada;
import udproject.compras.mainfragments.HomeFragment;

import static android.content.Context.MODE_PRIVATE;

public class LocalDB extends SQLiteOpenHelper {

    private static final String Nombre_BD="BDListaLocal ";
    private static final int Version_BD=6;

    private static final String TABLA_LISTA = " CREATE TABLE MiLista" +
            "(" +
            "   Id_Lista VARCHAR(40) PRIMARY KEY NOT NULL,"+
            "   PresupuestoInicial INT ,"+
            "   Mes_Lista VARCHAR(12)" +
            ");";

    private static  final String TABLA_PRODUCTO ="CREATE TABLE Productos" +
            "(" +
            "    Id_Producto INT PRIMARY KEY NOT NULL," +
            "    Id_Lista VARCHAR(40) NOT NULL,"+
            "    Name_Producto VARCHAR(50) NOT NULL," +
            "    Precio_Producto INT ," +
            "    PrecioUnitario INT NOT NULL,"+
            "    Cantidad INT NOT NULL," +
            "    FOREIGN KEY (Id_Lista) REFERENCES MiLista(Id_Lista)"+
            ");";

    private static final String TABLA_GUARDADA="CREATE TABLE ListaGuardada" +
            "("+
            "    Id_Guardada VARCHAR(40) PRIMARY KEY NOT NULL," +
            "    Id_Lista VARCHAR(40),"+
            "   Name_Lista VARCHAR (15)," +
            "   Almacen VARCHAR (12),"+
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
        SharedPreferences sharedPref = context.getSharedPreferences("CREDENCIALES",Context.MODE_PRIVATE);
        String vid=sharedPref.getString("IDlista", "NO HAY NADA");
        SQLiteDatabase BDAgregar=getWritableDatabase();
        if (BDAgregar!=null)
        {
            BDAgregar.execSQL("INSERT INTO Productos (Id_Producto, Name_Producto, PrecioUnitario, Cantidad, Precio_Producto, Id_Lista) VALUES ("+id_product+",'"+Nombre+"', "+Precio+", '"+Cantidad +"', "+Precio+", '"+vid+"');");

            BDAgregar.close();
        }

        HomeFragment homeFragment=new HomeFragment();
        homeFragment.UpdateRecycler();
    }

    public List<Item_Producto> ListaProducto(String idLista)
    {
        SQLiteDatabase BDReadProducto=getReadableDatabase();
        Cursor cr=BDReadProducto.rawQuery("SELECT Id_Producto, Name_Producto, Precio_Producto , Cantidad, PrecioUnitario FROM Productos WHERE Id_Lista='"+idLista+"' ;", null);
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

    public void GuardarLista(String IDLista, String Nombre, String Almacen)
    {
        String IDGUARDADA=UUID.randomUUID().toString();
        SQLiteDatabase BDAgregar=getWritableDatabase();
        if (BDAgregar!=null)
        {
            BDAgregar.execSQL("INSERT INTO ListaGuardada (Id_Guardada, Id_Lista, Name_Lista, Almacen) VALUES ('"+IDLista+"', '"+IDLista+"', '"+Nombre+"', '"+Almacen+"');");

            BDAgregar.close();
        }
    }



    public List<item_ListaGuardada> ListaGuardada(){

        SQLiteDatabase insertarLista=getReadableDatabase();
        //Cursor cursor=insertarLista.rawQuery("SELECT L.Name_Lista, L.PresupuestoInicial, L.Id_Lista FROM MiLista L, ListaGuardada G where G.Id_Guardada ="+idguardada+"; ", null);
        Cursor cursor=insertarLista.rawQuery("SELECT G.Name_Lista, G.Id_Lista, L.PresupuestoInicial, G.Almacen FROM MiLista L, ListaGuardada G where G.Id_Lista=L.Id_Lista; ", null);
        List<item_ListaGuardada> guardada=new ArrayList<>();
        try{
            while (cursor.moveToNext()){
                guardada.add(new item_ListaGuardada(cursor.getString(0), cursor.getString(1), cursor.getInt(2), 0, cursor.getString(3)));
            }
        }catch (Exception e){
            System.out.println("ERROR BD LISTAGUARDAD: "+e);
        }

        return guardada;
    }
//CHART DETALLES DEL PRODUCTO

    public ArrayList<String> NombredeLista(String Nname)
    {
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<String> Nombre=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT LG.Name_Lista FROM ListaGuardada LG JOIN Productos PR  WHERE PR.Name_Producto LIKE '%"+Nname+"%'", null);

        while ((cursor.moveToNext())){
            Nombre.add(cursor.getString(0));
        }
db.close();

        return Nombre;
    }



    public ArrayList<String> PrecioProductoNombre(String Nombre)
    {
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<String> Productos=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT PrecioUnitario FROM Productos WHERE Name_Producto LIKE '%"+Nombre+"%'; ", null);

        while ((cursor.moveToNext())){
            Productos.add(cursor.getString(0));
        }
        db.close();
        return Productos;
    }

    //CCHART CUENTA FRAGMENT
    public ArrayList<String> Meses()
    {
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<String> meses=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT Mes_Lista FROM MiLista ", null);

        while ((cursor.moveToNext())){
            meses.add(cursor.getString(0));
        }
        db.close();
        return meses;
    }

    public String Presupuestos(String mes){
        SQLiteDatabase db=getReadableDatabase();
        String PresupuestosList="";
        Cursor cursor=db.rawQuery("SELECT SUM(PresupuestoInicial) FROM MiLista WHERE Mes_Lista='"+mes+"';  ", null);

        while ((cursor.moveToNext())){
            PresupuestosList=cursor.getString(0);
        }
        db.close();
        return PresupuestosList;


    }


    public void Productos (String id){
        SQLiteDatabase readSQL=getReadableDatabase();
        Cursor cursor=readSQL.rawQuery("SELECT Id_Producto, Name_Producto,Precio_Producto , Cantidad, PrecioUnitario FROM Productos  WHERE Id_Lista='"+id+"'; ", null);

        while (cursor.moveToNext()){
            System.out.println(cursor.getString(0)+ " "+cursor.getString(1));
        }
    }

    public int CantidadGastada(){
        SharedPreferences sharedPref = context.getSharedPreferences("CREDENCIALES",Context.MODE_PRIVATE);
        String vid=sharedPref.getString("IDlista", "NO HAY NADA");

        SQLiteDatabase read=getReadableDatabase();
        int total=0;
        Cursor cursor=read.rawQuery("SELECT SUM(Precio_Producto) FROM Productos WHERE Id_Lista='"+vid+"' ",null);

        while (cursor.moveToNext()){
            total=cursor.getInt(0);
        }

        read.close();
        return total;
    }

    public void ifExist(String id)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM MiLista WHERE Id_Lista='"+id+"';");
        db.execSQL("DELETE FROM Productos WHERE Id_Lista='"+id+"'");
        db.close();
    }

    public void AgregarALista(int Presupuesto){
        String IDLista = UUID.randomUUID().toString();
        String IDhistorial=UUID.randomUUID().toString();

        SharedPreferences shared = context.getSharedPreferences("CREDENCIALES", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("IDlista", IDLista);
        SQLiteDatabase BDCrear=getWritableDatabase();

        Calendar calendar=Calendar.getInstance();

        String meses[] = {
                "Enero",
                "Febrero",
                "Marzo",
                "Abril",
                "Mayo",
                "Junio",
                "Julio",
                "Agosto",
                "Septiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"};

        editor.putString("Mes", meses[calendar.get(Calendar.MONTH)]);
        editor.commit();


        if (BDCrear!=null){
            BDCrear.execSQL("INSERT INTO MiLista (Id_Lista, PresupuestoInicial, Mes_Lista) VALUES ('"+IDLista+"', "+Presupuesto+", '"+meses[calendar.get(Calendar.MONTH)] +"')");

            BDCrear.close();
        }
    }

    public void GuardarListaFirebase(String id, String Presupuesto, String mes, String Almacen, String Nombre)
    {
        String IDward = UUID.randomUUID().toString();
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        int pr=Integer.parseInt(Presupuesto);
        sqLiteDatabase.execSQL("INSERT INTO MiLista (Id_Lista, PresupuestoInicial, Mes_Lista) " +
                "VALUES ('"+id+"', "+pr+", '"+mes+"')");
        sqLiteDatabase.execSQL("INSERT INTO ListaGuardada(Id_Guardada, Id_Lista, Name_Lista, Almacen)" +
                "VALUES ('"+IDward+"', '"+id+"', '"+Nombre+"', '"+Almacen+"')");

        sqLiteDatabase.close();
    }

    public void GuardarProductosFirebase(String idLista, String Nombre, String Precio){
        int IDproducto= (int) (Math.floor(Math.random() * (5000 - 1)) + 1);
        int PrecioUni=Integer.parseInt(Precio);
        SQLiteDatabase db=getWritableDatabase();

        db.execSQL("INSERT INTO Productos (Id_Producto, Id_Lista, Name_Producto, PrecioUnitario, Cantidad, Precio_Producto)" +
                "VALUES ("+IDproducto+", '"+idLista+"', '"+Nombre+"', "+PrecioUni+", 1, "+PrecioUni+" )");
        db.close();
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

    public int Sugerido(){
        SQLiteDatabase BDCrear=getReadableDatabase();
        Cursor cr=BDCrear.rawQuery("SELECT SUM(PresupuestoInicial) FROM MiLista", null);
        Cursor cr2=BDCrear.rawQuery("SELECT count(PresupuestoInicial) FROM MiLista", null);
        int Presupuesto=0, numRegistros=0, Promedio;


        while (cr.moveToNext()){
            Presupuesto=cr.getInt(0);
        }

        while (cr2.moveToNext()){
            numRegistros=cr2.getInt(0);
        }
        if (numRegistros==0)
        {
            return 0;
        }
        else {
            Promedio=Presupuesto/numRegistros;
        }

        return Promedio;
    }

    public void ActualizarPresupuesto(String IDLista, int Presupuesto){

        SQLiteDatabase db=getWritableDatabase();

        db.execSQL("UPDATE MiLista SET PresupuestoInicial="+Presupuesto+" WHERE Id_Lista='"+IDLista+"'; ");
        db.close();
    }

}
