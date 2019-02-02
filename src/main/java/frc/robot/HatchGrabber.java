package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class HatchGrabber {

  private static HatchGrabber m_instance;

  private Solenoid grabberSolenoid;

  private HatchGrabber() {

    this.grabberSolenoid = 
      new Solenoid(Constants.PCM_CAN_ADDRESS, Constants.HATCH_GRABBER_SOLENOID);
    this.grabberSolenoid.set(false);
  }

  /**
   * @return The HatchGrabber singleton.
   */
  public static HatchGrabber getInstance() {
    if (m_instance == null) {
      m_instance = new HatchGrabber();
    }

    return m_instance;
  }
  
  public void release() {
    this.grabberSolenoid.set(false);
  }

  public void grab() {
    this.grabberSolenoid.set(true);
  }
}