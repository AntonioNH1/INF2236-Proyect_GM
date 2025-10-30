package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCaida {
    protected Rectangle area;
    protected Texture textura;

    public ObjetoCaida(Texture textura, float x, float y) {
        this.textura = textura;
        this.area = new Rectangle(x, y, 64, 64);
    }

    // Comportamiento común a todos los objetos que caen
    public void actualizarPosicion(float delta) {
        area.y -= 300 * delta;
    }

    // Comportamiento común
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    public Rectangle getArea() {
        return area;
    }

    // --- TEMPLATE METHOD PATTERN ---

    /**
     * Este es el "Template Method".
     * Define el esqueleto del algoritmo de colisión y es 'final'
     * para que las subclases no puedan sobreescribirlo.
     */
    public final void procesarColision(Tarro tarro) {
        // 1. Aplicar el efecto (paso abstracto, obligatorio)
        aplicarEfecto(tarro);

        // 2. Reproducir un sonido (hook, opcional)
        reproducirSonido();
    }

    /**
     * Paso abstracto: Debe ser implementado por cada subclase concreta.
     * Define qué le hace este objeto al tarro.
     */
    protected abstract void aplicarEfecto(Tarro tarro);

    /**
     * Hook (gancho): Es un paso opcional con una implementación vacía.
     * Las subclases pueden (pero no están obligadas a) sobreescribirlo.
     */
    protected void reproducirSonido() {
        // Por defecto, no hace nada.
    }
}