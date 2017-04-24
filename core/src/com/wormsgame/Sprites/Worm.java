package com.wormsgame.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.wormsgame.Screens.PlayScreen;
import com.wormsgame.WormsClass;

import javax.xml.soap.Text;

/**
 * Created by Daniel on 4/20/2017.
 */
public class Worm extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion wormStand;
    public enum State{ FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;

    private Animation <TextureRegion> wormRun;
    private Animation <TextureRegion> wormJump;

    private boolean runningRight;
    private float stateTimer;

    public Worm(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("Player"));
        this.world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++)
        {
            frames.add(new TextureRegion(getTexture(), i * 33, 316,32,32));
            wormRun = new Animation(0.1f, frames);

        }

        for (int i = 1; i == 1; i++)
        {
            frames.add(new TextureRegion(getTexture(),i*32,317,32,32));
            wormJump = new Animation(0.1f,frames);
        }


        wormStand = new TextureRegion(getTexture(), 32,253,31,31);
        defineWorm();
        setBounds(0,0,31/WormsClass.PPM,32/WormsClass.PPM);
        setRegion(wormStand);





    }

    public void update(float dt)
    {
        setPosition(b2body.getPosition().x -getWidth() / 2,b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = wormJump.getKeyFrame(stateTimer,true);
                break;
            case STANDING:
            default:
                region = wormStand;
                break;
        }
        // Flip
        if((b2body.getLinearVelocity().x < 0  ||  !runningRight)  && !region.isFlipX())
        {
            region.flip(true,false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
         region.flip(true,false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState()
    {
        if (b2body.getLinearVelocity().x != 0)
        {
            return State.RUNNING;
        }
        else
            return State.STANDING;
    }
    public void defineWorm()
    {

        BodyDef bdef = new BodyDef();
        bdef.position.set(60/WormsClass.PPM,32/WormsClass.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        // Creating a rectangle
        shape.setRadius(12 / WormsClass.PPM);

        fdef.shape =shape;
        b2body.createFixture(fdef);
    }
}
