package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import greenmoonsoftware.gdx.GreenMoonGame;
import greenmoonsoftware.gdx.GreenMoonTiledRenderer;
import greenmoonsoftware.gdx.dangerroom.contra.BillRizer;

public class Box2dScreenWithUnits implements Screen {

  private final GreenMoonGame game;
  private final OrthographicCamera camera;

  private final Hud hud;

  private GreenMoonTiledRenderer mapRenderer;

  private Body playerBody;

  private BillRizer rizer;

  public Box2dScreenWithUnits(GreenMoonGame game) {
    this.game = game.enableDebugRendering().setScale(1);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 20, 10);
    camera.update();

    hud = new Hud(game, 20, 10);

    mapRenderer = new GreenMoonTiledRenderer("gunner/Room2.tmx", game);

    definePlayer();
    rizer = new BillRizer(new Texture(Gdx.files.internal("contra/Contra3Players.png")));
  }

  private void definePlayer() {
    playerBody = defineSquare();
  }

  private Body defineSquare() {
    BodyDef def = new BodyDef();
    def.type = BodyDef.BodyType.DynamicBody;
    def.position.set(1, 2);
    def.fixedRotation = true;
    Body body = game.createBody(def);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(.5f, .5f);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0.5f;
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
    game.setProjectionMatrix(camera);
    game.setProjectionMatrix(hud.stage.getCamera());

    update(delta);

    hud.stage.draw();

    rizer.update(delta, game.fromBox2d(playerBody.getPosition().x), game.fromBox2d(playerBody.getPosition().y));
    game.doInBatch(new GreenMoonGame.BatchAction() {
      @Override
      public void execute(SpriteBatch batch) {
        rizer.render(batch);
      }
    });

    game.render(camera);
    game.step(Gdx.graphics.getDeltaTime(), 6, 2);
  }

  private void update(float delta) {
    updateCamera();
    hud.update(delta);

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && playerBody.getLinearVelocity().x <= 5) {
      playerBody.applyLinearImpulse(new Vector2(0.8f, 0),  playerBody.getWorldCenter(), true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && playerBody.getLinearVelocity().x >= -5) {
      playerBody.applyLinearImpulse(new Vector2(-0.8f, 0),  playerBody.getWorldCenter(), true);
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      playerBody.applyLinearImpulse(new Vector2(0f, 4f), playerBody.getWorldCenter(), true);
    }
  }

  private void updateCamera() {
    camera.position.x = playerBody.getPosition().x;
    camera.position.y = playerBody.getPosition().y;
//    camera.position.set(rizer.getX() + rizer.getWidth() / 2, rizer.getY() + rizer.getHeight() / 2, 0);
    camera.update();
    mapRenderer.render(camera);
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
