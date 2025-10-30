package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaCurativa extends ObjetoCaida {

    private Sound healSound;

    public GotaCurativa(Texture textura, Sound healSound, float x, float y) {
        super(textura, x, y);
        this.healSound = healSound;
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarVida();
    }

    @Override
    protected void reproducirSonido() {
        healSound.play();
    }
}