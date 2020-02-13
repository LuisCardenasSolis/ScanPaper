package com.example.scanpaper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView formatTxt;
    private EditText costo,articulo,minimo,familia,contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permisos();


        //CONFIG---------------------------------------------------


        Button scanBtn = findViewById(R.id.scan_button);
        Button buscar = findViewById(R.id.buscar);
        contentTxt =  findViewById(R.id.codigo1);
        formatTxt = findViewById(R.id.codigo2);
        costo = findViewById(R.id.costo);
        articulo = findViewById(R.id.articulo);
        minimo = findViewById(R.id.minimo);
        familia = findViewById(R.id.familia);

        scanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v.getId()==R.id.scan_button){
                    iniciar();
                }
            }
        });

        buscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (v.getId()==R.id.buscar){
                    mostrarInfo(v);
                }
            }
        });

        //--------------------------------------------------------

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
            FragmentManager fm=getSupportFragmentManager();
        if (id == R.id.nav_home) {
            Toast.makeText(getApplication(),"Scaneando...", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_gallery) {
            Toast.makeText(getApplication(),"Productos Scaneados... ", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_tools) {
            Toast.makeText(getApplication(),"Informacion de Productos...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast toast = Toast.makeText(getApplication(),"Compartiendo...", Toast.LENGTH_SHORT);
            toast.show();
        }
        return true;
    }

    //PROPIO----------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            if(scanningResult.getContents()==null){
                Toast.makeText(this,"Cancelaste el scaneo!", Toast.LENGTH_LONG).show();

            }else{
                formatTxt.setText(scanningResult.getFormatName());
                contentTxt.setText(scanningResult.getContents());
            }

        }else{
            super.onActivityResult(requestCode,resultCode,intent);
            Toast.makeText(this,"No se recibieron datos del scaneo!", Toast.LENGTH_SHORT).show();
        }

    }


    private void iniciar(){
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);

        scanIntegrator.setPrompt("Escanear Codigo");
        scanIntegrator.setCameraId(0);
        scanIntegrator.setBeepEnabled(false);
        scanIntegrator.setBeepEnabled(false);
        scanIntegrator.initiateScan();
    }


    public void mostrarInfo(View view){


        if (contentTxt.getText().toString().equals("")){
            Toast.makeText(this,"Codigo vacio no valido!", Toast.LENGTH_LONG).show();
        }else {

            try {
                MyOpenHelper  conn = new MyOpenHelper(this,"productos",null,1);

                SQLiteDatabase db = conn.getReadableDatabase();
                String codigo =  contentTxt.getText().toString();
                Cursor fila = db.rawQuery("SELECT articulo,minimo,costo,familia FROM productos WHERE codigo ='"+codigo+"'", null);

                if ( fila.moveToFirst()){
                    articulo.setText(fila.getString(fila.getColumnIndex("articulo")));
                    minimo.setText(fila.getString(fila.getColumnIndex("minimo")));
                    costo.setText(fila.getString(fila.getColumnIndex("costo")));
                    familia.setText(fila.getString(fila.getColumnIndex("familia")));
                    fila.close();
                    db.close();
                }else{
                    Toast.makeText(this,"Datos no encontrados, intente de nuevo", Toast.LENGTH_SHORT).show();
                    articulo.setText("");
                    minimo.setText("");
                    costo.setText("");
                    familia.setText("");
                }
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length ==2 && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    public void permisos(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Permisos Desactivados");
            dialogo.setMessage("Debe aceptar los permisos de acceso a la camara para poder usar la camara");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},100);
            }
        }
    }



