package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import greenmoonsoftware.gdx.GreenMoonBox2dGame;
import greenmoonsoftware.gdx.dangerroom.contra.bill.BillRizer;

public class FirstRoomScreen implements Screen {
  private final GreenMoonBox2dGame game;
  private OrthographicCamera camera;
  private Viewport gamePort;

  private BillRizer rizer;

  public FirstRoomScreen(GreenMoonBox2dGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
    camera.update();

    gamePort = new FitViewport(400 / 100, 245 / 100, camera);

    rizer = new BillRizer(game, new Texture(Gdx.files.internal("contra/Contra3Players.png")));
  }

  @Override
  public void render(float delta) {
    update(delta);
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();

    game.setProjectionMatrix(camera);

    game.doInBatch(new GreenMoonBox2dGame.BatchAction() {
      @Override
      public void execute(SpriteBatch batch) {
        rizer.render(batch);
      }
    });
  }

  private void update(float delta) {
    rizer.update(delta);
  }

  @Override
  public void show() {

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
