package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import greenmoonsoftware.gdx.GreenMoonBox2dGame;

public class DangerRoomGame extends GreenMoonBox2dGame {

	private BitmapFont font;

	public void onCreate() {
		super.onCreate();
		font = new BitmapFont();
		this.setScreen(new Box2dScreenWithUnits(this));
	}

	public void render() {
		super.render(); //important!
	}

	public void onDispose() {
		font.dispose();
	}
}
