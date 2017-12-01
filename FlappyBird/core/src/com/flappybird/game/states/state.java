package com.flappybird.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


abstract class   state {

    OrthographicCamera cam;
    private Vector3 mouse;
    GameStateManager gsm;

    state(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();


    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch ab);
    public abstract void dispose();
}
