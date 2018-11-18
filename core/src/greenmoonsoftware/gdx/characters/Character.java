package greenmoonsoftware.gdx.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import greenmoonsoftware.gdx.GreenMoonGame;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Consumer;

public class Character {
  private final GreenMoonGame game;
  private Texture walkSheet;
  private float stateTime;
  private float x, y;
  private Map<Integer, Consumer> keymap;

  private LinkedHashSet<Direction> activeDirections = new LinkedHashSet<Direction>();
  private Direction currentDirection = Direction.DOWN;

  private Map<Direction, Animation<TextureRegion>> directionAnimations = new HashMap<Direction, Animation<TextureRegion>>();

  public Character(GreenMoonGame game, String resource, float startX, float startY) {
    this.game = game;
    stateTime = 0f;
    x = startX;
    y = startY;

    ;
    walkSheet = new Texture(Gdx.files.internal(resource));

    directionAnimations.put(Direction.DOWN, walkAnimation(0));
    directionAnimations.put(Direction.UP, walkAnimation(144));
    directionAnimations.put(Direction.RIGHT, walkAnimation(96));
    directionAnimations.put(Direction.LEFT, walkAnimation(48));
  }

  public void render() {
    stateTime += Gdx.graphics.getDeltaTime();
    updateLocation();

    // Get current frame of animation for the current stateTime
    TextureRegion currentFrame = currentAnimation().getKeyFrame(stateTime, !activeDirections.isEmpty());

    game.doInBatch(batch -> batch.draw(currentFrame, x, y));
  }

  public void dispose() {
    walkSheet.dispose();
  }

  private void updateLocation() {
    activeDirections.forEach(this::calculateLocation);
  }

  private Animation<TextureRegion> currentAnimation() {
    return (directionAnimations.containsKey(currentDirection))
      ? directionAnimations.get(currentDirection)
      : directionAnimations.get(Direction.DOWN);
  }

  private void calculateLocation(Direction d) {
    if (d == null){
      return;
    }
    float[] moves = d.move();
    x += moves[0];
    y += moves[1];
  }

  public void move(Direction d) {
    currentDirection = d;
    activeDirections.add(d);
  }

  public void stop(Direction d) {
    activeDirections.remove(d);
  }

  boolean isColliding(Character other) {
    Rectangle me = new Rectangle(x,y, 32,48);
    Rectangle you = new Rectangle(other.x, other.y, 48,48);
    return me.overlaps(you);
  }

  private Animation<TextureRegion> walkAnimation(int y) {
    TextureRegion[] walkFrames = new TextureRegion[] {
      new TextureRegion(walkSheet, 0, y, 32, 48),
      new TextureRegion(walkSheet, 32, y, 32, 48),
      new TextureRegion(walkSheet, 64, y, 32, 48),
      new TextureRegion(walkSheet, 96, y, 32, 48)
    };
    return new Animation<TextureRegion>(0.150f, walkFrames);
  }

}

