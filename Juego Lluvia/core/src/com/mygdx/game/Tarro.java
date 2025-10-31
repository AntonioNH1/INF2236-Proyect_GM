package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

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
	   private float velocidadBase = 400;
	   private float multiplicadorVelocidad = 1f;
	   private long tiempoBoost = 0;
	   private float multiplicadorPuntos = 1.0f;
	   private long tiempoBonus = 0;
	   
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
	   public void sumarPuntos(int puntos) {
		    this.puntos += puntos * multiplicadorPuntos;
	   }

	   public void activarMultiplicador(float factor, int duracionSegundos) {
		    this.multiplicadorPuntos = factor;
		    this.tiempoBonus = System.currentTimeMillis() + (duracionSegundos * 1000);
	   }

		public void actualizarBonificaciones() {
		    if (multiplicadorPuntos > 1.0f && System.currentTimeMillis() > tiempoBonus) {
		        multiplicadorPuntos = 1.0f;
		    }
		}
		
		public void sumarVida() {
			if (this.vidas < 3) {
                this.vidas++;
            }
		}
	
	   public void crear() {
		  bucket = new Rectangle();
		  bucket.x = 800 / 2 - 64 / 2;
		  bucket.y = 20;
		  bucket.width = 64;
		  bucket.height = 64;
		  
	   }
	   
	   
	   public void restarVidas(int cantidad) {
		   vidas -= cantidad;
		   herido = true;
		   tiempoHerido = tiempoHeridoMax;
		   sonidoHerido.play(); 
	   }
	   
	   public void dibujar(SpriteBatch batch) {

		 if (!herido)  
		   batch.draw(bucketImage, bucket.x, bucket.y);
		 else {
		   batch.draw(bucketImage, bucket.x, bucket.y+ MathUtils.random(-5,5));
		   tiempoHerido--;
		   if (tiempoHerido<=0) herido = false;
		 }
	   } 
	   
	   public void aumentarVelocidadTemporal(float factor, int segundos) {
		    multiplicadorVelocidad = factor;
		    tiempoBoost = TimeUtils.millis() + (segundos * 1000);
		}
	   
	   private void revisarBoost() {
		    if (tiempoBoost > 0 && TimeUtils.millis() > tiempoBoost) {
		        multiplicadorVelocidad = 1f;
		        tiempoBoost = 0;
		    }
		}
	   
	   public void actualizarMovimiento() {
	       revisarBoost();
	       float velxActual = velocidadBase * multiplicadorVelocidad;

	       if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
	           bucket.x -= velxActual * Gdx.graphics.getDeltaTime();
	       if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
	           bucket.x += velxActual * Gdx.graphics.getDeltaTime();

	       if (bucket.x < 0) bucket.x = 0;
	       if (bucket.x > 800 - bucket.width) bucket.x = 800 - bucket.width;
	   }
	   
	   
	   public void reset() {
	        this.vidas = 3;
	        this.puntos = 0;
	        this.herido = false;
	        this.tiempoHerido = 0;
	        this.multiplicadorVelocidad = 1f;
	        this.tiempoBoost = 0;
	        this.multiplicadorPuntos = 1.0f;
	        this.tiempoBonus = 0;

	        bucket.x = 800 / 2 - 64 / 2;
			bucket.y = 20;
	    }
	   
	    

	public void destruir() {
		  
	}
	
   public boolean estaHerido() {
	   return herido;
   }
	   
}