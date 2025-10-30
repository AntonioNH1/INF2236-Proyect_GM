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
    
    // MODIFICADO: Un solo Array que usa Polimorfismo (LSP)
	private Array<ObjetoCaida> objetosCaida; 
    private long lastDropTime;
    
    // Almacenamos todas las "plantillas" (texturas y sonidos)
    private Texture texGotaBuena;
    private Texture texGotaMala;
    private Texture texGotaDorada;
    private Texture texGotaCurativa;
    private Texture texGotaMalvada;
    private Texture texGotaVelocidad;
    
    private Sound soundGotaBuena;
    private Sound soundGotaDorada;
    private Sound soundGotaCurativa;
    private Sound soundGotaMalvada;
    private Sound soundSpeed;
    
    private Music rainMusic;
	   
    /**
     * MODIFICADO: El constructor ahora recibe todos los assets necesarios
     * para actuar como una "Fábrica" de objetos.
     */
	public Lluvia(Texture texGotaBuena, Texture texGotaMala, Texture texGotaDorada, 
                  Texture texGotaCurativa, Texture texGotaMalvada, Sound soundGotaBuena, 
                  Sound soundGotaDorada, Sound soundGotaCurativa, Sound soundGotaMalvada, 
                  Music rainMusic, Texture texGotaVelocidad, Sound soundSpeed) {
        
        // Texturas
		this.texGotaBuena = texGotaBuena;
		this.texGotaMala = texGotaMala;
        this.texGotaDorada = texGotaDorada;
        this.texGotaCurativa = texGotaCurativa;
        this.texGotaMalvada = texGotaMalvada;
        this.texGotaVelocidad = texGotaVelocidad;
        
        // Sonidos
		this.soundGotaBuena = soundGotaBuena;
        this.soundGotaDorada = soundGotaDorada;
        this.soundGotaCurativa = soundGotaCurativa;
        this.soundGotaMalvada = soundGotaMalvada;
        this.soundSpeed = soundSpeed;

        this.rainMusic = rainMusic;
	}
	
	public void crear() {
        // MODIFICADO: Inicializa el nuevo Array
		objetosCaida = new Array<ObjetoCaida>(); 
		crearObjetoCaida(); // Renombrado de crearGotaDeLluvia
	      
	    rainMusic.setLooping(true);
	    rainMusic.play();
	}
	
    /**
     * MODIFICADO: Este método ahora actúa como un "Factory Method"
     * Decide qué tipo de ObjetoCaida instanciar.
     */
	private void crearObjetoCaida() {
	      float x = MathUtils.random(0, 800-64);
	      float y = 480;
	      
	      ObjetoCaida nuevoObjeto;
          int tipo = MathUtils.random(1, 100); // Probabilidad sobre 100

          if (tipo <= 15) { // 15% Gota Mala (-1 vida)
              nuevoObjeto = new GotaMala(texGotaMala, x, y);
          } 
          else if (tipo <= 22) { // 7% Gota Malvada (-100 pts)
              nuevoObjeto = new GotaMalvada(texGotaMalvada, soundGotaMalvada, x, y);
          } 
          else if (tipo <= 30) { // 8% Gota Curativa (+1 vida)
              nuevoObjeto = new GotaCurativa(texGotaCurativa, soundGotaCurativa, x, y);
          } 
          else if (tipo <= 35) { // 5% Gota Dorada (+50 pts)
              nuevoObjeto = new GotaDorada(texGotaDorada, soundGotaDorada, x, y);
          } 
          else if (tipo <= 38) { // 3% Gota Velocidad (usa interfaz EfectoEspecial)
              nuevoObjeto = new GotaVelocidad(texGotaVelocidad, soundSpeed, x, y);
          } 
          else { // 62% Gota Buena (+10 pts)
              nuevoObjeto = new GotaBuena(texGotaBuena, soundGotaBuena, x, y);
          }

	      
	      objetosCaida.add(nuevoObjeto);
	      lastDropTime = TimeUtils.nanoTime();
	   }
	
   /**
    * MODIFICADO: Lógica de actualización y colisión refactorizada.
    * Ya no usa 'if-else' para los tipos.
    */
   public void actualizarMovimiento(Tarro tarro) { 
	   // generar gotas
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearObjetoCaida();
	  
	   // Usamos un Iterador para poder eliminar objetos de forma segura
       // mientras recorremos la lista.
	   Iterator<ObjetoCaida> iter = objetosCaida.iterator();
	   while (iter.hasNext()) {
		  ObjetoCaida objeto = iter.next();
          
          // 1. Mover el objeto
		  objeto.actualizarPosicion(Gdx.graphics.getDeltaTime());
	      
          // 2. Revisar si cae al suelo
	      if(objeto.getArea().y + 64 < 0) {
	    	  iter.remove(); // Eliminar de la lista
	    	  continue; // Saltar a la siguiente iteración
	      }
          
          // 3. Revisar si colisiona con el tarro
	      if(objeto.getArea().overlaps(tarro.getArea())) {
              
              // --- ¡AQUÍ ESTÁ LA MAGIA! (OCP, LSP, DIP, Template Method) ---
              // Lluvia (alto nivel) le dice al objeto (bajo nivel)
              // que procese su propia colisión. No le importa de qué tipo es.
	    	  objeto.procesarColision(tarro);
	    	  
	    	  iter.remove(); // Eliminar de la lista
	      }
	   }   
   }
   
   /**
    * MODIFICADO: Lógica de dibujo refactorizada.
    * Ya no usa 'if-else' para dibujar.
    */
   public void actualizarDibujoLluvia(SpriteBatch batch) { 
	  // Cada objeto sabe cómo dibujarse a sí mismo (LSP)
	  for (ObjetoCaida objeto : objetosCaida) {
		  objeto.dibujar(batch);
	  }
   }
   
   /**
    * MODIFICADO: La clase principal (PruebaGameLLuvia)
    * se encargará de liberar los sonidos y la música.
    */
   public void destruir() {
	      // dropSound.dispose(); // <-- Línea eliminada
	      // rainMusic.dispose(); // <-- Línea eliminada
   }
   
   public void reiniciar() {
	    objetosCaida.clear(); // Limpia todas las gotas actuales
	}

}