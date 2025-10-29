package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Gota10 extends Gota {

    public Gota10(float x, float y, float velocidad) {
        super(x, y, velocidad, new Texture("gota10.png"));
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.sumarPuntos(10);
    }
}
