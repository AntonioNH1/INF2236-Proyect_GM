package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Gota100 extends Gota {

    public Gota100(float x, float y, float velocidad) {
        super(x, y, velocidad, new Texture("gota100.png"));
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.sumarPuntos(100);
    }
}
