package greenmoonsoftware.gdx.battlegrounds;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import greenmoonsoftware.gdx.GreenMoonGame;

public class Player {
    private Texture texture;
    private GreenMoonGame game;

    private int x;
    private int y;


    Player(GreenMoonGame game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        Pixmap pixmap = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0, 1, 0, 0.75f );
        pixmap.fillCircle( 8, 8, 8 );
        texture = new Texture( pixmap );
        pixmap.dispose();
    }

    public void render() {
        game.doInBatch(batch -> batch.draw(texture, x, y));
    }
}
