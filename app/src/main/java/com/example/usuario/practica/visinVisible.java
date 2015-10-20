package com.example.usuario.practica;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.practica.datos.Contacto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 18/10/2015.
 */
public class visinVisible implements View.OnClickListener {

    int position;
    TextView tv;
    Context contexto;
    ImageView imagen;

    public visinVisible(int Position, TextView tv, Context Contexto, ImageView img){
        this.position=Position;
        this.tv=tv;
        this.contexto=Contexto;
        this.imagen=img;
    }

    @Override
    public void onClick(View v) {
        if(tv.getVisibility()==View.VISIBLE){
            tv.setVisibility(View.GONE);
            imagen.setImageResource(R.drawable.icon_more);
        }else{
            tv.setVisibility(View.VISIBLE);
            imagen.setImageResource(R.drawable.icon_less);
        }
    }
}
