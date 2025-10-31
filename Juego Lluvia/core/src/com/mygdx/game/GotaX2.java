package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


public class GotaX2 extends ObjetoCaida {

    private Sound soundBonus;
    private EfectoEspecial efectoDoble;

    public GotaX2(Texture textura, Sound soundBonus, float x, float y) {
        super(textura, x, y);
        this.soundBonus = soundBonus;
        this.efectoDoble = new EfectoBonificacionDoble(8); 
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(50); 
        efectoDoble.aplicarEfecto(tarro); 
    }

    @Override
    protected void reproducirSonido() {
        soundBonus.play();
    }
}
