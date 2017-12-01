package com.flappybird.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flappybird.game.FlappyBird;
import com.flappybird.game.sprites.Bird;
import com.flappybird.game.sprites.Tube;


public class PlayState extends state{

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1,groundPos2;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,300);
        cam.setToOrtho(false, FlappyBird.WIDTH/2,FlappyBird.HEIGHT/2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2,GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2)+ground.getWidth(),GROUND_Y_OFFSET);

        tubes = new Array<Tube>();

        for(int i = 1;i <= TUBE_COUNT;i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        updateGround();
        cam.position.x = bird.getPosition().x + 80;

        for(int i = 0; i < tubes.size;i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosBotTube().x + ((Tube.WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
            if(tube.collides(bird.getBounds())){
                gsm.set(new PlayState(gsm));
            }
        }
        if(bird.getPosition().y <= ground.getHeight()+GROUND_Y_OFFSET){
            gsm.set(new PlayState(gsm));
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch ab) {
        ab.setProjectionMatrix(cam.combined);
        ab.begin();
        ab.draw(bg,cam.position.x - (cam.viewportWidth/2),0);
        ab.draw(bird.getTexture(),bird.getPosition().x,bird.getPosition().y);
        for(Tube tube : tubes){
            ab.draw(tube.getTopTube(),tube.getPosTopTube().x,tube.getPosTopTube().y);
            ab.draw(tube.getBottomTube(),tube.getPosBotTube().x,tube.getPosBotTube().y);
            }
        ab.draw(ground,groundPos1.x,groundPos1.y);
        ab.draw(ground,groundPos2.x,groundPos2.y);

        ab.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube : tubes){
            tube.dispose();
        }
        System.out.println("Play State Disposed");
    }

    private void updateGround(){
        if (cam.position.x-(cam.viewportWidth/2)>ground.getWidth()+groundPos1.x){
            groundPos1.add(ground.getWidth()*2,0);
        }
        if (cam.position.x-(cam.viewportWidth/2)>ground.getWidth()+groundPos2.x){
            groundPos2.add(ground.getWidth()*2,0);
        }
    }

}
