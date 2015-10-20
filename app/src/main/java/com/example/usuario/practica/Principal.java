package com.example.usuario.practica;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.practica.datos.AgregarContacto;
import com.example.usuario.practica.datos.Contacto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Principal extends AppCompatActivity {

    private Adaptador ad;
    private TextView tvNombre, tvTelf;
    private List<Contacto> contactos;
    private List<String> telefonos;
    private ImageView img;
    private  ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.mnAcercaDe:
                Toast.makeText(this,"Poyecto PDMDM 2º DAM:\n Isaac López Delgado",Toast.LENGTH_LONG).show();
                break;
            case R.id.mnAdd:
                Intent intent = new Intent(this, AgregarContacto.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Contacto> getListaContactos(Context contexto){
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String proyeccion[] = null;
        String seleccion = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ? and " +
                ContactsContract.Contacts.HAS_PHONE_NUMBER + "= ?";
        String argumentos[] = new String[]{"1","1"};
        String orden = ContactsContract.Contacts.DISPLAY_NAME + " collate localized asc";
        Cursor cursor = contexto.getContentResolver().query(uri, proyeccion, seleccion, argumentos, orden);
        int indiceId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int indiceNombre = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        List<Contacto> lista = new ArrayList<>();
        Contacto contacto;
        while(cursor.moveToNext()){
            contacto = new Contacto();
            contacto.setId(cursor.getLong(indiceId));
        contacto.setNombre(cursor.getString(indiceNombre));
        lista.add(contacto);
    }
    return lista;
}

    /*prueba : public static void metodo(String nombre, List<String> tlfns){
        Contacto nuevo = new Contacto(nombre,0,tlfns);
        this.contactos.add(nuevo);
    }*/

    public void init(){
        tvNombre = (TextView)findViewById(R.id.tv1);
        contactos= getListaContactos(this);
        img = (ImageView)findViewById(R.id.imageView2);
        tvTelf = (TextView)findViewById(R.id.tvTelefonos);
        for(Contacto l: contactos){
            long id = l.getId();
            telefonos = getListaTelefonos(this,id);
            l.setTelefonos(telefonos);
        }
        /*
        Contacto nuevo =(Contacto)getIntent().getExtras().getSerializable("contacto");
        contactos.add(nuevo);
        */

        lv = (ListView)findViewById(R.id.lvAgenda);
        ad = new Adaptador(this,R.layout.elemento_lista,contactos);
        lv.setAdapter(ad);
        ad.notifyDataSetChanged();
        registerForContextMenu(lv);



    }

    public List<String> getListaTelefonos(Context contexto, long id){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String proyeccion[] = null;
        String seleccion = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        String argumentos[] = new String[]{id+""};
        String orden = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Cursor cursor = contexto.getContentResolver().query(uri, proyeccion, seleccion, argumentos, orden);
        int indiceNumero = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        List<String> lista = new ArrayList<>();
        String numero;
        while(cursor.moveToNext()){
            numero = cursor.getString(indiceNumero);
            lista.add(numero);
        }
        return lista;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getItemId()==R.id.mnBorrar){
            contactos.remove(info.position);
        }
        ad.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }
}
