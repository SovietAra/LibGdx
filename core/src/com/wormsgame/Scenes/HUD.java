package com.wormsgame.Scenes;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wormsgame.WormsClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.awt.*;

/**
 * Created by Daniel on 4/20/2017.
 */
public class HUD implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private float timeCount;
    private static int score;

    Label countdownLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label wormLabel;

    public HUD(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(WormsClass.V_WIDTH,WormsClass.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top(); //place table of the top of our screen
        table.setFillParent(true); // table has the size of the screen

        countdownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //Createing a Label, "d" stand for integer and the length of it. worldTimer is our Integer there.
        //Label Style is just a "Cosmetic' features
        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("First World", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        wormLabel = new Label("Worm", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(wormLabel).expandX().padTop(10); // expand X - > get the position of X
        table.add(worldLabel).expandX().padTop(10); // you can combine those expands w/o any troubles
        table.add(timeLabel).expandX().padTop(10); // padTop - > jumps x units from the top
        table.row(); // creating a new row
        table.add(scoreLabel).expandX();// you don't need pad there
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);

    }

    public void update (float dt)
    {
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer --;
            countdownLabel.setText(String.format("%03d",worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%03d",score));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
