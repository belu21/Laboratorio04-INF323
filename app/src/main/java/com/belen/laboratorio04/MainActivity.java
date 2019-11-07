package com.belen.laboratorio04;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView superficie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Ventana sin tÃ­tulo */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* Se establece las banderas de la ventana de esta Actividad */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* Se crea el objeto Renderiza */
        superficie = new Renderiza(this);
        /*
         * Activity <- GLSurfaceView : Coloca la Vista de la Superficie del
         * OpenGL como un Contexto de Ã©sta Actividad.
         */
        setContentView(superficie);
// setContentView(R.layout.activity_main);
    }
    /**
     * Recuerda que debe reanudar superficie
     */
    @Override
    protected void onResume() {
        super.onResume();
        superficie.onResume();
    }
    /**
     * TambiÃ©n pausa la superficie
     */
    @Override
    protected void onPause() {
        super.onPause();
        superficie.onPause();
    }

}
