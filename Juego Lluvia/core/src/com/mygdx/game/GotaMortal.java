package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GotaMortal extends Gota {

    public GotaMortal(float x, float y, float velocidad) {
        super(x, y, velocidad, new Texture("gotaMortal.png"));
    }

    @Override
    public void efecto(Tarro tarro) {
        // Quita todas las vidas
        while (tarro.getVidas() > 0) {
            tarro.dañar();
        }
    }
}
