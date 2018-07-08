package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import greenmoonsoftware.gdx.GreenMoonGame;
import greenmoonsoftware.gdx.GreenMoonTiledRenderer;
import greenmoonsoftware.gdx.dangerroom.contra.BillRizer;

public class Box2dScreenWithUnits implements Screen {

  private final GreenMoonGame game;
  private final OrthographicCamera camera;

  private final Hud hud;

  private GreenMoonTiledRenderer mapRenderer;

  private BillRizer rizer;

  public Box2dScreenWithUnits(GreenMoonGame game) {
    this.game = game.enableDebugRendering().setScale(1);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 20, 10);
    camera.update();

    hud = new Hud(game, 20, 10);

    mapRenderer = new GreenMoonTiledRenderer("gunner/Room2.tmx", game);

    rizer = new BillRizer(game, new Texture(Gdx.files.internal("contra/Contra3Players.png")));
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

    rizer.update(delta);
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

    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      rizer.moveRight();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      rizer.moveLeft();
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      rizer.jump();
    }
  }

  private void updateCamera() {
    camera.position.x = rizer.getPosition().x;
    camera.position.y = rizer.getPosition().y;
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
