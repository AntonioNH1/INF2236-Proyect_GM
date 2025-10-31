package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Preferences; 
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LluviaInterfazV extends ApplicationAdapter {
    private OrthographicCamera camera;
	   private SpriteBatch batch;	   
	   private BitmapFont font;
	   
	   private Tarro tarro;
	   private Lluvia lluvia;
    
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
    

    private enum GameState {
        RUNNING,
        GAME_OVER
    }
    private GameState gameState;


    private GlyphLayout glyphLayout; // para centrar texto
    private Rectangle retryButton;   // para detectar clics en el botón
    private Vector3 touchPos;        // para las coordenadas del clic
    
    private Preferences prefs; // el objeto que maneja el guardado
    private int highscore;     // highscore actual
    
	@Override
	public void create () {
		 font = new BitmapFont();
		 
         prefs = Gdx.app.getPreferences("MiJuegoDeLluvia"); 
         highscore = prefs.getInteger("highscore", 0);
         
		 
	     texTarro = new Texture(Gdx.files.internal("bucket.png"));
         texGotaBuena = new Texture(Gdx.files.internal("drop.png"));
         texGotaMala = new Texture(Gdx.files.internal("dropBad.png"));

         texGotaDorada = new Texture(Gdx.files.internal("dropGold.png"));
         texGotaCurativa = new Texture(Gdx.files.internal("dropHeal.png"));
         texGotaMalvada = new Texture(Gdx.files.internal("dropEvil.png"));
       
         texGotaVelocidad = new Texture(Gdx.files.internal("dropVelocidad.png"));
       
         texX2 = new Texture(Gdx.files.internal("x2.png"));
       
	     
         soundHurt = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
         soundDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
       
         soundGold = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
         soundHeal = Gdx.audio.newSound(Gdx.files.internal("heal.wav"));
         soundEvil = Gdx.audio.newSound(Gdx.files.internal("hurtScore.wav"));
       
   
         soundSpeed = Gdx.audio.newSound(Gdx.files.internal("soundSpeed.wav"));
         soundBonus = Gdx.audio.newSound(Gdx.files.internal("soundBonus.wav"));
      
	     musicRain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
       
      
		 tarro = new Tarro(texTarro, soundHurt);
         lluvia = new Lluvia(texGotaBuena, texGotaMala, texGotaDorada, texGotaCurativa, texGotaMalvada,
                         soundDrop, soundGold, soundHeal, soundEvil, musicRain, texGotaVelocidad, soundSpeed, texX2, soundBonus);
	      
	     camera = new OrthographicCamera();
	     camera.setToOrtho(false, 800, 480);
	     batch = new SpriteBatch();
	      
	     tarro.crear();
	     lluvia.crear();
	      
	     gameState = GameState.RUNNING; 
         glyphLayout = new GlyphLayout();
         retryButton = new Rectangle();
         touchPos = new Vector3();
	}
	
	@Override
	public void render () {
     
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		switch (gameState) {
        
        case RUNNING:
            font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
            font.draw(batch, "Vidas : " + tarro.getVidas(), 720, 475);
            font.draw(batch, "Highscore: " + highscore, 350, 475); 
            
            if (!tarro.estaHerido()) {
                tarro.actualizarMovimiento();        
                lluvia.actualizarMovimiento(tarro);	   
            }
            
            tarro.dibujar(batch);
            lluvia.actualizarDibujoLluvia(batch);

            if (tarro.getVidas() <= 0) {
                musicRain.stop(); 
                
                // guardar el highscore
                if (tarro.getPuntos() > highscore) {
                    highscore = tarro.getPuntos(); 
                    prefs.putInteger("highscore", highscore); 
                    prefs.flush(); // lo guarda en disco
                }
                
                gameState = GameState.GAME_OVER; 
            }
            break;
        
        case GAME_OVER:

            glyphLayout.setText(font, "GAME OVER");
            float goX = (800 - glyphLayout.width) / 2;
            float goY = (480 + glyphLayout.height) / 2 + 50; 
            font.draw(batch, glyphLayout, goX, goY);
            
            glyphLayout.setText(font, "Tu Puntuacion: " + tarro.getPuntos());
            float scoreX = (800 - glyphLayout.width) / 2;
            float scoreY = goY - 50; 
            font.draw(batch, glyphLayout, scoreX, scoreY);

            glyphLayout.setText(font, "Highscore: " + highscore);
            float hsX = (800 - glyphLayout.width) / 2;
            float hsY = scoreY - 30; 
            font.draw(batch, glyphLayout, hsX, hsY);

            glyphLayout.setText(font, "Reintentar");
            float retryX = (800 - glyphLayout.width) / 2;
            float retryY = hsY - 50; 
            font.draw(batch, glyphLayout, retryX, retryY);


            retryButton.set(retryX, retryY - glyphLayout.height, glyphLayout.width, glyphLayout.height);


            if (Gdx.input.isTouched()) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);

                if (retryButton.contains(touchPos.x, touchPos.y)) {
                    resetGame(); 
                }
            }
            break;
    }
		
		batch.end();	
	}
	
	
	public void resetGame() {
        tarro.reset();   
        lluvia.reset();   
        musicRain.play();
        gameState = GameState.RUNNING;
    }
	
	@Override
	public void dispose () {
		
	   tarro.destruir(); 
       lluvia.destruir(); 
	      
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
