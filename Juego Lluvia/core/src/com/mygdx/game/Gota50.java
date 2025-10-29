package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Gota50 extends Gota {

    public Gota50(float x, float y, float velocidad) {
        super(x, y, velocidad, new Texture("gota50.png"));
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.sumarPuntos(50);
    }
}
