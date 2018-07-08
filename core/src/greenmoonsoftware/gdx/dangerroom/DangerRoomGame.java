package greenmoonsoftware.gdx.dangerroom;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import greenmoonsoftware.gdx.GreenMoonGame;

public class DangerRoomGame extends GreenMoonGame {

	private BitmapFont font;

	public void onCreate() {
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
