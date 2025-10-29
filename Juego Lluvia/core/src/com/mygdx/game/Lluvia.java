package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.MathUtils;

public class Lluvia {

    private Array<Gota> gotas;         
    private long lastDropTime;

    private Sound dropSound;
    private Music rainMusic;

    public Lluvia(Sound dropSound, Music rainMusic) {
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
    }

    public void crear() {
        gotas = new Array<Gota>();
        crearGotaDeLluvia();

        // iniciar música
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        float x = MathUtils.random(0, 800 - 64);
        float y = 480;
        float velocidad = 300;

        int tipo = MathUtils.random(1, 100);
        Gota nueva;

        if (tipo <= 50) {            // 50%
            nueva = new Gota10(x, y, velocidad);
        } else if (tipo <= 70) {     // 20%
            nueva = new Gota20(x, y, velocidad);
        } else if (tipo <= 85) {     // 15%
            nueva = new Gota50(x, y, velocidad);
        } else if (tipo <= 95) {     // 10%
            nueva = new GotaMala(x, y, velocidad);  
        } else {                     // 5%
            nueva = new GotaMortal(x, y, velocidad); 
        }


        gotas.add(nueva);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void actualizarMovimiento(Tarro tarro) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }

        for (int i = gotas.size - 1; i >= 0; i--) {
            Gota g = gotas.get(i);
            g.mover(Gdx.graphics.getDeltaTime());

            // eliminar si sale de pantalla
            if (g.getRectangulo().y + 64 < 0) {
                gotas.removeIndex(i);
                continue;
            }

            // eliminar si colisiona con el tarro
            if (tarro.colisionaCon(g.getRectangulo())) {
                g.efecto(tarro);
                dropSound.play();
                gotas.removeIndex(i);
            }
        }
    }


    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Gota g : gotas) {
            g.dibujar(batch);
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }
}
