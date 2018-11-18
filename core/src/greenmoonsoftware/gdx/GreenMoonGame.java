package greenmoonsoftware.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GreenMoonGame extends Game {
  private SpriteBatch batch;

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
    onCreate();
  }

  public SpriteBatch getBatch() {
    return batch;
  }

  public final void dispose() {
    onDispose();
    batch.dispose();
  }

  public interface BatchAction {
    void execute(SpriteBatch batch);
  }
}
