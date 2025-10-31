package com.mygdx.game;


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
