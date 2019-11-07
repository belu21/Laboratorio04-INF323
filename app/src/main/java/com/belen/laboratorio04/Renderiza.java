package com.belen.laboratorio04;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;
public class Renderiza extends GLSurfaceView implements Renderer {
    /* Objeto */
    Objeto obj;
    Objeto obj2;
    Objeto obj3;
    Objeto obj4;
    Objeto obj5;



    /* Ancho y alto de la ventana */
    private int ancho, alto;
    /* Para la rotación */
    private ArcBall arcBall = new ArcBall(640.0f, 480.0f);
    private float[] MatrizRotacion = new float[16];
    private float[] B = new float[16];

    Context contexto;
    public Renderiza(Context contexto) {
        super(contexto);

        this.contexto=contexto;
        /* Inicia el renderizado */
        this.setRenderer(this);
        /* La ventana solicita recibir una entrada */
        this.requestFocus();
        /* Establece que la ventana detecte el modo táctil */
        this.setFocusableInTouchMode(true);
        /* Se renderizará al inicio o cuando se llame a requestRender() */
        this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {

        obj = new Objeto(contexto, "10438_Circular_Grass_Patch_v1_iterations-2.obj");
        obj2 = new Objeto(contexto, "lowpolytree.obj");
        obj3 = new Objeto(contexto, "farmhouse_obj.obj");
        obj4 = new Objeto(contexto, "porsche.obj");
        obj5 = new Objeto(contexto, "f-16.obj");


        /* B = I */
   //     Matriz4.identidad(B);
        /* Deshabilita dithering, no se limita la paleta de colores */
   //     gl.glDisable(GL10.GL_DITHER);
        /* Habilita el modo de sombreado plano */
   //     gl.glShadeModel(GL10.GL_FLAT);
        /* Habilita el ocultamiento de superficies */
   //     gl.glEnable(GL10.GL_DEPTH_TEST);
        /* Limpia el buffer de profundidad con el valor de 1.0 */
   //     gl.glClearDepthf(1.0f);
        /* Acepta si valor Z de entrada es igual al valor Z del buffer de profundidad */
   //     gl.glDepthFunc(GL10.GL_LEQUAL);
        /* Color de fondo */
        //    gl.glClearColor(0, 0, 0, 0);

        /* Definici�n del color de la luz - Valores por defecto */
        float [] luz_ambiente = { 0, 0, 0, 1}; // I
        float [] luz_difusa = { 1, 1, 1, 1 };
        float [] luz_especular = { 1, 1, 1, 1 };
        float [] luz_posicion = { 0, 0, 1, 0}; // L

        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, luz_ambiente, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, luz_difusa, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, luz_especular, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, luz_posicion, 0);

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glEnable(GL10.GL_NORMALIZE);
        gl.glClearColor(153/255f, 217/255f, 234/256f, 0);
    }



    @Override
    public void onDrawFrame(GL10 gl) {
        /* Incializa el buffer de color y de profundidad */
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        /* Inicializa la Matriz del Modelo-Vista */
        gl.glLoadIdentity(); // MVM = I
        gl.glMultMatrixf(MatrizRotacion, 0); // MVM = MVM * MatrizRotacion

        gl.glPushMatrix();
        gl.glPushMatrix();
        gl.glScalef(2,2,2);
        gl.glRotatef(270,1,0,0);
        obj.dibuja(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(-1,-1,0);
        obj2.dibuja(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(0,-1,-1);
        obj2.dibuja(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(1.5f,-0.6f,0);
        gl.glScalef(1.3f,1.3f,1.3f);
        obj3.dibuja(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(0,-1,0);
        obj4.dibuja(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(0,3,0);
        obj5.dibuja(gl);
        gl.glPopMatrix();



        gl.glPopMatrix();
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        ancho = w;
        alto = h;
        /* Ventana de despliegue */
        gl.glViewport(0, 0, ancho, alto);
        /* Matriz de Proyección */
        gl.glMatrixMode(GL10.GL_PROJECTION);
        /* Inicializa la Matriz de Proyección */
        gl.glLoadIdentity();
        /* Proyección paralela */
        if (w <= h)
            gl.glOrthof(-2, 2, -2*(float)h/(float)w, 2*(float) h / (float) w, -10, 10);
        else
            gl.glOrthof(-2*(float)w/(float)h, 2*(float) w / (float) h, -2, 2, -10, 10);
        /* Matriz del Modelo-Vista */
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        /* Inicializa la Matriz del Modelo-Vista */
        gl.glLoadIdentity();
        Matriz4.identidad(MatrizRotacion);
        /* Ajusta el ancho a [-1..1] y el alto a [-1..1] */
        arcBall.ajusta(ancho, alto);
    }
    /**
     * Maneja los eventos del movimiento en la pantalla táctil.
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /* B = MatrizRotacion */
                Matriz4.copia(B, MatrizRotacion);
                arcBall.primerPunto(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                /* Actualiza el segundo vector y obtiene el cuaternión */
                Cuaternion q = arcBall.segundoPunto(x, y);
                /* Convierte el cuaternión a una matriz de rotación */
                Cuaternion.rota(MatrizRotacion, q);
                /* MatrizRotacion = MatrizRotacion * B */
                Matriz4.multiplica(MatrizRotacion, MatrizRotacion, B);
                requestRender();
        }
        return true;
    }
}


