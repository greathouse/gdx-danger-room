package greenmoonsoftware.gdx.battlegrounds;

import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class BattleGroundsInputProcessor implements InputProcessor {
  private Map<Integer, Set<Consumer>> keyDownMap = new HashMap<>();
  private Map<Integer, Set<Consumer>> keyUpMap = new HashMap<>();

  public BattleGroundsInputProcessor addKeyUp(int key, Consumer c) {
    addAction(keyUpMap, key, c);
    return this;
  }

  public BattleGroundsInputProcessor addKeyDown(int key, Consumer c) {
    addAction(keyDownMap, key, c);
    return this;
  }

  private void addAction(Map<Integer, Set<Consumer>> map, Integer key, Consumer c) {
    Set<Consumer> actions = map.getOrDefault(key, new HashSet<>());
    actions.add(c);
    map.put(key, actions);
  }

  @Override
  public boolean keyDown(int keycode) {
    keyDownMap.getOrDefault(keycode, new HashSet<>()).forEach(x -> x.accept(null));
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    keyUpMap.getOrDefault(keycode, new HashSet<>()).forEach(x -> x.accept(null));
    return true;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }
}
