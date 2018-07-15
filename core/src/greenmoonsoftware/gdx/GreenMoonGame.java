package greenmoonsoftware.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GreenMoonGame extends Game {
  private SpriteBatch batch;
  private World world;
  private int scale = 32;

  private Box2DDebugRenderer debugRenderer;
  private DebugHud debugHud;
  private boolean isDebugRendererEnabled = false;

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
    debugRenderer = new Box2DDebugRenderer();
    debugHud = new DebugHud();
    onCreate();
  }

  public void render(Camera camera) {
    if (isDebugRendererEnabled) {
      debugRenderer.render(world, scale(camera.combined));
      debugHud.render();
    }
  }

  protected World createWorld() {
    return new World(new Vector2(0, -10), true);
  }

  public Body createBody(BodyDef def) {
    return world.createBody(def);
  }

  public void addToDebug(String name, Body body) {
    debugHud.add(name, body);
  }

  public void step(float v, int i, int i1) {
    world.step(v, i, i1);
  }

  public SpriteBatch getBatch() {
    return batch;
  }

  public World getWorld() {
    return world;
  }

  public float toBox2d(float m) {
    return m / scale;
  }

  public float fromBox2d(float m) {
    return m * scale;
  }

  public Matrix4 scale(Matrix4 combined) {
    return combined.scl(scale);
  }

  public final void dispose() {
    onDispose();
    batch.dispose();
  }

  public GreenMoonGame enableDebugRendering() {
    this.isDebugRendererEnabled = true;
    return this;
  }

  public GreenMoonGame setScale(int scale) {
    this.scale = scale;
    return this;
  }

  public interface BatchAction {
    void execute(SpriteBatch batch);
  }
}
