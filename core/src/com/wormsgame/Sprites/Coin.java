package com.wormsgame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.wormsgame.Scenes.HUD;
import com.wormsgame.WormsClass;

/**
 * Created by Daniel on 4/21/2017.
 */
public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    public Coin(World world, TiledMap map, Rectangle bounds)
    {
        super(world,map,bounds);
        //getting the tileset
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        //read brick
        fixture.setUserData(this);

        setCategoryFilter(WormsClass.COIN_BIT);


    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin","Collision");
        if(getCell().getTile().getId()  == BLANK_COIN)
        {
            WormsClass.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }
        else
            WormsClass.manager.get("audio/sounds/coin.wav", Sound.class).play();

        //Change the Tile
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        HUD.addScore(100);
    }
}
