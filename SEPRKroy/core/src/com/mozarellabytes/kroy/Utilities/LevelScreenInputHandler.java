package com.mozarellabytes.kroy.Utilities;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mozarellabytes.kroy.Screens.LevelScreen;

public class LevelScreenInputHandler implements InputProcessor {

    private final LevelScreen levelScreen;

    public LevelScreenInputHandler(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
    }

    @Override
    public boolean keyDown(int keycode){
        switch (keycode) {
            case Input.Keys.ESCAPE:
            case Input.Keys.C:
                levelScreen.changeScreen();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode){
        return false;
    }

    @Override
    public boolean keyTyped(char character){return false;}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 clickCoordinates = new Vector2(screenX, screenY);
        Vector3 position = levelScreen.camera.unproject(new Vector3(clickCoordinates.x, clickCoordinates.y, 0));

        if(levelScreen.getHardButton().contains(position.x, position.y)) {
            levelScreen.clickedHardButton();
        } else if(levelScreen.getMediumButton().contains(position.x, position.y)){
            levelScreen.clickedMediumButton();
        } else if(levelScreen.getEasyButton().contains(position.x, position.y)){
            levelScreen.clickedEasyButton();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 clickCoordinates = new Vector2(screenX, screenY);
        Vector3 position = levelScreen.camera.unproject(new Vector3(clickCoordinates.x, clickCoordinates.y, 0));

        if(levelScreen.getHardButton().contains(position.x, position.y) || levelScreen.getMediumButton().contains(position.x, position.y) || levelScreen.getEasyButton().contains(position.x, position.y)) {
            levelScreen.toGameScreen();
        } else {
            levelScreen.Idle();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){return false;}

    @Override
    public boolean mouseMoved(int screenX, int screenY){return false;}

    @Override
    public boolean scrolled(int amount){return false;}


}
