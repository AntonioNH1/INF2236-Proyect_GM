package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator; // Importante usar el iterador

public class Lluvia {
    

	private Array<ObjetoCaida> objetosCaida; 
    private long lastDropTime;
    

    private Texture texGotaBuena;
    private Texture texGotaMala;
    private Texture texGotaDorada;
    private Texture texGotaCurativa;
    private Texture texGotaMalvada;
    private Texture texGotaVelocidad;
    private Texture texBonificacionX2;
    
    private Sound soundGotaBuena;
    private Sound soundGotaDorada;
    private Sound soundGotaCurativa;
    private Sound soundGotaMalvada;
    private Sound soundSpeed;
    private Sound soundBonus;
    
    private Music rainMusic;
	   

	public Lluvia(Texture texGotaBuena, Texture texGotaMala, Texture texGotaDorada, 
                  Texture texGotaCurativa, Texture texGotaMalvada, Sound soundGotaBuena, 
                  Sound soundGotaDorada, Sound soundGotaCurativa, Sound soundGotaMalvada, 
                  Music rainMusic, Texture texGotaVelocidad, Sound soundSpeed, Texture texBonificacionX2, Sound soundBonus) {
        
        // Texturas
		this.texGotaBuena = texGotaBuena;
		this.texGotaMala = texGotaMala;
        this.texGotaDorada = texGotaDorada;
        this.texGotaCurativa = texGotaCurativa;
        this.texGotaMalvada = texGotaMalvada;
        this.texGotaVelocidad = texGotaVelocidad;
        this.texBonificacionX2 = texBonificacionX2;
        
        // Sonidos
		this.soundGotaBuena = soundGotaBuena;
        this.soundGotaDorada = soundGotaDorada;
        this.soundGotaCurativa = soundGotaCurativa;
        this.soundGotaMalvada = soundGotaMalvada;
        this.soundSpeed = soundSpeed;
        this.soundBonus = soundBonus;

        this.rainMusic = rainMusic;
	}
	
	public void crear() {

		objetosCaida = new Array<ObjetoCaida>(); 
		crearObjetoCaida(); 
	      
	    rainMusic.setLooping(true);
	    rainMusic.play();
	}
	

	private void crearObjetoCaida() {
	      float x = MathUtils.random(0, 800-64);
	      float y = 480;
	      
	      ObjetoCaida nuevoObjeto;
          int tipo = MathUtils.random(1, 100); 

          if (tipo <= 30) {
              nuevoObjeto = new GotaMala(texGotaMala, x, y);
          } 
          else if (tipo <= 42) { 
              nuevoObjeto = new GotaMalvada(texGotaMalvada, soundGotaMalvada, x, y);
          } 
          else if (tipo <= 50) {
              nuevoObjeto = new GotaCurativa(texGotaCurativa, soundGotaCurativa, x, y);
          } 
          else if (tipo <= 55) { 
              nuevoObjeto = new GotaDorada(texGotaDorada, soundGotaDorada, x, y);
          } 
          else if (tipo <= 58) {
              nuevoObjeto = new GotaVelocidad(texGotaVelocidad, soundSpeed, x, y);
          }
          else if (tipo <= 60) { 
        	  nuevoObjeto = new GotaX2(texBonificacionX2, soundBonus, x, y);
          }
          else { 
              nuevoObjeto = new GotaBuena(texGotaBuena, soundGotaBuena, x, y);
          }

	      
	      objetosCaida.add(nuevoObjeto);
	      lastDropTime = TimeUtils.nanoTime();
	   }
	

   public void actualizarMovimiento(Tarro tarro) { 
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearObjetoCaida();
	  
	   Iterator<ObjetoCaida> iter = objetosCaida.iterator();
	   while (iter.hasNext()) {
		  ObjetoCaida objeto = iter.next();
          
          // 1. Mover el objeto
		  objeto.actualizarPosicion(Gdx.graphics.getDeltaTime());
	      
          // 2. Revisar si cae al suelo
	      if(objeto.getArea().y + 64 < 0) {
	    	  iter.remove(); 
	    	  continue; 
	      }
          
          // 3. Revisar si colisiona con el tarro
	      if(objeto.getArea().overlaps(tarro.getArea())) {
              

	    	  objeto.procesarColision(tarro);
	    	  
	    	  iter.remove(); // 
	      }
	   }   
	   tarro.actualizarBonificaciones();
   }
   

   public void actualizarDibujoLluvia(SpriteBatch batch) { 
	  // Cada objeto sabe cómo dibujarse a sí mismo (LSP)
	  for (ObjetoCaida objeto : objetosCaida) {
		  objeto.dibujar(batch);
	  }
   }
   

   public void destruir() {

   }
   
   public void reset() {
       if (objetosCaida != null) {
           objetosCaida.clear(); 
       }
       crearObjetoCaida(); 
   }
}