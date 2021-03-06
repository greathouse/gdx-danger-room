package greenmoonsoftware.gdx.dangerroom.contra.bill;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animations {
  private final Texture spriteSheet;

  private Animation<TextureRegion> run;
  private Animation<TextureRegion> standingShooting;
  private Animation<TextureRegion> jumping;
  private Animation<TextureRegion> runningShooting;
  private Animation<TextureRegion> shootingUpDiagonal;
  private Animation<TextureRegion> shootingDownDiagonal;
  private Animation<TextureRegion> die;
  private Animation<TextureRegion> stand;

  public Animations(Texture spriteSheet) {
    this.spriteSheet = spriteSheet;
    defineAnimations();
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
    shootingUpDiagonal = animation(0.18f,
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

  public Texture getSpriteSheet() {
    return spriteSheet;
  }

  public Animation<TextureRegion> getRun() {
    return run;
  }

  public Animation<TextureRegion> getStandingShooting() {
    return standingShooting;
  }

  public Animation<TextureRegion> getJumping() {
    return jumping;
  }

  public Animation<TextureRegion> getRunningShooting() {
    return runningShooting;
  }

  public Animation<TextureRegion> getShootingUpDiagonal() {
    return shootingUpDiagonal;
  }

  public Animation<TextureRegion> getShootingDownDiagonal() {
    return shootingDownDiagonal;
  }

  public Animation<TextureRegion> getDie() {
    return die;
  }

  public Animation<TextureRegion> getStand() {
    return stand;
  }
}
