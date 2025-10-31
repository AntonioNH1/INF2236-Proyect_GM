package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


public class GotaVelocidad extends ObjetoCaida {

    private Sound speedSound;
    private EfectoEspecial efectoVelocidad; 

    public GotaVelocidad(Texture textura, Sound speedSound, float x, float y) {
        super(textura, x, y);
        this.speedSound = speedSound;
        this.efectoVelocidad = new EfectoVelocidad(1.5f, 5); 
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(30);
        efectoVelocidad.aplicarEfecto(tarro);
    }

    @Override
    protected void reproducirSonido() {
        speedSound.play();
    }
}
