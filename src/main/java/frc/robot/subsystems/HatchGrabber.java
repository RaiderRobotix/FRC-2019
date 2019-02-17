package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.HatchGrabber.DefaultHatchGrabberCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.Solenoid;

public class HatchGrabber extends Subsystem {
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

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DefaultHatchGrabberCommand());
  }
}
