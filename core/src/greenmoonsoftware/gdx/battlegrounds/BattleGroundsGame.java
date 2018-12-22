package greenmoonsoftware.gdx.battlegrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import greenmoonsoftware.gdx.GreenMoonGame;
import greenmoonsoftware.gdx.ailounge.PlainScreen;

public class BattleGroundsGame extends GreenMoonGame {

    private Player player1;
    private BattleGroundsInputProcessor inputProcessor;

    public void onCreate() {
        this.setScreen(new PlainScreen(this));
        player1 = new Player(this, 100, 100);
        inputProcessor = new BattleGroundsInputProcessor();

        setupInputProcessor();
    }

    private void setupInputProcessor() {
        addCharacterKeyMap(player1, Input.Keys.LEFT, Input.Keys.UP, Input.Keys.RIGHT, Input.Keys.DOWN);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    private void addCharacterKeyMap(Player c, int left, int up, int right, int down) {
        inputProcessor
                .addKeyDown(left, (x) -> c.move(Direction.LEFT))
                .addKeyUp(left, (x) -> c.stop(Direction.LEFT))
                .addKeyDown(up, (x) -> c.move(Direction.UP))
                .addKeyUp(up, (x) -> c.stop(Direction.UP))
                .addKeyDown(right, (x) -> c.move(Direction.RIGHT))
                .addKeyUp(right, (x) -> c.stop(Direction.RIGHT))
                .addKeyDown(down, (x) -> c.move(Direction.DOWN))
                .addKeyUp(down, (x) -> c.stop(Direction.DOWN));
    }

    @Override
    public void render() {
        super.render();
        player1.render();
    }
}
