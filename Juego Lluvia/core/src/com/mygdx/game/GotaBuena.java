package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends ObjetoCaida {

    private Sound dropSound;

    public GotaBuena(Texture textura, Sound dropSound, float x, float y) {
        super(textura, x, y);
        this.dropSound = dropSound;
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(10);
    }

    @Override
    protected void reproducirSonido() {
        dropSound.play();
    }
}