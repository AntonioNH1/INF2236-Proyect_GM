package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends ObjetoCaida {

    // No necesita sonido propio, el tarro lo reproduce al ser dañado
    public GotaMala(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.dañar(); // El método dañar() del tarro ya reproduce el sonido
    }
    
    // No sobreescribe reproducirSonido(), usando el hook vacío.
}