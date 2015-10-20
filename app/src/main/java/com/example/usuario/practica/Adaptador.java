package com.example.usuario.practica;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.practica.datos.Contacto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

public class Adaptador extends ArrayAdapter<Contacto>{

    Context cx;
    private int res;
    private LayoutInflater lInflator;
    private List<Contacto> nombres;

    public class ViewHolder{
        public TextView tv1, tv2,tv3;
        public ImageView more;
    }

    public Adaptador(Context context, int resource, List<Contacto> nombres) {
        super(context, resource, nombres);
        this.cx=context;
        this.res= resource;
        this.nombres=nombres;
        this.lInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent){
        ViewHolder gv = new ViewHolder();
        if(convertView==null){
            convertView = lInflator.inflate(res,null);
            TextView tv = (TextView)convertView.findViewById(R.id.tv1);
            gv.tv1=tv;
            TextView tv2 = (TextView)convertView.findViewById(R.id.tv2);
            gv.tv2=tv2;
            TextView tv3 = (TextView)convertView.findViewById(R.id.tvTelefonos);
            gv.tv3=tv3;
            ImageView img = (ImageView)convertView.findViewById(R.id.imageView2);
            gv.more=img;
        }

        TextView tv = (TextView)convertView.findViewById(R.id.tv1);
        tv.setText(nombres.get(posicion).getNombre());

        tv = (TextView)convertView.findViewById(R.id.tv2);
        tv.setText("" + nombres.get(posicion).getTelefono(0));

        ImageView img = (ImageView)convertView.findViewById(R.id.imageView2);

        if(nombres.get(posicion).size()>1){
            img.setVisibility(View.VISIBLE);
        }

        tv=(TextView)convertView.findViewById(R.id.tvTelefonos);
        tv.setText("");
        int cont=1;
        boolean esprimero=true;
        while(nombres.get(posicion).size()>cont){
            if(esprimero==true) {
                tv.setText(""+nombres.get(posicion).getTelefono(cont));
                esprimero=false;
            }else{
                tv.setText(tv.getText().toString() + "\n" + nombres.get(posicion).getTelefono(cont));
            }
            cont++;
        }

        img.setOnClickListener
                (
                        new visinVisible
                                (posicion,tv,cx, img)
                );

        return convertView;
    }



}
