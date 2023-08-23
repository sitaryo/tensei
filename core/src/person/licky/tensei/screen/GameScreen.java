package person.licky.tensei.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import person.licky.tensei.Tensei;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {

    private final Tensei tensei;
    private final Texture dropImage;
    private final Texture bucketImage;
    private final Sound dropSound;
    private final Music rainMusic;

    private final OrthographicCamera camera;

    private final Rectangle bucket;
    private final List<Rectangle> raindrops;
    private long lastDropTime;

    private final Vector3 touchPos = new Vector3();

    public GameScreen(Tensei tensei) {
        this.tensei = tensei;


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        tensei.font = new BitmapFont();

        dropImage = new Texture(Gdx.files.internal("drop.png"));

        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        bucket = new Rectangle();
        bucket.x = 368;
        bucket.y = 20;
        bucket.height = 64;
        bucket.width = 64;

        raindrops = new ArrayList<>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        var raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 48;
        raindrop.height = 48;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
            }
        }

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            System.out.println(touchPos.x);
            camera.unproject(touchPos);
            System.out.println(touchPos.x);
            bucket.x = touchPos.x - 32;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }

        if (bucket.x < 0) {
            bucket.x = 0;
        }
        if (bucket.x > 800 - 64) {
            bucket.x = 800 - 64;
        }

        camera.update();
        tensei.batch.setProjectionMatrix(camera.combined);
        tensei.batch.begin();
        tensei.batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop : raindrops) {
            tensei.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        tensei.batch.end();
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
        rainMusic.dispose();
        bucketImage.dispose();
        dropImage.dispose();
        dropSound.dispose();
    }
}
