package com.wormsgame.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.wormsgame.WormsClass;

/**
 * Created by Daniel on 4/21/2017.
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds)
    {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth()/2)/ WormsClass.PPM,(bounds.getY() + bounds.getHeight() / 2)/WormsClass.PPM);


        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth()/2 / WormsClass.PPM,bounds.getHeight()/2/ WormsClass.PPM);
        fdef.shape = shape;

        //Capturing the fixture of an object into variable ( to decide what the fuck it is)
        fixture = body.createFixture(fdef);
    }

    public abstract void onHeadHit();
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    //Getting a layer cell of a tile that has to be replaced
    public TiledMapTileLayer.Cell getCell(){
        //Second layer
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        //Getting the right size of a cell
        return layer.getCell((int)(body.getPosition().x * WormsClass.PPM / 16),
                (int)(body.getPosition().y * WormsClass.PPM / 16));

    }
}
