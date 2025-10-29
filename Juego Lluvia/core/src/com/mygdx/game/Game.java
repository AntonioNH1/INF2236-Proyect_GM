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

public class Game extends ApplicationAdapter {
       
    private OrthographicCamera camera;
    private SpriteBatch batch;	   
    private BitmapFont font;
	   
    private Tarro tarro;
    private Lluvia lluvia;  // ✅ sigue igual
    
    @Override
    public void create () {
        font = new BitmapFont(); // fuente por defecto
         
        // 🔊 Cargar sonidos y música
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // 🪣 Crear el tarro (se mantiene igual)
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);
        tarro.crear();

        // 🌧️ Crear la lluvia (ahora solo pasa los sonidos)
        lluvia = new Lluvia(dropSound, rainMusic);
        lluvia.crear();
      
        // 🎥 Configurar la cámara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
    }
		

    @Override
    public void render () {
        // Limpiar pantalla (fondo azul oscuro)
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // 🧮 Dibujar puntaje y vidas
        font.draw(batch, "Puntaje: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 720, 475);
			
        if (!tarro.estaHerido()) {
            tarro.actualizarMovimiento();   // movimiento del tarro
            lluvia.actualizarMovimiento(tarro);  // movimiento de gotas y colisiones
        }
			
        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);
        batch.end();	
    }
		
    @Override
    public void dispose () {
        tarro.destruir();
        lluvia.destruir();
        batch.dispose();
        font.dispose();
    }
}
