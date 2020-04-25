package com.mozarellabytes.kroy.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.GUI;
import com.mozarellabytes.kroy.Utilities.GameInputHandler;
import com.mozarellabytes.kroy.Utilities.LevelScreenInputHandler;
import com.mozarellabytes.kroy.Utilities.MenuInputHandler;
import java.awt.*;

public class LevelScreen implements Screen {
    private final Kroy game;

    private Texture backgroundImage;

    private final Rectangle easyButton;

    private final Rectangle mediumButton;

    private final Rectangle hardButton;

    private final Texture easyButtonTexture;
    private final Texture easyButtonClickedTexture;

    private final Texture mediumButtonTexture;
    private final Texture mediumButtonClickedTexture;

    private final Texture hardButtonTexture;
    private final Texture hardButtonClickedTexture;

    private Texture currentHardTexture;
    private Texture currentMediumTexture;
    private Texture currentEasyTexture;

    private final float screenWidth;
    private final float screenHeight;
    private final Screen parent;
    private final String screen;

    public final OrthographicCamera camera;

    int difficulty = 0;

    public LevelScreen(final Kroy game, Screen parent, String screen) {
        this.parent = parent;
        this.screen = screen;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);

        screenWidth = camera.viewportWidth;
        screenHeight = camera.viewportHeight;

        this.game = game;
        backgroundImage = new Texture(Gdx.files.internal("menuscreen_blank_2.png"), true);
        backgroundImage.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        easyButtonTexture = new Texture(Gdx.files.internal("ui/easyButton.png"),true);
        easyButtonTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        easyButtonClickedTexture = new Texture(Gdx.files.internal("ui/easyButton_clicked.png"),true);
        easyButtonClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        mediumButtonTexture = new Texture(Gdx.files.internal("ui/mediumButton.png"), true);
        mediumButtonTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        mediumButtonClickedTexture = new Texture(Gdx.files.internal("ui/mediumButton_clicked.png"),true);
        mediumButtonClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        hardButtonTexture = new Texture(Gdx.files.internal("ui/hardButton.png"), true);
        hardButtonTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        hardButtonClickedTexture = new Texture(Gdx.files.internal("ui/hardButton_clicked.png"),true);
        hardButtonClickedTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        currentEasyTexture = easyButtonTexture;
        currentMediumTexture = mediumButtonTexture;
        currentHardTexture = hardButtonTexture;

        easyButton = new Rectangle();
        easyButton.width = (int) (easyButtonTexture.getWidth()*0.75);
        easyButton.height = (int) (easyButtonTexture.getHeight()*0.75);
        easyButton.x = (int) (camera.viewportWidth/2 - easyButton.width/2);
        easyButton.y = (int) ((camera.viewportHeight/2 - easyButton.height/2)*0.2);

        mediumButton = new Rectangle();
        mediumButton.width = (int) (mediumButtonTexture.getWidth()*0.75);
        mediumButton.height = (int) (mediumButtonTexture.getHeight()*0.75);
        mediumButton.x = (int) (camera.viewportWidth /2 -mediumButton.width/2);
        mediumButton.y = (int) ((camera.viewportHeight/2 - mediumButton.height/2)*0.45);

        hardButton = new Rectangle();
        hardButton.width = (int) (hardButtonTexture.getWidth()*0.75);
        hardButton.height = (int) (hardButtonTexture.getHeight()*0.75);
        hardButton.x = (int) (camera.viewportWidth /2 - hardButton.width/2);
        hardButton.y = (int) ((camera.viewportHeight/2 - hardButton.height/2)*0.7);

        Gdx.input.setInputProcessor(new LevelScreenInputHandler(this));

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        drawBackgroundImage();

        game.batch.begin();
        game.batch.draw(backgroundImage,0,0,camera.viewportWidth,camera.viewportHeight);
        game.font60.draw(game.batch,"Select a difficulty level", screenWidth / 3.5f, screenHeight / 2f);
        game.font60Purple.draw(game.batch,"Select a difficulty level", screenWidth / 3.49f, screenHeight / 1.99f);

        game.batch.draw(currentEasyTexture,easyButton.x,easyButton.y,easyButton.width,easyButton.height);
        game.batch.draw(currentMediumTexture,mediumButton.x,mediumButton.y,mediumButton.width,mediumButton.height);
        game.batch.draw(currentHardTexture,hardButton.x,hardButton.y,hardButton.width,hardButton.height);

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

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
        backgroundImage.dispose();
        easyButtonTexture.dispose();
        easyButtonClickedTexture.dispose();
        mediumButtonTexture.dispose();
        mediumButtonClickedTexture.dispose();
        hardButtonTexture.dispose();
        hardButtonClickedTexture.dispose();
        currentEasyTexture.dispose();
        currentMediumTexture.dispose();
        currentHardTexture.dispose();
    }

    private void drawBackgroundImage(){
        game.batch.begin();
        game.batch.draw(backgroundImage, 0 , 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.end();
    }
    public void changeScreen() {
        if (this.screen.equals("game")) {
            GUI gui = new GUI(game, (GameScreen) parent);
            Gdx.input.setInputProcessor(new GameInputHandler((GameScreen) parent, gui));
            gui.idleInfoButton();
            this.game.setScreen(parent);
        } else if (this.screen.equals("menu")) {
            Gdx.input.setInputProcessor(new MenuInputHandler((MenuScreen)parent));
            this.game.setScreen(parent);
        }
    }

    public void toGameScreen() {
        game.setScreen(new GameScreen(game, difficulty));
        //game.setScreen(new MinigameScreen(this.game)); //uncomment this to play minigame when clicking the "Start" button.
        this.dispose();
    }

    public void Idle(){
        currentHardTexture = hardButtonTexture;
        currentMediumTexture = mediumButtonTexture;
        currentEasyTexture = easyButtonTexture;
    }
    public void clickedEasyButton() {
        currentEasyTexture = easyButtonClickedTexture;
        difficulty = 0;
    }

    public void clickedMediumButton() {
        currentMediumTexture = mediumButtonClickedTexture;
        difficulty = 1;
    }

    public void clickedHardButton() {
        currentHardTexture = hardButtonClickedTexture;
        difficulty = 2;
    }

    public Rectangle getEasyButton() {
        return easyButton;
    }

    public Rectangle getMediumButton() {
        return mediumButton;
    }

    public Rectangle getHardButton() {
        return hardButton;
    }





}
