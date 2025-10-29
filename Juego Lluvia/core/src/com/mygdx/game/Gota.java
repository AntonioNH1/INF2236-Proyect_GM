package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase abstracta que representa una gota genérica.
 * Sirve como superclase para las gotas específicas (Gota10, Gota20, etc.)
 */
public abstract class Gota {

    protected float x, y, velocidad;      // posición y velocidad de caída
    protected Texture textura;            // imagen asociada
    protected Rectangle rectangulo;       // área para detectar colisiones

    public Gota(float x, float y, float velocidad, Texture textura) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.textura = textura;
        this.rectangulo = new Rectangle(x, y, textura.getWidth(), textura.getHeight());
    }

    /** Actualiza la posición vertical de la gota */
    public void mover(float delta) {
        y -= velocidad * delta;
        rectangulo.setPosition(x, y);
    }

    /** Dibuja la gota en pantalla */
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, x, y, 48, 48); // 🔹 fuerza tamaño 48x48 px
    }

    /** Retorna el rectángulo de colisión */
    public Rectangle getRectangulo() {
        return rectangulo;
    }

    /** 
     * Método abstracto que define el efecto de la gota al tocar el tarro.
     * Cada subclase lo implementa de manera distinta (sumar puntos, restar vidas, etc.)
     */
    public abstract void efecto(Tarro tarro);
}
