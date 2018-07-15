package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class StagelessHud {
  private SpriteBatch batch;
  private OrthographicCamera textCamera;
  private BitmapFont font;

  public StagelessHud() {
    batch = new SpriteBatch();
    float aspectRatio = (float)Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
    float viewPortWidth = 20;
    float viewPortHeight = viewPortWidth * aspectRatio;

    float cameraViewPortWidth = 1024; // Set the size of the viewport for the text to something big
    float cameraViewPortHeight = cameraViewPortWidth * aspectRatio;
    textCamera = new OrthographicCamera(cameraViewPortWidth, cameraViewPortHeight);
    font = buildFont("pdark.ttf", 32, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*");
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
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    textCamera.update();

    batch.begin();
    batch.setProjectionMatrix(textCamera.combined);
    font.draw(batch, "ABC", 300 ,300);

    batch.end();
  }
}
