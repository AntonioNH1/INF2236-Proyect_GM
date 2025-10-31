package com.mygdx.game;


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
