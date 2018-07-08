package greenmoonsoftware.gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class GreenMoonTiledRenderer {
  private final GreenMoonGame game;
  private final World world;
  private TiledMap map;
  private TiledMapRenderer tiledMapRenderer;

  public GreenMoonTiledRenderer(String resource, GreenMoonGame game) {
    this.game = game;
    map = new TmxMapLoader().load(resource);
    this.world = game.getWorld();
    tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
  }

  public void render(OrthographicCamera camera) {
    tiledMapRenderer.setView(camera);
    tiledMapRenderer.render();

    mapWorld();
  }

  private void mapWorld() {
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    Body body;

    //create ground bodies/fixtures
    for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
      Rectangle rect = ((RectangleMapObject) object).getRectangle();

      bdef.type = BodyDef.BodyType.StaticBody;
      bdef.position.set(game.toBox2d(rect.getX() + rect.getWidth() / 2), game.toBox2d(rect.getY() + rect.getHeight() / 2));

      body = world.createBody(bdef);

      shape.setAsBox(game.toBox2d(rect.getWidth() / 2), game.toBox2d(rect.getHeight() / 2));
      fdef.shape = shape;
      body.createFixture(fdef);
    }
  }
}
