package greenmoonsoftware.gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GreenMoonTiledRenderer {
  private TiledMap tiledMap;
  private TiledMapRenderer tiledMapRenderer;

  public GreenMoonTiledRenderer(String resource) {
    tiledMap = new TmxMapLoader().load(resource);
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
  }

  public void render(OrthographicCamera camera) {
    tiledMapRenderer.setView(camera);
    tiledMapRenderer.render();
  }
}
