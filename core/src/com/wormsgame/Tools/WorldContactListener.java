package com.wormsgame.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.wormsgame.Sprites.InteractiveTileObject;

/**
 * Created by Daniel on 4/24/2017.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // What happens if 2 Fixters begin their connection
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //Find out, if one of the Fixtures is the Head
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            //Find out what is a Head and what is a Object
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB :fixA;

            // We have to find out, if the object is a InteractiveTileObject
            if(object.getUserData()!= null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        // What happens if 2 Fixters are disconnected


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //You can change the characteristic of collisions

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // What happened due to this collision
    }
}
