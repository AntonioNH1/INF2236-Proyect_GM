package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final LluviaInterfazV game;
    
    private OrthographicCamera camera;
    private Tarro tarro;
    private Lluvia lluvia;
    
    private Preferences prefs;
    private int highscore;
    
    private boolean isGameOver = false;

    public GameScreen(final LluviaInterfazV game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        prefs = Gdx.app.getPreferences("MiJuegoDeLluvia");
        highscore = prefs.getInteger("highscore", 0);

        tarro = new Tarro(Recursos.getInstancia().texTarro, Recursos.getInstancia().soundHurt);
        tarro.crear();
        
        lluvia = new Lluvia();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        if (!isGameOver) {
            game.font.draw(game.batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
            game.font.draw(game.batch, "Vidas : " + tarro.getVidas(), 720, 475);
            
            game.font.draw(game.batch, "Highscore: " + highscore, 350, 475);
            
            if (!tarro.estaHerido()) {
                tarro.actualizarMovimiento();
                lluvia.actualizarMovimiento(tarro);
            }

            tarro.dibujar(game.batch);
            lluvia.actualizarDibujoLluvia(game.batch);

            if (tarro.getVidas() <= 0) {
                isGameOver = true;
                Recursos.getInstancia().musicRain.stop();
                
                if (tarro.getPuntos() > highscore) {
                    highscore = tarro.getPuntos();
                    prefs.putInteger("highscore", highscore);
                    prefs.flush();
                }
            }

        } else {
            game.font.draw(game.batch, "GAME OVER", 0, 300, 800, Align.center, false);
            game.font.draw(game.batch, "Puntaje Final: " + tarro.getPuntos(), 0, 250, 800, Align.center, false);
            game.font.draw(game.batch, "Highscore: " + highscore, 0, 200, 800, Align.center, false);
            game.font.draw(game.batch, "Toca para reiniciar", 0, 100, 800, Align.center, false);

            if (Gdx.input.isTouched()) {
                reiniciarJuego();
            }
        }

        game.batch.end();
    }

    private void reiniciarJuego() {
        tarro.reset();
        lluvia.reset();
        Recursos.getInstancia().musicRain.play();
        highscore = prefs.getInteger("highscore", 0);
        isGameOver = false;
    }

    @Override
    public void dispose() {
        tarro.destruir();
        lluvia.destruir();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}