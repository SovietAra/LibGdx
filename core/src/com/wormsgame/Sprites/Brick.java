package com.wormsgame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.wormsgame.Scenes.HUD;
import com.wormsgame.WormsClass;

import java.awt.*;

/**
 * Created by Daniel on 4/21/2017.
 */
public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds)
    {
        super(world,map,bounds);
        //Setting userdata to an object itself
        fixture.setUserData(this);

        setCategoryFilter(WormsClass.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","Collision");
        setCategoryFilter(WormsClass.DESTROYED_BIT);

        HUD.addScore(200);

        //Deleting the cell
        getCell().setTile(null);

        //Playing sounds
        WormsClass.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
