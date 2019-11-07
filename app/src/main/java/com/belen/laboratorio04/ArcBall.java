package com.belen.laboratorio04;

/**
 * ArcBall
 * Obtiene los vectores U y V de la esfera (x^2 + y^2 + z^2 = 1).
 *
 *
 */
public class ArcBall {
    private static final float Epsilon = 1.0e-5f;
    Vector3 U;
    Vector3 V;
    float ajustaAncho, ajustaAlto;
    class Punto2f {
        public float x, y;
        public Punto2f(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
    public ArcBall(float w, float h) {
        U = new Vector3();
        V = new Vector3();
        ajusta(w, h);
    }
    /* Ajusta el ancho y alto de la ventana */
    public void ajusta(float w, float h) {
        if (!((w > 1.0f) && (h > 1.0f)))
            System.out.println("ERROR");
        /* Ajusta el factor para el ancho y alto (2 = [-1..1]) */
        ajustaAncho = 2.0f / (w - 1.0f);
        ajustaAlto = 2.0f / (h - 1.0f);
    }
    /* Obtiene el vector dado un punto (x,y) */
    public void obtieneVector(Vector3 vector, float x, float y) {
        /* Copia punto */
        Punto2f temp = new Punto2f(x, y);
        /* Ajusta las coordenadas del punto al rango [-1..1] */
        temp.x = (temp.x * ajustaAncho) - 1.0f;
        temp.y = 1.0f - (temp.y * ajustaAlto);
        /* Calcula el cuadrado de la longitud del vector */
        float longitud2 = (temp.x * temp.x) + (temp.y * temp.y);
        /*
         * Considerando que: radio^2 = x^2 + y^2 + z^2
         * ¿Cuales son los valores de x, y y z?
         *
         * Si el punto está fuera de la esfera... (longitud2 > 1)
         */
        if (longitud2 > 1.0f) {
            /* Calcula un factor de normalización (radio / sqrt(longitud2)) */
            float norma = (float) (1.0 / Math.sqrt(longitud2));
            /* Retorna el vector "normalizado", un punto sobre la esfera */
            vector.x = temp.x * norma;
            vector.y = temp.y * norma;
            vector.z = 0.0f;
        } else { /* e.o.c. está dentro */
            /*
             * Retorna un vector, un punto dentro la esfera
             * z = sqrt(radio^cuadrado - (x^2 + y^2))
             */
            vector.x = temp.x;
            vector.y = temp.y;
            vector.z = (float) Math.sqrt(1.0f - longitud2);
        }
    }
    /* Obtiene el vector U */
    public void primerPunto(float x, float y) {
        obtieneVector(U, x, y);
    }
    /* Obtiene el Cuaternion de U y V */
    public Cuaternion segundoPunto(float x, float y) {
        Cuaternion q = new Cuaternion();
        /* Obtiene el vector V */
        obtieneVector(V, x, y);
        /* Retorna el cuaternión equivalente a la rotación. */
        if (q != null) {
            /* Calcula la Normal = U x V */
            Vector3 Normal = U.producto_vectorial(V);
            /* Calcula la longitud de la normal */
            if (Normal.longitud() > Epsilon) { /* si no es cero */
                q.x = Normal.x;
                q.y = Normal.y;
                q.z = Normal.z;
                /* w = (theta / 2), donde theta es el ángulo de rotación */
                q.w = U.producto_escalar(V);
            } else { /* si es cero */
                /* U y V coinciden */
                q.x = q.y = q.z = q.w = 0.0f;
            }
        }
        return q;
    }
}

