package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Recursos {
    // La única instancia 
    private static Recursos instancia;

    // Texturas
    public Texture texTarro;
    public Texture texGotaBuena;
    public Texture texGotaMala;
    public Texture texGotaDorada;
    public Texture texGotaCurativa;
    public Texture texGotaMalvada;
    public Texture texGotaVelocidad;
    public Texture texX2;

    // Sonidos
    public Sound soundHurt;
    public Sound soundDrop;
    public Sound soundHeal;
    public Sound soundEvil;
    public Sound soundSpeed;
    public Sound soundBonus;
    
    // Música
    public Music musicRain;

    //Constructor privado
    private Recursos() {
        cargarRecursos();
    }

    // Método de acceso global
    public static Recursos getInstancia() {
        if (instancia == null) {
            instancia = new Recursos();
        }
        return instancia;
    }

    private void cargarRecursos() {
        // Cargar Texturas
        texTarro = new Texture(Gdx.files.internal("bucket.png"));
        texGotaBuena = new Texture(Gdx.files.internal("drop.png"));
        texGotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        texGotaDorada = new Texture(Gdx.files.internal("dropGold.png"));
        texGotaCurativa = new Texture(Gdx.files.internal("dropHeal.png"));
        texGotaMalvada = new Texture(Gdx.files.internal("dropEvil.png"));
        texGotaVelocidad = new Texture(Gdx.files.internal("dropVelocidad.png"));
        texX2 = new Texture(Gdx.files.internal("x2.png"));

        // Cargar Sonidos
        soundHurt = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        soundDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

        soundHeal = Gdx.audio.newSound(Gdx.files.internal("heal.wav"));
        soundEvil = Gdx.audio.newSound(Gdx.files.internal("hurtScore.wav"));
        soundSpeed = Gdx.audio.newSound(Gdx.files.internal("soundSpeed.wav"));
        soundBonus = Gdx.audio.newSound(Gdx.files.internal("soundBonus.wav"));

        // Cargar Música
        musicRain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    }

    // Método para liberar memoria
    public void dispose() {
        texTarro.dispose();
        texGotaBuena.dispose();
        texGotaMala.dispose();
        texGotaDorada.dispose();
        texGotaCurativa.dispose();
        texGotaMalvada.dispose();
        texGotaVelocidad.dispose();
        texX2.dispose();

        soundHurt.dispose();
        soundDrop.dispose();
        soundHeal.dispose();
        soundEvil.dispose();
        soundSpeed.dispose();
        soundBonus.dispose();

        musicRain.dispose();
        
        instancia = null;
    }
}
