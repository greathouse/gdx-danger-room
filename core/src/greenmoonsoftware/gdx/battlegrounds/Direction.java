package greenmoonsoftware.gdx.battlegrounds;

import com.badlogic.gdx.Gdx;

public enum Direction {
  STOP() {float[] move() {return new float[]{0,0};}},
  UP() {float[] move() {return new float[]{0,250 * Gdx.graphics.getDeltaTime()};}},
  DOWN() {float[] move() {return new float[]{0,-250 * Gdx.graphics.getDeltaTime()};}},
  LEFT() {float[] move() {return new float[]{-250 * Gdx.graphics.getDeltaTime(),0};}},
  RIGHT() {float[] move() {return new float[]{250 * Gdx.graphics.getDeltaTime(),0};}}
  ;

  abstract float[] move();

}
