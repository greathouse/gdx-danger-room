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

public class Box2dScreen implements Screen {

  private static final int PPM = 32;
  private final GreenMoonGame game;
  private final OrthographicCamera camera;
  private final World world;
  private final Box2DDebugRenderer debugRenderer;
  private final Hud hud;

  private Body playerBody;

  public Box2dScreen(GreenMoonGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800 / 2, 480 / 2);
    camera.update();

    hud = new Hud(game, 800, 480);

    world = new World(new Vector2(0, -10), true);
    debugRenderer = new Box2DDebugRenderer();

    definePlayer();
    defineGround();
  }

  private void defineGround() {
    BodyDef groundBodyDef = new BodyDef();
    groundBodyDef.position.set(new Vector2(0, toBox2d(10)));

    Body groundBody = world.createBody(groundBodyDef);

    PolygonShape groundBox = new PolygonShape();
    groundBox.setAsBox(toBox2d(camera.viewportWidth), toBox2d(10.0f));
    groundBody.createFixture(groundBox, 0.0f);
    groundBox.dispose();
  }

  private void definePlayer() {
//    playerBody = defineCircle();
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

    body.createFixture(shape, 1.0f);
    shape.dispose();
    return body;
  }

  private Body defineCircle() {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(toBox2d(100), toBox2d(300));

    Body circleBody = world.createBody(bodyDef);

    CircleShape circle = new CircleShape();
    circle.setRadius(toBox2d(16f));

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    fixtureDef.restitution = 0.1f; // Make it bounce a little bit

    Fixture fixture = circleBody.createFixture(fixtureDef);

    circle.dispose();
    return circleBody;
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

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && playerBody.getLinearVelocity().x <= 2) {
      playerBody.applyLinearImpulse(0.8f, 0, playerBody.getPosition().x, playerBody.getPosition().y, true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && playerBody.getLinearVelocity().x >= -2) {
      playerBody.applyLinearImpulse(-0.8f, 0, playerBody.getPosition().x, playerBody.getPosition().y, true);
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      playerBody.applyForceToCenter(0, 300, true);
    }
  }

  private void updateCamera() {
    camera.position.x = fromBox2d(playerBody.getPosition().x);
    camera.position.y = fromBox2d(playerBody.getPosition().y);
    camera.update();
  }

  private float toBox2d(float m) {
//    return m;
    return m / PPM;
  }

  private float fromBox2d(float m) {
//    return m;
    return m * PPM;
  }

  private Matrix4 scale(Matrix4 combined) {
    return combined.scl(PPM);
//    return combined;
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
