package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaMalvada extends ObjetoCaida {

    private Sound evilSound;

    public GotaMalvada(Texture textura, Sound evilSound, float x, float y) {
        super(textura, x, y);
        this.evilSound = evilSound;
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.restarVidas(2);
    }

    @Override
    protected void reproducirSonido() {
        evilSound.play();
    }
}