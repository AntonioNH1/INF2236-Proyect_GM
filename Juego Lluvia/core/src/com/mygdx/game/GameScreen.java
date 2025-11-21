package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input; // Importante para detectar teclas
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
    
    // Variables para el Highscore
    private Preferences prefs;
    private int highscore;
    
    // Estados del juego
    private boolean isGameOver = false;
    private boolean isPaused = false; // Nuevo estado de Pausa

    public GameScreen(final LluviaInterfazV game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Cargar Highscore
        prefs = Gdx.app.getPreferences("MiJuegoDeLluvia");
        highscore = prefs.getInteger("highscore", 0);

        // Crear objetos
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

        // --- 1. DETECTAR TECLA PAUSA (ESC) ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (!isGameOver) { // Solo pausar si estamos vivos
                isPaused = !isPaused; // Alternar (Si es true pasa a false, y viceversa)
                
                // Controlar la música
                if (isPaused) {
                    Recursos.getInstancia().musicRain.pause();
                } else {
                    Recursos.getInstancia().musicRain.play();
                }
            }
        }
        
        // --- 2. OPCIÓN SALIR AL MENÚ (Solo en Pausa) ---
        if (isPaused && Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Recursos.getInstancia().musicRain.stop();
            game.setScreen(new MainMenuScreen(game));
            dispose();
            return; // Salimos del método para no dibujar nada más
        }

        game.batch.begin();

        if (!isGameOver) {
            // --- JUEGO CORRIENDO (O PAUSADO) ---
            
            // HUD (Siempre visible)
            game.font.draw(game.batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
            game.font.draw(game.batch, "Vidas : " + tarro.getVidas(), 720, 475);
            game.font.draw(game.batch, "Highscore: " + highscore, 350, 475);
            
            // --- LÓGICA DE MOVIMIENTO ---
            // SOLO actualizamos si NO está en pausa
            if (!isPaused) {
                if (!tarro.estaHerido()) {
                    tarro.actualizarMovimiento();
                    lluvia.actualizarMovimiento(tarro);
                }
            }

            // --- DIBUJAR OBJETOS ---
            // Dibujamos SIEMPRE (incluso en pausa) para ver el juego congelado
            tarro.dibujar(game.batch);
            lluvia.actualizarDibujoLluvia(game.batch);
            
            // --- DIBUJAR TEXTO DE PAUSA ---
            if (isPaused) {
                game.font.draw(game.batch, "PAUSA", 0, 300, 800, Align.center, false);
                game.font.draw(game.batch, "Presiona ESC para Continuar", 0, 250, 800, Align.center, false);
                game.font.draw(game.batch, "Presiona M para ir al Menú", 0, 200, 800, Align.center, false);
            }

            // Verificar Game Over (Solo si no está pausado)
            if (!isPaused && tarro.getVidas() <= 0) {
                isGameOver = true;
                Recursos.getInstancia().musicRain.stop();
                
                if (tarro.getPuntos() > highscore) {
                    highscore = tarro.getPuntos();
                    prefs.putInteger("highscore", highscore);
                    prefs.flush();
                }
            }

        } else {
            // --- GAME OVER ---
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
        isPaused = false; // Asegurarnos de quitar la pausa al reiniciar
    }

    @Override
    public void dispose() {
        tarro.destruir();
        lluvia.destruir();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {
        // Opcional: Auto-pausar si el jugador minimiza la ventana
        isPaused = true;
        Recursos.getInstancia().musicRain.pause();
    }
    @Override public void resume() {}
    @Override public void hide() {}
}