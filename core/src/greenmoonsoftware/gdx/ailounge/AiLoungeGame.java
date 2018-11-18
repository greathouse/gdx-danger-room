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
  private AiLoungeInputProcessor inputProcessor;

  public void onCreate() {
    font = new BitmapFont();
    this.setScreen(new PlainScreen(this));

    deadpool = new Character(this,"characters/deadpool.png", 300f, 300f);
    inputProcessor = new AiLoungeInputProcessor();

    addKeyMaps();
    Gdx.input.setInputProcessor(inputProcessor);
  }

  private void addKeyMaps() {
    addCharacterKeyMap(deadpool, Input.Keys.LEFT, Input.Keys.UP, Input.Keys.RIGHT, Input.Keys.DOWN);
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

  }
}
