package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Gota que aplica un efecto de bonificación doble (x2) de puntos
 * durante unos segundos.
 * Cumple OCP, LSP, DIP y Strategy.
 */
public class GotaX2 extends ObjetoCaida {

    private Sound soundBonus;
    private EfectoEspecial efectoDoble;

    public GotaX2(Texture textura, Sound soundBonus, float x, float y) {
        super(textura, x, y);
        this.soundBonus = soundBonus;
        this.efectoDoble = new EfectoBonificacionDoble(8); // dura 8 segundos
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(50); // da puntos base
        efectoDoble.aplicarEfecto(tarro); // activa el multiplicador
    }

    @Override
    protected void reproducirSonido() {
        soundBonus.play();
    }
}
