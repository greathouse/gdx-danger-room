package greenmoonsoftware.gdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import greenmoonsoftware.gdx.ailounge.AiLoungeGame;
import greenmoonsoftware.gdx.dangerroom.DangerRoomGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new DangerRoomGame(), config);
		new LwjglApplication(new AiLoungeGame(), config);
	}
}
