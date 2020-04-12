package com.mozarellabytes.kroy.Utilities;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){return false;}

    @Override
    public boolean mouseMoved(int screenX, int screenY){return false;}

    @Override
    public boolean scrolled(int amount){return false;}


}
