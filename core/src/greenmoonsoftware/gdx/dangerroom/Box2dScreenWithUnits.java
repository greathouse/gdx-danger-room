package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import greenmoonsoftware.gdx.DebugHud;
import greenmoonsoftware.gdx.GreenMoonBox2dGame;
import greenmoonsoftware.gdx.GreenMoonTiledRenderer;
import greenmoonsoftware.gdx.dangerroom.contra.bill.BillRizer;

public class Box2dScreenWithUnits implements Screen {

  private final GreenMoonBox2dGame game;
  private final OrthographicCamera camera;

  private GreenMoonTiledRenderer mapRenderer;

  private BillRizer rizer;
  private final DebugHud stageHud;

  public Box2dScreenWithUnits(GreenMoonBox2dGame game) {
    this.game = game.enableDebugRendering().setScale(1);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 20, 10);
    camera.update();

    mapRenderer = new GreenMoonTiledRenderer("gunner/Room2.tmx", game);

    rizer = new BillRizer(game, new Texture(Gdx.files.internal("contra/Contra3Players.png")));
    stageHud = new DebugHud();
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    game.setProjectionMatrix(camera);

    update(delta);

    stageHud.render();
    rizer.update(delta);
    game.doInBatch(batch -> rizer.render(batch));

    game.render(this.camera);
    game.step(Gdx.graphics.getDeltaTime(), 6, 2);
  }

  private void update(float delta) {
    updateCamera();

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      rizer.moveRight();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      rizer.moveLeft();
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      rizer.jump();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
      rizer.shooting();
    }
    if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
      rizer.notShooting();
    }
  }

  private void updateCamera() {
    camera.position.x = rizer.getX();
    camera.position.y = rizer.getY();
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
  }
}
