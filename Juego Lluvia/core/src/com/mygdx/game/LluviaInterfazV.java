package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences; 
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class LluviaInterfazV extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;	   
    private BitmapFont font;
    
    private Tarro tarro;
    private Lluvia lluvia;

    private enum GameState {
        RUNNING,
        GAME_OVER
    }
    private GameState gameState;

    private GlyphLayout glyphLayout; 
    private Rectangle retryButton;   
    private Vector3 touchPos;        
    
    private Preferences prefs; 
    private int highscore;     
    
    @Override
    public void create () {
        font = new BitmapFont();
         
        prefs = Gdx.app.getPreferences("MiJuegoDeLluvia"); 
        highscore = prefs.getInteger("highscore", 0);
        
        Recursos.getInstancia(); 
        
        tarro = new Tarro(Recursos.getInstancia().texTarro, Recursos.getInstancia().soundHurt);
        
        lluvia = new Lluvia();
          
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
                Recursos.getInstancia().musicRain.stop();
                
                if (tarro.getPuntos() > highscore) {
                    highscore = tarro.getPuntos(); 
                    prefs.putInteger("highscore", highscore); 
                    prefs.flush(); 
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
        gameState = GameState.RUNNING;
    }
    
    @Override
    public void dispose () {
       tarro.destruir(); 
       lluvia.destruir(); 
          
       Recursos.getInstancia().dispose();
       
       batch.dispose();
       font.dispose();
    }
}