package greenmoonsoftware.gdx.dangerroom.contra;

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
import com.badlogic.gdx.utils.Array;
import greenmoonsoftware.gdx.GreenMoonGame;

public class BillRizer extends Sprite {
  private final GreenMoonGame game;
  private Body playerBody;
  private Texture spriteSheet;
  private float stateTimer = 0f;
  private Animation<TextureRegion> run;
  private Animation<TextureRegion> standingShooting;
  private Animation<TextureRegion> jumping;
  private Animation<TextureRegion> runningShooting;
  private Animation<TextureRegion> shootingUpDiaganol;
  private Animation<TextureRegion> shootingDownDiagonal;
  private Animation<TextureRegion> die;
  private Animation<TextureRegion> stand;

  private Animation<TextureRegion> currentAnimation;
  private float x, y;

  private boolean isRight = true;


  public BillRizer(GreenMoonGame game, Texture spriteSheet) {
    this.game = game;
    this.spriteSheet = spriteSheet;
    defineAnimations();
    currentAnimation = stand;
    playerBody = defineBody();
  }

  private void defineAnimations() {
    stand();
    standingShooting();
    jumping();
    running();
    runningShooting();
    shootingUpDiaganol();
    shootingDownDiagonal();
    die();
  }

  private void stand() {
    stand = animation(0.0f, region(3, 2, 32, 40));
    stand.setPlayMode(Animation.PlayMode.NORMAL);
  }

  private void die() {
    die = animation(0.5f,
      region(16, 294, 32, 27),
      region(55, 304, 48, 13)
    );
  }

  private void shootingDownDiagonal() {
    shootingDownDiagonal = animation(0.18f,
      region(11, 225, 27, 42),
      region(47, 225, 26, 42),
      region(78, 225, 27,42),
      region(116, 226, 26, 42)
    );
  }

  private void shootingUpDiaganol() {
    shootingUpDiaganol = animation(0.18f,
      region(8, 170, 28, 46),
      region(47, 170, 23, 46),
      region(70, 172, 28, 46),
      region(107, 172, 24, 46)
    );
  }

  private void runningShooting() {
    runningShooting = animation( 0.1f,
      region(7, 126, 40, 40),
      region(47, 126, 40, 40),
      region(90, 126, 41, 40),
      region(134, 126, 40, 40)
    );
  }

  private void running() {
    run = animation(0.18f,
      region(7, 81, 30, 39),
      region(44, 81, 29, 39),
      region(78, 81, 34, 39),
      region(118, 81, 29, 39)
    );
  }

  private void standingShooting() {
    standingShooting = animation(0.1f,
      region(3, 2, 32, 40),
      region(41, 2, 32, 40),
      region(75, 2, 40, 40)
    );
  }

  private void jumping() {
    jumping = animation(0.1f,
      region(2, 49, 24, 20),
      region(30, 48, 20, 24),
      region(59, 48, 24, 20),
      region(93, 47, 20, 24)
    );
  }

  private Animation<TextureRegion> animation(float frameDuration, TextureRegion... regions) {
    Array<TextureRegion> frames = new Array<TextureRegion>();
    frames.addAll(regions);
    return new Animation<TextureRegion>(frameDuration, frames);
  }

  private TextureRegion region(int x, int y, int width, int height) {
    return new TextureRegion(this.spriteSheet, x, y, width, height);
  }

  public BillRizer update(float delta) {
    float bodyx = game.fromBox2d(playerBody.getPosition().x);
    float bodyy = game.fromBox2d(playerBody.getPosition().y);
    setBounds(bodyx - getWidth() / 2,bodyy - getHeight() / 2,1,1);
    setOriginCenter();
    setRegion(getFrame(delta));
    this.x = 10 - getWidth() / 2;
    this.y = 5 - getHeight() / 2;

    return this;
  }

  public void render(SpriteBatch batch) {
    stateTimer += Gdx.graphics.getDeltaTime();
    TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTimer, true);
    if (currentFrame.isFlipX() && isRight) {
      currentFrame.flip(true, false);
    }
    if (!currentFrame.isFlipX() && !isRight) {
      currentFrame.flip(true, false);
    }
    batch.draw(currentFrame, x, y, 1, 1);
  }

  private TextureRegion getFrame(float delta) {
    stateTimer += delta;
    return run.getKeyFrame(stateTimer, true);
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
    currentAnimation = run;
    if (playerBody.getLinearVelocity().x <= 5) {
      playerBody.applyLinearImpulse(new Vector2(0.8f, 0), playerBody.getWorldCenter(), true);
    }
    return this;
  }

  public BillRizer moveLeft() {
    isRight = false;
    currentAnimation = run;
    if (playerBody.getLinearVelocity().x >= -5) {
      playerBody.applyLinearImpulse(new Vector2(-0.8f, 0), playerBody.getWorldCenter(), true);
    }
    return this;
  }

  public BillRizer jump() {
    playerBody.applyLinearImpulse(new Vector2(0f, 4f), playerBody.getWorldCenter(), true);
    return this;
  }

  public Vector2 getPosition() {
    return playerBody.getPosition();
  }

  @Override
  public float getX() {
    return x;
  }

  @Override
  public float getY() {
    return y;
  }
}
