package greenmoonsoftware.gdx.battlegrounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import greenmoonsoftware.gdx.GreenMoonGame;

import java.util.LinkedHashSet;

public class Player {
    private Texture texture;
    private Sprite turret;
    private GreenMoonGame game;

    private int x;
    private int y;
    private LinkedHashSet<Direction> activeDirections = new LinkedHashSet<Direction>();

    private float stateTime;

    private float elapsedTime = 0.0f;
    private Vector2 center = new Vector2(10, 10);


    Player(GreenMoonGame game, int x, int y) {
        this.game = game;
        stateTime = 0f;
        this.x = x;
        this.y = y;
        body();
        turret();
    }

    private void turret() {
        Pixmap p = new Pixmap(6, 2, Pixmap.Format.RGBA8888);
        p.setColor(0,1,0,0.75f);
        p.fillRectangle(0,0,6,2);
        turret = new Sprite(new Texture(p));
        p.dispose();
    }

    private void body() {
        Pixmap pixmap = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0, 1, 0, 0.75f );
        pixmap.fillCircle( 8, 8, 8 );
        texture = new Texture( pixmap );
        pixmap.dispose();
    }

    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        final float speed = 30.0f; // in degrees per second
        final float radius = 8.0f; // the radius of the circle you'll be rotating
        float angle = stateTime * speed;
        updateLocation();
        game.doInBatch(batch -> {
            batch.draw(texture, x, y);
            turret.setRotation(angle - 90);
            turret.setPosition(x + 20 + radius * (float)Math.cos(angle * MathUtils.degRad), y + 8 + radius * (float)Math.sin(angle * MathUtils.degRad));
            turret.draw(batch);
//            batch.draw(turret, x + 18, y + 7);
        });
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
