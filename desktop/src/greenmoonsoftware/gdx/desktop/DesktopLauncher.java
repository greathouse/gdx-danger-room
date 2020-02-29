package greenmoonsoftware.gdx.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;
import greenmoonsoftware.gdx.ailounge.AiLoungeGame;
import greenmoonsoftware.gdx.battlegrounds.BattleGroundsGame;
import greenmoonsoftware.gdx.dangerroom.DangerRoomGame;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new DangerRoomGame(), config);
		new LwjglApplication(new AiLoungeGame(), config);
//		new LwjglApplication(new BattleGroundsGame(), config);
	}
}
