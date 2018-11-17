package greenmoonsoftware.gdx.ailounge;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import greenmoonsoftware.gdx.GreenMoonGame;
import greenmoonsoftware.gdx.characters.Character;

public class AiLoungeGame extends GreenMoonGame {
  private BitmapFont font;
  private PlainScreen screen;
  private Character deadpool;

  public void onCreate() {
    font = new BitmapFont();
    this.setScreen(new PlainScreen(this));

    deadpool = new Character("characters/deadpool.png", 300f, 300f);
  }

  @Override
  public void render() {
    deadpool.render();

  }
}
