package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class ControlReinicio {

    private boolean gameOver;
    private float temporizador;
    private GameLluvia juego;

    public ControlReinicio(GameLluvia juego) {
        this.juego = juego;
        this.gameOver = false;
        this.temporizador = 0;
    }

    public void actualizar(Tarro tarro, Lluvia lluvia) {
        // Detectar Game Over una sola vez
        if (!gameOver && tarro.getVidas() <= 0) {
            System.out.println(">>> GAME OVER DETECTADO <<<");
            gameOver = true;
            temporizador = 0;
        }

        // Si está en Game Over
        if (gameOver) {
            temporizador += Gdx.graphics.getDeltaTime();

            // Dibujar texto de pausa
            juego.batch.begin();
            juego.font.draw(juego.batch, "REINICIANDO...", 330, 240);
            juego.batch.end();

            // Cuando pasan 2 segundos
            if (temporizador >= 2f) {
                System.out.println(">>> REINICIANDO JUEGO <<<");
                tarro.reiniciar();
                lluvia.reiniciar();
                gameOver = false;
                temporizador = 0;
            }
        }
    }

    public boolean estaEnGameOver() {
        return gameOver;
    }
}
