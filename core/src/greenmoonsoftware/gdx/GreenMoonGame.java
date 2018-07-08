package greenmoonsoftware.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GreenMoonGame extends Game {
  private SpriteBatch batch;
  private World world;

  protected void onDispose(){}
  protected void onCreate(){}

  public GreenMoonGame setProjectionMatrix(Camera camera) {
    if (camera == null) {
      throw new IllegalArgumentException("Camera cannot be null");
    }
    batch.setProjectionMatrix(camera.combined);
    return this;
  }

  public GreenMoonGame doInBatch(BatchAction action) {
    batch.begin();
    if (action != null)
      action.execute(batch);
    batch.end();
    return this;
  }

  @Override
  public final void create() {
    batch = new SpriteBatch();
    world = createWorld();
    onCreate();
  }

  protected World createWorld() {
    return new World(new Vector2(0, -10), true);
  }

  public Body createBody(BodyDef def) {
    return world.createBody(def);
  }

  public World getWorld() {
    return world;
  }

  public void step(float v, int i, int i1) {
    world.step(v, i, i1);
  }

  public final void dispose() {
    onDispose();
    batch.dispose();
  }

  public interface BatchAction {
    void execute(SpriteBatch batch);
  }

  public SpriteBatch getBatch() {
    return batch;
  }
}
