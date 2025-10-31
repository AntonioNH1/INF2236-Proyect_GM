package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends ObjetoCaida {


    public GotaMala(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.restarVidas(1); 
    }
    
}