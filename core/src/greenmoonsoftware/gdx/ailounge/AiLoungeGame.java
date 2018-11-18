package greenmoonsoftware.gdx.ailounge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import greenmoonsoftware.gdx.GreenMoonGame;
import greenmoonsoftware.gdx.characters.Character;
import greenmoonsoftware.gdx.characters.Direction;

public class AiLoungeGame extends GreenMoonGame {
  private BitmapFont font;
  private Character deadpool;
  private Character jedi;
  private AiLoungeInputProcessor inputProcessor;

  public void onCreate() {
    font = new BitmapFont();
    this.setScreen(new PlainScreen(this));

    deadpool = new Character(this,"characters/deadpool.png", 300f, 300f);
    jedi = new Character(this, "characters/jedi.png", 400f, 300f);
    inputProcessor = new AiLoungeInputProcessor();

    setupInputProcessor();
  }

  private void setupInputProcessor() {
    addCharacterKeyMap(deadpool, Input.Keys.LEFT, Input.Keys.UP, Input.Keys.RIGHT, Input.Keys.DOWN);
    addCharacterKeyMap(jedi, Input.Keys.A, Input.Keys.W, Input.Keys.D, Input.Keys.S);
    Gdx.input.setInputProcessor(inputProcessor);
  }

  private void addCharacterKeyMap(Character c, int left, int up, int right, int down) {
    inputProcessor
      .addKeyDown(left, (x) -> c.move(Direction.LEFT))
      .addKeyUp(left, (x) -> c.stop(Direction.LEFT))
      .addKeyDown(up, (x) -> c.move(Direction.UP))
      .addKeyUp(up, (x) -> c.stop(Direction.UP))
      .addKeyDown(right, (x) -> c.move(Direction.RIGHT))
      .addKeyUp(right, (x) -> c.stop(Direction.RIGHT))
      .addKeyDown(down, (x) -> c.move(Direction.DOWN))
      .addKeyUp(down, (x) -> c.stop(Direction.DOWN));
  }

  @Override
  public void render() {
    super.render();
    deadpool.render();
    jedi.render();
  }
}
