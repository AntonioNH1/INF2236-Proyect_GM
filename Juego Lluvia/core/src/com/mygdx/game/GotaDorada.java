package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaDorada extends ObjetoCaida {

    private Sound goldSound;

    public GotaDorada(Texture textura, Sound goldSound, float x, float y) {
        super(textura, x, y);
        this.goldSound = goldSound;
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(50);
    }

    @Override
    protected void reproducirSonido() {
        goldSound.play();
    }
}