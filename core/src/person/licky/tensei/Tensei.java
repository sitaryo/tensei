package person.licky.tensei;

import com.badlogic.gdx.Game;
import person.licky.tensei.screen.*;
import person.licky.tensei.screen.meta.GameScreen;

public class Tensei extends Game {

    private final LoadingScreen loadingScreen;
    private final MainScreen mainScreen;
    private final MenuScreen menuScreen;
    private final PreferencesScreen preferencesScreen;
    private final EndScreen endScreen;

    public Tensei() {
        this.loadingScreen = new LoadingScreen(this);
        this.mainScreen = new MainScreen(this);
        this.menuScreen = new MenuScreen(this);
        this.preferencesScreen = new PreferencesScreen(this);
        this.endScreen = new EndScreen(this);
    }

    @Override
    public void create() {
        changeScreen(GameScreen.MENU);
    }

    @Override
    public void render() {
    }

    @Override
    public void dispose() {
    }

    public void changeScreen(GameScreen screen) {
        this.setScreen(
                switch (screen) {
                    case LOADING -> loadingScreen;
                    case MAIN -> mainScreen;
                    case MENU -> menuScreen;
                    case PREFERENCES -> preferencesScreen;
                    case END -> endScreen;
                }
        );
    }
}
