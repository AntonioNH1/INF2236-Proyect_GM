package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;

public class Lluvia {
    
    private Array<ObjetoCaida> objetosCaida; 
    private long lastDropTime;
    

    public Lluvia() {
        Recursos.getInstancia().musicRain.setLooping(true);
        Recursos.getInstancia().musicRain.play();
    }
    
    public void crear() {
        objetosCaida = new Array<ObjetoCaida>(); 
        crearObjetoCaida(); 
    }
    
    private void crearObjetoCaida() {
        float x = MathUtils.random(0, 800-64);
        float y = 480;
        
        ObjetoCaida nuevoObjeto;
        int tipo = MathUtils.random(1, 100); 
        
        Recursos res = Recursos.getInstancia();

        if (tipo <= 30) {
            nuevoObjeto = new GotaMala(res.texGotaMala, x, y);
        } 
        else if (tipo <= 42) { 
            nuevoObjeto = new GotaMalvada(res.texGotaMalvada, res.soundEvil, x, y);
        } 
        else if (tipo <= 50) {
            nuevoObjeto = new GotaCurativa(res.texGotaCurativa, res.soundHeal, x, y);
        } 
        else if (tipo <= 55) { 
            nuevoObjeto = new GotaDorada(res.texGotaDorada, res.soundDrop, x, y);
        } 
        else if (tipo <= 58) {
            nuevoObjeto = new GotaVelocidad(res.texGotaVelocidad, res.soundSpeed, x, y);
        }
        else if (tipo <= 60) { 
            nuevoObjeto = new GotaX2(res.texX2, res.soundBonus, x, y);
        }
        else { 
            nuevoObjeto = new GotaBuena(res.texGotaBuena, res.soundDrop, x, y);
        }
        
        objetosCaida.add(nuevoObjeto);
        lastDropTime = TimeUtils.nanoTime();
    }
    
    public void actualizarMovimiento(Tarro tarro) { 
        if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearObjetoCaida();
       
        Iterator<ObjetoCaida> iter = objetosCaida.iterator();
        while (iter.hasNext()) {
            ObjetoCaida objeto = iter.next();
            objeto.actualizarPosicion(Gdx.graphics.getDeltaTime());
            
            if(objeto.getArea().y + 64 < 0) {
                iter.remove(); 
                continue; 
            }
            
            if(objeto.getArea().overlaps(tarro.getArea())) {
                objeto.procesarColision(tarro);
                iter.remove(); 
            }
        }   
        tarro.actualizarBonificaciones();
    }
    
    public void actualizarDibujoLluvia(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) { 
        for (ObjetoCaida objeto : objetosCaida) {
            objeto.dibujar(batch);
        }
    }
    
    public void destruir() {
    
    }
    
    public void reset() {
        if (objetosCaida != null) {
            objetosCaida.clear(); 
        }
        crearObjetoCaida(); 
        Recursos.getInstancia().musicRain.play(); 
    }
}