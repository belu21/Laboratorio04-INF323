package com.belen.laboratorio04;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Triangulo {



    /* Coordenadas cartesianas (x, y, z) */
    private float vertices[] = new float [] {

            // Adelante
            -1, -1,  1, // 0
            1, -1,  1, // 1
            0,  1,  0, // 2

            // Abajo
            -1, -1, -1, // 3
            1, -1, -1, // 4
            1, -1,  1, // 5
            -1, -1,  1, // 6

            // Atras
            1, -1, -1, // 7
            -1, -1, -1, // 8
            0,  1,  0, // 9

            // Derecha
            1, -1,  1, // 10
            1, -1, -1, // 11
            0,  1,  0, // 12

            // Izquierda
            -1, -1, -1, // 13
            -1, -1,  1, // 14
            0,  1,  0, // 15
    };

    byte maxColor = (byte)255;

    /* Los colores x c/vÃ©rtice (r,g,b,a) */
    private byte colores[] = new byte[] {
            maxColor, 0, 0, 1, // 0
            maxColor, 0, 0, 1, // 1
            maxColor, 0, 0, 1, // 2

            0, 0, maxColor, 1, // 0
            0, 0, maxColor, 1, // 1
            0, 0, maxColor, 1, // 2
            0, 0, maxColor, 1, // 3

            0, maxColor, 0, 1, // 0
            0, maxColor, 0, 1, // 1
            0, maxColor, 0, 1, // 2

            maxColor, maxColor, 0, 1, // 0
            maxColor, maxColor, 0, 1, // 1
            maxColor, maxColor, 0, 1, // 2

            maxColor, 0, maxColor, 1, // 0
            maxColor, 0, maxColor, 1, // 1
            maxColor, 0, maxColor, 1, // 2

    };



    /* Indices */
    private short indices[] = new short [] {
            //  T1          T2
            0,  1,  2, // Frente
            3,  4,  5,  3,  5,  6, // Abajo
            7,  8,  9, // Atras
            10,  11,  12, // Derecha
            13,  14,  15, // Izquierda
    };

    private FloatBuffer bufVertices;
    private ByteBuffer bufColores;
    private ShortBuffer bufIndices;

    public Triangulo() {

        /* Lee los vÃ©rtices */
        ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
        bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden del byte nativo
        bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
        bufVertices.put(vertices);
        bufVertices.rewind(); // puntero al principio del buffer

        /* Lee los colores */
        bufColores = ByteBuffer.allocateDirect(colores.length);
        bufColores.put(colores);
        bufColores.position(0); // puntero al principio del buffer

        /* Lee los indices */
        bufByte = ByteBuffer.allocateDirect(indices.length * 2);
        bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
        bufIndices = bufByte.asShortBuffer(); // Convierte de byte a short
        bufIndices.put(indices);
        bufIndices.rewind(); // puntero al principio del buffer

    }

    public void dibuja(GL10 gl) {

        /* Se habilita el acceso al arreglo de vÃ©rtices */
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        /* Se habilita el acceso al arreglo de colores */
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        /* Se especifica los datos del arreglo de vÃ©rtices */
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufVertices);

        /* Se especifica los datos del arreglo de colores */
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, bufColores);

        /* Renderiza las primitivas desde los datos de los arreglos (vÃ©rtices,
         * colores e Ã­ndices) */
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, bufIndices);

        /* Se deshabilita el acceso al arreglo de vÃ©rtices */
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        //* Se deshabilita el acceso al arreglo de colores */
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

    }
}
