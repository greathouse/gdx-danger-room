package greenmoonsoftware.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public class DebugHud {
  private BitmapFont font;

  private Stage stage;
  private Viewport viewport;

  private List<BodyDebug> bodies = new ArrayList<BodyDebug>();

  private final Table table;
  private final Label.LabelStyle skin;

  public DebugHud() {
    float cameraViewPortWidth = 1024; // Set the size of the viewport for the text to something big
    font = buildFont("Comic Sans MS.ttf", 24, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*");

    viewport = new FitViewport(cameraViewPortWidth, cameraViewPortWidth, new OrthographicCamera());
    stage = new Stage(viewport);

    //define a table used to organize our hud's labels
    table = new Table();
    stage.addActor(table);

    //make the table fill the entire stage
    table.setFillParent(true);
    table.setDebug(true);
    table.left().top();

    //define our labels using the String, and a Label style consisting of a font and color
    skin = new Label.LabelStyle(font, Color.WHITE);
  }

  private static BitmapFont buildFont(String filename, int size, String characters) {
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(filename));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = size;
    parameter.characters = characters;
    parameter.kerning = true;
    parameter.magFilter = Texture.TextureFilter.Linear;
    parameter.minFilter = Texture.TextureFilter.Linear;
    BitmapFont font = generator.generateFont(parameter);
    font.getData().markupEnabled = true;
    generator.dispose();
    return font;
  }

  public void render () {
    update();
    stage.draw();
  }

  public void update() {
    for (BodyDebug b : bodies) {
      b.update();
    }
  }

  public DebugHud add(String name, Body body) {
    BodyDebug e = new BodyDebug(body, skin);
    table.row();
    table.add(new Label(name, skin));
    table.row();
    table.add(e.getTable());
    this.bodies.add(e);
    return this;
  }

  private static class BodyDebug {
    private final Body body;
    private Table table;
    private Label positionX;
    private Label positionY;

    private BodyDebug(Body body, Label.LabelStyle skin) {
      this.body = body;
      table = new Table();
      table.setFillParent(false);
      positionX = new Label(null, skin);
      positionY = new Label(null, skin);
      table.add(new Label("X:", skin));
      table.add(positionX).right();
      table.row();
      table.add(new Label("Y:", skin));
      table.add(positionY).right();
    }

    void update() {
      Vector2 position = body.getPosition();
      positionX.setText(String.format("%.4f", position.x));
      positionY.setText(String.format("%.4f", position.y));
    }

    Table getTable() {
      return this.table;
    }
  }
}
