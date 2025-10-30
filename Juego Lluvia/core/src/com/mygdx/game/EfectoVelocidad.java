package com.mygdx.game;

/**
 * Estrategia concreta que implementa un efecto de velocidad.
 * Aumenta temporalmente la velocidad del tarro.
 */
public class EfectoVelocidad implements EfectoEspecial {

    private float factor;
    private int duracionSegundos;

    public EfectoVelocidad(float factor, int duracionSegundos) {
        this.factor = factor;
        this.duracionSegundos = duracionSegundos;
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.aumentarVelocidadTemporal(factor, duracionSegundos);
    }
}
