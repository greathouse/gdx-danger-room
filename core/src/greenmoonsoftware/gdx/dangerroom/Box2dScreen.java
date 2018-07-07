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

  private final GreenMoonGame game;
  private final OrthographicCamera camera;
  private final World world;
  private final Box2DDebugRenderer debugRenderer;

  private Body playerBody;

  public Box2dScreen(GreenMoonGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800 / 2, 480 / 2);
    camera.update();

    world = new World(new Vector2(0, -10), true);
    debugRenderer = new Box2DDebugRenderer();

    definePlayer();
    defineGround();
  }

  private void defineGround() {
    BodyDef groundBodyDef = new BodyDef();
    groundBodyDef.position.set(new Vector2(0, scale(10)));

    Body groundBody = world.createBody(groundBodyDef);

    PolygonShape groundBox = new PolygonShape();
    groundBox.setAsBox(camera.viewportWidth, 10.0f);
    groundBody.createFixture(groundBox, 0.0f);
    groundBox.dispose();
  }

  private void definePlayer() {
    playerBody = defineCircle();
  }

  private Body defineSquare() {
    BodyDef def = new BodyDef();
    def.type = BodyDef.BodyType.DynamicBody;
    def.position.set(100, 300);
    return null;
  }

  private Body defineCircle() {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(scale(100), scale(300));

    Body circleBody = world.createBody(bodyDef);

    CircleShape circle = new CircleShape();
    circle.setRadius(scale(6f));

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0.4f;
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

    debugRenderer.render(world, scale(camera.combined));
    //Step at the end of the render method
    world.step(1/60f, 6, 2);
  }

  private void update(float delta) {
    updateCamera();
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      playerBody.applyLinearImpulse(0.8f, 0, playerBody.getPosition().x, playerBody.getPosition().y, true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      playerBody.applyLinearImpulse(-0.8f, 0, playerBody.getPosition().x, playerBody.getPosition().y, true);
    }
  }

  private void updateCamera() {
    camera.position.x = scaleUp(playerBody.getPosition().x);
    camera.position.y = scaleUp(playerBody.getPosition().y);
    camera.update();
  }

  private float scale(float m) {
    return m;
//    return m / 32;
  }

  private float scaleUp(float m) {
    return m;
//    return m * 32;
  }

  private Matrix4 scale(Matrix4 combined) {
//    combined.scl(32);
    return combined;
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

  }
}
