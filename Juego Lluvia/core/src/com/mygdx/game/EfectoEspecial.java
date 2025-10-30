package com.mygdx.game;

/**
 * Interfaz para definir estrategias de efecto especial sobre el Tarro.
 * Cumple el principio de inversión de dependencia (DIP):
 * el Tarro y las gotas dependen de esta abstracción.
 */
public interface EfectoEspecial {
    void aplicarEfecto(Tarro tarro);
}
