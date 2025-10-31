package com.mygdx.game;

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

    public void actualizarPosicion(float delta) {
        area.y -= 300 * delta;
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    public Rectangle getArea() {
        return area;
    }


    public final void procesarColision(Tarro tarro) {
        aplicarEfecto(tarro);
        reproducirSonido();
    }


    protected abstract void aplicarEfecto(Tarro tarro);


    protected void reproducirSonido() {

    }
}