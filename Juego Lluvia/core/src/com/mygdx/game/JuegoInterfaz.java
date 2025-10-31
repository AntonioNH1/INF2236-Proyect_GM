package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class JuegoInterfaz extends ApplicationAdapter {
    private OrthographicCamera camera;
	   private SpriteBatch batch;	   
	   private BitmapFont font;
	   
	   private Tarro tarro;
	   private Lluvia lluvia;
    
    // MODIFICADO: Almacenamos todos los assets aquí
    private Texture texTarro;
    private Texture texGotaBuena;
    private Texture texGotaMala;
    private Texture texGotaDorada;
    private Texture texGotaCurativa;
    private Texture texGotaMalvada;
    private Texture texGotaVelocidad;
    private Texture texX2;
    
    private Sound soundHurt;
    private Sound soundDrop;
    private Sound soundGold;
    private Sound soundHeal;
    private Sound soundEvil;
    private Sound soundSpeed;
    private Sound soundBonus;
    
    private Music musicRain;
    
	@Override
	public void create () {
		 font = new BitmapFont();
		 
		  // Cargar todas las texturas
		  texTarro = new Texture(Gdx.files.internal("bucket.png"));
       texGotaBuena = new Texture(Gdx.files.internal("drop.png"));
       texGotaMala = new Texture(Gdx.files.internal("dropBad.png"));
       // Asegúrate de tener estas imágenes en tu carpeta 'assets' (o 'internal')
       texGotaDorada = new Texture(Gdx.files.internal("dropGold.png"));
       texGotaCurativa = new Texture(Gdx.files.internal("dropHeal.png"));
       texGotaMalvada = new Texture(Gdx.files.internal("dropEvil.png"));
       
       // 🔹 NUEVO: textura de la gota velocidad
       texGotaVelocidad = new Texture(Gdx.files.internal("dropVelocidad.png"));
       // asegúrate de tener "dropSpeed.png" en la carpeta assets
       texX2 = new Texture(Gdx.files.internal("x2.png"));
       
	      // Cargar todos los sonidos
       soundHurt = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
       soundDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
       // Asegúrate de tener estos sonidos
       soundGold = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
       soundHeal = Gdx.audio.newSound(Gdx.files.internal("heal.wav"));
       soundEvil = Gdx.audio.newSound(Gdx.files.internal("hurtScore.wav"));
       
    // 🔹 NUEVO: sonido exclusivo para la gota velocidad
       soundSpeed = Gdx.audio.newSound(Gdx.files.internal("soundSpeed.wav"));
       soundBonus = Gdx.audio.newSound(Gdx.files.internal("speed.mp3"));
      
	      musicRain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
       
       // Creación de objetos e inyección de dependencias
		  tarro = new Tarro(texTarro, soundHurt);
       lluvia = new Lluvia(texGotaBuena, texGotaMala, texGotaDorada, texGotaCurativa, texGotaMalvada,
                         soundDrop, soundGold, soundHeal, soundEvil, musicRain, texGotaVelocidad, soundSpeed, texX2, soundBonus);
	      
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 480);
	      batch = new SpriteBatch();
	      
	      tarro.crear();
	      lluvia.crear();
	}
	
	@Override
	public void render () {
		// --- NINGÚN CAMBIO ES NECESARIO AQUÍ ---
     // La lógica de renderizado sigue siendo la misma gracias
     // a la correcta abstracción.
     
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + tarro.getVidas(), 720, 475);
		
		if (!tarro.estaHerido()) {
	        tarro.actualizarMovimiento();        
	        lluvia.actualizarMovimiento(tarro);	   
		}
		
		tarro.dibujar(batch);
		lluvia.actualizarDibujoLluvia(batch);
		
		batch.end();	
	}
	
	@Override
	public void dispose () {
       // MODIFICADO: Esta clase ahora libera todos los assets que cargó
	      tarro.destruir(); // (ahora vacío)
       lluvia.destruir(); // (ahora vacío)
	      
       // Texturas
       texTarro.dispose();
       texGotaBuena.dispose();
       texGotaMala.dispose();
       texGotaDorada.dispose();
       texGotaCurativa.dispose();
       texGotaMalvada.dispose();
       texGotaVelocidad.dispose();
       texX2.dispose();
       
       // Sonidos
       soundHurt.dispose();
       soundDrop.dispose();
       soundGold.dispose();
       soundHeal.dispose();
       soundEvil.dispose();
       soundSpeed.dispose();
       soundBonus.dispose();
       
       // Música
       musicRain.dispose();
       
       // Otros
	      batch.dispose();
	      font.dispose();
	}
	}