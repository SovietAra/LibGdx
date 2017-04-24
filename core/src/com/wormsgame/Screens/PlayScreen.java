package com.wormsgame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wormsgame.Scenes.HUD;
import com.wormsgame.Sprites.Worm;
import com.wormsgame.Tools.B2WorldCreator;
import com.wormsgame.WormsClass;

/**
 * Created by Daniel on 4/20/2017.
 */
public class PlayScreen implements Screen {
    private WormsClass game;

    private Viewport gamePort;
    private OrthographicCamera gamecam;
    private HUD hud;

    //TiledMap
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Getting touch Psition
    private Worm worm;
    private Vector3 touchPosition;

    //Box2D
    private World world;
    private Box2DDebugRenderer b2dr;

    //Animation
    private TextureAtlas atlas;

    public PlayScreen(WormsClass game) {
        this.game = game;

        atlas = new TextureAtlas("Player_and_Enemy.pack");

        touchPosition = new Vector3(0,0,0);


        hud = new HUD(game.batch);
        gamecam = new OrthographicCamera();
        gamePort = new StretchViewport(WormsClass.V_WIDTH / WormsClass.PPM,WormsClass.V_HEIGHT/ WormsClass.PPM,gamecam);

        // loading and rendering our tilemap
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("MyMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/WormsClass.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2 ,gamePort.getWorldHeight() /2,0);

        //Box2D gravity and activities
        world = new World(new Vector2(0,-10),true);
        worm = new Worm(world,this);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world,map);


    }

    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    public void handleInput(float dt)
    {//TODO adasd
        if(Gdx.input.isTouched())
        {
            touchPosition = gamecam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));

            //Movement to the right
            if (touchPosition.x > worm.b2body.getPosition().x  && worm.b2body.getLinearVelocity().x <= 2)
                worm.b2body.applyLinearImpulse(new Vector2(0.1f,0), worm.b2body.getWorldCenter(),true);

            //Jump
            if (Gdx.input.justTouched()) {
                if (touchPosition.y > worm.b2body.getPosition().y + 0.5)
                    worm.b2body.applyLinearImpulse(new Vector2(0, 4f), worm.b2body.getWorldCenter(), true);
            }
            //Movement to the Left
            if (touchPosition.x < worm.b2body.getPosition().x  && worm.b2body.getLinearVelocity().x >= -2)
                worm.b2body.applyLinearImpulse(new Vector2(-0.1f,0), worm.b2body.getWorldCenter(),true);

        }


    }

    public void update(float dt)
    {
        handleInput(dt);

        world.step(1/60f,6,2);
        gamecam.position.x = worm.b2body.getPosition().x;

        worm.update(dt);

        gamecam.update();
        renderer.setView(gamecam); // renders only what our game can see
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //renderer our 2D Bodies
        b2dr.render(world,gamecam.combined);

        //render animation
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        worm.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
