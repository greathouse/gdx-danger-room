package greenmoonsoftware.gdx;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GreenMoonBox2dGame extends GreenMoonGame {

  private World world;
  private int scale = 32;

  private Box2DDebugRenderer debugRenderer;
  private DebugHud debugHud;
  private boolean isDebugRendererEnabled = false;

  @Override
  public void onCreate() {
    world = createWorld();
    debugRenderer = new Box2DDebugRenderer();
    debugHud = new DebugHud();
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

  public GreenMoonBox2dGame enableDebugRendering() {
    this.isDebugRendererEnabled = true;
    return this;
  }

  public GreenMoonBox2dGame setScale(int scale) {
    this.scale = scale;
    return this;
  }
}
