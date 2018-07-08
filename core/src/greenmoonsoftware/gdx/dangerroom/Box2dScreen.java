package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import greenmoonsoftware.gdx.GreenMoonGame;
import greenmoonsoftware.gdx.GreenMoonTiledRenderer;

public class Box2dScreen implements Screen {

  public static final int PPM = 32;
  private final GreenMoonGame game;
  private final OrthographicCamera camera;
  private final World world;
  private final Box2DDebugRenderer debugRenderer;
  private final Hud hud;

  GreenMoonTiledRenderer mapRenderer;

  private Body playerBody;

  public Box2dScreen(GreenMoonGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800 / 2, 480 / 2);
    camera.update();

    hud = new Hud(game, 800, 480);

    world = new World(new Vector2(0, -10), true);
    debugRenderer = new Box2DDebugRenderer();

    mapRenderer = new GreenMoonTiledRenderer("gunner/Room1.tmx", world);

    definePlayer();
  }

  private void definePlayer() {
    playerBody = defineSquare();
  }

  private Body defineSquare() {
    BodyDef def = new BodyDef();
    def.type = BodyDef.BodyType.DynamicBody;
    def.position.set(toBox2d(100), toBox2d(50));
    def.fixedRotation = true;
    Body body = world.createBody(def);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(toBox2d(32 / 2), toBox2d(32 / 2));

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 6.0f;
    body.createFixture(fixtureDef);
    shape.dispose();
    return body;
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    update(delta);

    game.setProjectionMatrix(hud.stage.getCamera());
    hud.stage.draw();

    debugRenderer.render(world, scale(camera.combined));
    //Step at the end of the render method
    world.step(1/60f, 6, 2);
  }

  private void update(float delta) {
    updateCamera();
    hud.update(delta);

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && playerBody.getLinearVelocity().x <= 5) {
      playerBody.applyLinearImpulse(0.8f, 0, playerBody.getPosition().x, playerBody.getPosition().y, true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && playerBody.getLinearVelocity().x >= -5) {
      playerBody.applyLinearImpulse(-0.8f, 0, playerBody.getPosition().x, playerBody.getPosition().y, true);
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      playerBody.applyForceToCenter(0, 400, true);
    }
  }

  private void updateCamera() {
    camera.position.x = fromBox2d(playerBody.getPosition().x);
    camera.position.y = fromBox2d(playerBody.getPosition().y);
    camera.update();

    mapRenderer.render(camera);
  }

  private float toBox2d(float m) {
    return m / PPM;
  }

  private float fromBox2d(float m) {
    return m * PPM;
  }

  private Matrix4 scale(Matrix4 combined) {
    return combined.scl(PPM);
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    hud.dispose();
  }
}
