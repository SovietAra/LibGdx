package com.wormsgame.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.wormsgame.Sprites.Brick;
import com.wormsgame.Sprites.Coin;
import com.wormsgame.WormsClass;

/**
 * Created by Daniel on 4/21/2017.
 */
public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map)
    {
        // Parts of Box2D
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //ground
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))//getting the 2nd layer of tiledmap
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ WormsClass.PPM,(rect.getY() + rect.getHeight() / 2)/WormsClass.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2 / WormsClass.PPM,rect.getHeight()/2/ WormsClass.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //pipe
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))//getting the 2nd layer of tiledmap
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ WormsClass.PPM,(rect.getY() + rect.getHeight() / 2)/WormsClass.PPM);


            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2 / WormsClass.PPM,rect.getHeight()/2/ WormsClass.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //bricks
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))//getting the 2nd layer of tiledmap
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); //advising a rectangle
            new Brick(world,map,rect);
        }

        //coins
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))//getting the 2nd layer of tiledmap
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(world,map,rect);
        }
    }

}
