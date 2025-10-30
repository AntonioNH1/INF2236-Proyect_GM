package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Tarro {
	   private Rectangle bucket;
	   private Texture bucketImage;
	   private Sound sonidoHerido;
	   private int vidas = 3;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   
	   public Tarro(Texture tex, Sound ss) {
		   bucketImage = tex;
		   sonidoHerido = ss;
	   }
	   
		public int getVidas() {
			return vidas;
		}
	
		public int getPuntos() {
			return puntos;
		}
		public Rectangle getArea() {
			return bucket;
		}
		public void sumarPuntos(int pp) {
			puntos+=pp;
            // Asegurarse de que los puntos no sean negativos (opcional)
            if (puntos < 0) puntos = 0; 
		}
		
		/**
		 * NUEVO MÉTODO para la GotaCurativa
		 */
		public void sumarVida() {
			vidas++;
		}
	
	   public void crear() {
 		  // ... (sin cambios)
		  bucket = new Rectangle();
		  bucket.x = 800 / 2 - 64 / 2;
		  bucket.y = 20;
		  bucket.width = 64;
		  bucket.height = 64;
	   }
	   
	   public void dañar() {
 		  // ... (sin cambios)
		  vidas--;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }
	   
	   public void dibujar(SpriteBatch batch) {
 		  // ... (sin cambios)
		 if (!herido)  
		   batch.draw(bucketImage, bucket.x, bucket.y);
		 else {
		   batch.draw(bucketImage, bucket.x, bucket.y+ MathUtils.random(-5,5));
		   tiempoHerido--;
		   if (tiempoHerido<=0) herido = false;
		 }
	   } 
	   
	   
	   public void actualizarMovimiento() { 
		   // ... (sin cambios)
		   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();
		   if(bucket.x < 0) bucket.x = 0;
		   if(bucket.x > 800 - 64) bucket.x = 800 - 64;
	   }
	    
	/**
	 * MODIFICADO: La clase que carga un recurso (PruebaGameLLuvia)
	 * debe ser la que lo libera. Tarro ya no es responsable de
	 * liberar 'bucketImage'.
	 */
	public void destruir() {
		    // bucketImage.dispose(); // <-- Línea eliminada
	}
	
   public boolean estaHerido() {
	   return herido;
   }
	   
}