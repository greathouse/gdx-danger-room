package greenmoonsoftware.gdx.battlegrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import greenmoonsoftware.gdx.GreenMoonGame;

import java.util.LinkedHashSet;

public class Player {
    private Texture texture;
    private GreenMoonGame game;

    private int x;
    private int y;
    private LinkedHashSet<Direction> activeDirections = new LinkedHashSet<Direction>();

    private float stateTime;


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
        stateTime += Gdx.graphics.getDeltaTime();
        updateLocation();
        game.doInBatch(batch -> batch.draw(texture, x, y));
    }

    private void updateLocation() {
        activeDirections.forEach(this::calculateLocation);
    }

    private void calculateLocation(Direction d) {
        if (d == null){
            return;
        }
        float[] moves = d.move();
        x += moves[0];
        y += moves[1];
    }

    public void move(Direction d) {
        activeDirections.add(d);
    }

    public void stop(Direction d) {
        activeDirections.remove(d);
    }
}
