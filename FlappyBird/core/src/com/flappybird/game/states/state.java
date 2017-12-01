package com.flappybird.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by vivasvan on 30-Nov-17.
 */

public abstract class state {

    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected state(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();


    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch ab);
    public abstract void dispose();
}
