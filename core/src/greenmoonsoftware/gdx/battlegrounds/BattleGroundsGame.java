package greenmoonsoftware.gdx.battlegrounds;

import greenmoonsoftware.gdx.GreenMoonGame;
import greenmoonsoftware.gdx.ailounge.PlainScreen;

public class BattleGroundsGame extends GreenMoonGame {

    private Player player1;

    public void onCreate() {
        this.setScreen(new PlainScreen(this));
        player1 = new Player(this, 100, 100);
        player2 = new Player(this, 200, 100);
    }

    @Override
    public void render() {
        super.render();
        player1.render();
    }
}
