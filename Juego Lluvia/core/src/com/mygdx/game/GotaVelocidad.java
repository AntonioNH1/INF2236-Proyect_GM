package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Gota que usa la estrategia de efecto especial de velocidad.
 * Cumple OCP, LSP y Strategy (usa una estrategia EfectoEspecial).
 */
public class GotaVelocidad extends ObjetoCaida {

    private Sound speedSound;
    private EfectoEspecial efectoVelocidad; // estrategia (patrón Strategy)

    public GotaVelocidad(Texture textura, Sound speedSound, float x, float y) {
        super(textura, x, y);
        this.speedSound = speedSound;
        this.efectoVelocidad = new EfectoVelocidad(1.5f, 5); // 50% más rápido por 5 seg
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        // Otorga puntos y aplica el efecto especial
        tarro.sumarPuntos(30);
        efectoVelocidad.aplicarEfecto(tarro);
    }

    @Override
    protected void reproducirSonido() {
        speedSound.play();
    }
}
