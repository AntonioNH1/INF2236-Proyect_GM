package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends Gota {

    public GotaMala(float x, float y, float velocidad) {
        super(x, y, velocidad, new Texture("gotaMala.png"));
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.dañar(); // método que ya tienes en Tarro
    }
}
