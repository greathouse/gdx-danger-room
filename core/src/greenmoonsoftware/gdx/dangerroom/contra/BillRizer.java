package greenmoonsoftware.gdx.dangerroom.contra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BillRizer {
  private Texture spriteSheet;
  private float stateTimer = 0f;
  private Animation<TextureRegion> run;
  private Animation<TextureRegion> standingShooting;
  private Animation<TextureRegion> jumping;
  private Animation<TextureRegion> runningShooting;
  private float x, y;

  public BillRizer(Texture spriteSheet) {
    this.spriteSheet = spriteSheet;
    standingShooting();
    jumping();
    running();
    runningShooting();
  }

  private void runningShooting() {
    runningShooting = animation( 0.1f,
      region(7, 126, 40, 40),
      region(47, 126, 40, 40),
      region(90, 126, 41, 40),
      region(134, 126, 40, 40)
    );
  }

  private void running() {
    Array<TextureRegion> frames = new Array<TextureRegion>();
    frames.add(region(7, 81, 30, 39));
    frames.add(region(44, 81, 29, 39));
    frames.add(region(78, 81, 34, 39));
    frames.add(region(118, 81, 29, 39));
    run = new Animation<TextureRegion>(0.18f, frames);
  }

  private void standingShooting() {
    Array<TextureRegion> frames = new Array<TextureRegion>();
    frames.add(region(3, 2, 32, 40));
    frames.add(region(41, 2, 32, 40));
    frames.add(region(75, 2, 40, 40));
    standingShooting = new Animation<TextureRegion>(0.1f, frames);
  }

  private void jumping() {
    Array<TextureRegion> frames = new Array<TextureRegion>();
    frames.add(region(2, 49, 24, 20));
    frames.add(region(30, 48, 20, 24));
    frames.add(region(59, 48, 24, 20));
    frames.add(region(93, 47, 20, 24));
    jumping = new Animation<TextureRegion>(0.1f, frames);
  }

  private Animation<TextureRegion> animation(float frameDuration, TextureRegion... regions) {
    Array<TextureRegion> frames = new Array<TextureRegion>();
    frames.addAll(regions);
    return new Animation<TextureRegion>(frameDuration, frames);
  }

  private TextureRegion region(int x, int y, int width, int height) {
    return new TextureRegion(this.spriteSheet, x, y, width, height);
  }

  public void update(float delta) {
    x = 100;
    y = 100;
  }

  public void render(SpriteBatch batch) {
    stateTimer += Gdx.graphics.getDeltaTime();
    TextureRegion currentFrame = runningShooting.getKeyFrame(stateTimer, true);
    batch.draw(currentFrame, x, y);
  }

  private TextureRegion getFrame(float delta) {
    stateTimer += delta;
    return run.getKeyFrame(stateTimer, true);
  }
}
