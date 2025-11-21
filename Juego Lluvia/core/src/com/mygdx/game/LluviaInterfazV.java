package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LluviaInterfazV extends Game {
    
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        // 1. Inicializar herramientas compartidas
        batch = new SpriteBatch();
        font = new BitmapFont(); // Fuente por defecto

        // 2. Cargar recursos (Singleton)
        Recursos.getInstancia(); 

        // 3. Ir a la pantalla del Menú
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); // Importante: delega el renderizado a la pantalla actual
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        Recursos.getInstancia().dispose(); // Liberar recursos al cerrar todo
    }
}