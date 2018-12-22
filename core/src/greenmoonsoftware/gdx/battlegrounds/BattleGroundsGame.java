package greenmoonsoftware.gdx.battlegrounds;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import greenmoonsoftware.gdx.GreenMoonGame;
import greenmoonsoftware.gdx.ailounge.PlainScreen;

public class BattleGroundsGame extends GreenMoonGame {

    private Texture pixmaptex;

    public void onCreate() {
        this.setScreen(new PlainScreen(this));

        Pixmap pixmap = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0, 1, 0, 0.75f );
        pixmap.fillCircle( 8, 8, 8 );
        pixmaptex = new Texture( pixmap );
        pixmap.dispose();
    }

    @Override
    public void render() {
        super.render();
        doInBatch(batch -> batch.draw(pixmaptex, 100, 100));
    }
}
