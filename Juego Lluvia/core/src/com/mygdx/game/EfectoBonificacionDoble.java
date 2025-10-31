package com.mygdx.game;

/**
 * Aplica un multiplicador temporal de puntos al tarro.
 * Implementa EfectoEspecial (Strategy pattern).
 */
public class EfectoBonificacionDoble implements EfectoEspecial {

    private int duracionSegundos;

    public EfectoBonificacionDoble(int duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.activarMultiplicador(2.0f, duracionSegundos);
    }
}
