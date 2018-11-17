package greenmoonsoftware.gdx.ailounge;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

public class AiEnemy implements Telegraph {
  @Override
  public boolean handleMessage(Telegram msg) {
    return true;
  }
}
