package greenmoonsoftware.gdx;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class GreenMoonControllerLogger implements ControllerListener {
  @Override
  public void connected(Controller controller) {
    System.out.println("Controller Connected");
  }

  @Override
  public void disconnected(Controller controller) {
    System.out.println("Controller disconnected");
  }

  @Override
  public boolean buttonDown(Controller controller, int buttonIndex) {
    System.out.println("Button Down: " + buttonIndex);
    return false;
  }

  @Override
  public boolean buttonUp(Controller controller, int buttonIndex) {
    System.out.println("Button Up: " + buttonIndex);
    return false;
  }

  @Override
  public boolean axisMoved(Controller controller, int axisIndex, float value) {
    System.out.println("Axis Moved: " + axisIndex + " - " + value);
    return false;
  }

  @Override
  public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
    System.out.println("POV Moved: " + povIndex + " - " + value);
    return false;
  }

  @Override
  public boolean xSliderMoved(Controller controller, int sliderIndex, boolean value) {
    System.out.println("X Slider Moved: " + sliderIndex + " - " + value);
    return false;
  }

  @Override
  public boolean ySliderMoved(Controller controller, int sliderIndex, boolean value) {
    System.out.println("Y Slider Moved: " + sliderIndex + " - " + value);
    return false;
  }

  @Override
  public boolean accelerometerMoved(Controller controller, int accelerometerIndex, Vector3 value) {
    System.out.println("Accelerometer Moved: " + accelerometerIndex + " - " + value);
    return false;
  }
}
