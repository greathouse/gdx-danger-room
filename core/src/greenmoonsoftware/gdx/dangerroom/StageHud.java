package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StageHud {
//  private OrthographicCamera textCamera;
  private BitmapFont font;

  private Stage stage;
  private Viewport viewport;

  private Label countdownLabel;

  public StageHud() {
    float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
    float viewPortWidth = 20;
    float viewPortHeight = viewPortWidth * aspectRatio;

    float cameraViewPortWidth = 1024; // Set the size of the viewport for the text to something big
//    float cameraViewPortHeight = cameraViewPortWidth * aspectRatio;
//    textCamera = new OrthographicCamera(cameraViewPortWidth, cameraViewPortHeight);
    font = buildFont("Comic Sans MS.ttf", 18, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*");

    viewport = new FitViewport(cameraViewPortWidth, cameraViewPortWidth, new OrthographicCamera());
    stage = new Stage(viewport);

    //define a table used to organize our hud's labels
    Table table = new Table();
    //Top-Align table
    table.top();
    //make the table fill the entire stage
    table.setFillParent(true);

    //define our labels using the String, and a Label style consisting of a font and color
    countdownLabel = new Label(String.format("%03d", 6969), new Label.LabelStyle(font, Color.WHITE));
    table.add(countdownLabel).expandX();
    stage.addActor(table);
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
    stage.draw();
  }
}
