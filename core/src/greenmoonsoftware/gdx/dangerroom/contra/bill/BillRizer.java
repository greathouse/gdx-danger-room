package greenmoonsoftware.gdx.dangerroom.contra.bill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import greenmoonsoftware.gdx.GreenMoonGame;

public class BillRizer extends Sprite {
  private final GreenMoonGame game;
  private Body playerBody;

  private float stateTimer = 0f;

  private Animations animations;
  private float x, y;

  private boolean isRight = true;
  private GunState gunState = GunState.NONE;
  private GunDirection gunDirection = GunDirection.STRAIGHT;

  private enum MovementState {
    STANDING,
    JUMPING,
    RUNNING,
    PRONE,
    CLIMBING,
    SHOW_BOATING
  }

  private enum GunState {
    NONE, SHOOTING
  }

  private enum GunDirection {
    STRAIGHT, UP, DOWN, UP_DIAGONAL, DOWN_DIAGONAL
  }

  public BillRizer(GreenMoonGame game, Texture spriteSheet) {
    this.game = game;
    this.animations = new Animations(spriteSheet);
    playerBody = defineBody();
  }

  public BillRizer update(float delta) {
    float bodyx = game.fromBox2d(playerBody.getPosition().x);
    float bodyy = game.fromBox2d(playerBody.getPosition().y);
    setBounds(bodyx - getWidth() / 2,bodyy - getHeight() / 2,1,1);
    setOriginCenter();
    this.x = bodyx - getWidth() / 2;
    this.y = bodyy - getHeight() / 2;

    return this;
  }

  public void render(SpriteBatch batch) {
    stateTimer += Gdx.graphics.getDeltaTime();
    TextureRegion currentFrame = getCurrentAnimation().getKeyFrame(stateTimer, true);
    if (currentFrame.isFlipX() && isRight) {
      currentFrame.flip(true, false);
    }
    if (!currentFrame.isFlipX() && !isRight) {
      currentFrame.flip(true, false);
    }
    batch.draw(currentFrame, x, y, 1, 1);
  }

  private Animation<TextureRegion> getCurrentAnimation() {
    MovementState movementState = getMovementState();
    if (movementState.equals(MovementState.STANDING)) {
      if (gunState.equals(GunState.SHOOTING)) {
        return animations.getStandingShooting();
      }
      return animations.getStand();
    }
    if (movementState.equals(MovementState.JUMPING)) {
      return animations.getJumping();
    }
    if (movementState.equals(MovementState.RUNNING)) {
      if (gunState.equals(GunState.SHOOTING)) {
        return animations.getRunningShooting();
      }
      return animations.getRun();
    }
    return animations.getStand();
  }

  private MovementState getMovementState() {
    if (playerBody.getLinearVelocity().y < -0.2 || playerBody.getLinearVelocity().y > 0.2 ) {
      return MovementState.JUMPING;
    }
    if (playerBody.getLinearVelocity().x != 0) {
      return MovementState.RUNNING;
    }
    return MovementState.STANDING;
  }

  private Body defineBody() {
    BodyDef def = new BodyDef();
    def.type = BodyDef.BodyType.DynamicBody;
    def.position.set(1, 2);
    def.fixedRotation = true;
    Body body = game.createBody(def);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(.5f, .5f);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0.5f;
    body.createFixture(fixtureDef);
    shape.dispose();
    return body;
  }

  public BillRizer moveRight() {
    isRight = true;
    if (playerBody.getLinearVelocity().x <= 5) {
      playerBody.applyLinearImpulse(new Vector2(0.8f, 0), playerBody.getWorldCenter(), true);
    }
    return this;
  }

  public BillRizer moveLeft() {
    isRight = false;
    if (playerBody.getLinearVelocity().x >= -5) {
      playerBody.applyLinearImpulse(new Vector2(-0.8f, 0), playerBody.getWorldCenter(), true);
    }
    return this;
  }

  public BillRizer jump() {
    playerBody.applyLinearImpulse(new Vector2(0f, 4f), playerBody.getWorldCenter(), true);
    return this;
  }

  public BillRizer shooting() {
    gunState = GunState.SHOOTING;
    return this;
  }

  public BillRizer notShooting() {
    gunState = GunState.NONE;
    return this;
  }

  @Override
  public float getX() {
    return playerBody.getPosition().x;
  }

  @Override
  public float getY() {
    return playerBody.getPosition().y;
  }
}
