package person.licky.tensei.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import person.licky.tensei.Tensei;

public class MainMenuScreen implements Screen {
    private final Tensei tensei;
    private OrthographicCamera camera;

    public MainMenuScreen(Tensei tensei) {
        this.tensei = tensei;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        tensei.batch.setProjectionMatrix(camera.combined);

        tensei.batch.begin();
        tensei.font.draw(tensei.batch, "Welcome to Tensei!!", 100, 150);
        tensei.font.draw(tensei.batch, "Tap anywhere to begin!", 100, 150);
        tensei.batch.end();
        if (Gdx.input.isTouched()) {
            tensei.setScreen(new GameScreen(tensei));
            dispose();
        }
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

    }
}
