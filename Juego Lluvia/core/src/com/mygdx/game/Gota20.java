package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Gota20 extends Gota {

    public Gota20(float x, float y, float velocidad) {
        super(x, y, velocidad, new Texture("gota20.png"));
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.sumarPuntos(20);
    }
}
