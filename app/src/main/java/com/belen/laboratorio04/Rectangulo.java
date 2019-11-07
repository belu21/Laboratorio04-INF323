package com.belen.laboratorio04;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
public class Rectangulo {
	/**
	 *     2
	 *     /\
	 *    /  \
	 *   /    \
	 *  /      \
	 * /________\
	 * 0 		1
	 */
	/* Coordenadas cartesianas (x, y) */
	private float vertices[] = new float [] {
		-10,-1f,10, // 0
		10,-1f,10, // 1
		10,-1f,-10, // 2
		-10,-1f,-10,
	};
	byte maxColor = (byte)255;
	private byte colores[] = new byte[]{
		


		maxColor,maxColor,maxColor,maxColor,
		maxColor,maxColor,maxColor,maxColor,
		maxColor,maxColor,maxColor,maxColor,
		maxColor,maxColor,maxColor,maxColor,
		
		
	};
	
	FloatBuffer bufVertices;
	ByteBuffer bufColores;
	public Rectangulo() {
		/* Lee los v�rtices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden del byte nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufVertices.put(vertices);
		bufVertices.rewind(); // puntero al principio del buffer
		
		/*read colors*/
		bufColores = ByteBuffer.allocateDirect(colores.length);
		bufColores.put(colores);
		bufColores.position(0);
	}
	public void dibuja(GL10 gl) {
		/* Se activa el arreglo de v�rtices */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		/*colors*/
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		/* Se especifica los datos del arreglo de v�rtices */
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufVertices);//3 para 3d en z
		/*Se especifica los datos del arreglo de colores*/
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, bufColores);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
