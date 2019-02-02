package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class BallArm {

  private static BallArm instance;

  private final CANSparkMax topRoller;
  private final CANSparkMax bottomRoller;

  private DoubleSolenoid wrist = new DoubleSolenoid(1, 2);
  private DoubleSolenoid mastTilt = new DoubleSolenoid(3, 4);
  private DoubleSolenoid mastExtend = new DoubleSolenoid(5,6);

  private BallArm() {
    topRoller = new CANSparkMax(Constants.TOP_ROLLER_CAN_ID, MotorType.kBrushless);
    bottomRoller = new CANSparkMax(Constants.BOTTOM_ROLLER_CAN_ID, MotorType.kBrushless);

    // topRoller.setInverted(Constants.TOP_ROLLER_INVERTED);
    // bottomRoller.setInverted(Constants.BOTTOM_ROLLER_INVERTED);
  }

  /**
   * 
   * @return The BallArm singleton.
   */
  public static BallArm getInstance() {
    if (instance == null) {
      instance = new BallArm();
    }
    
    return instance;
  }

  public void intake(double speed) {
    topRoller.set(speed);
    bottomRoller.set(-speed);
  }

  public void eject(double speed) {
    topRoller.set(-speed);
    bottomRoller.set(speed);
  }

  public void stop() {
    topRoller.set(0.0);
    bottomRoller.set(0.0);
  }

  public void wristUp() {
    wrist.set(DoubleSolenoid.Value.kForward);
  }

  public void wristDown() {
    wrist.set(DoubleSolenoid.Value.kReverse);
  }

  public void extend() {
    mastExtend.set(DoubleSolenoid.Value.kForward);
  }

  public void contract() {
    mastExtend.set(DoubleSolenoid.Value.kReverse);
  }

  public void tiltUp() {
    mastTilt.set(DoubleSolenoid.Value.kReverse);
  }

  public void tiltDown() {
    mastTilt.set(DoubleSolenoid.Value.kForward);
  }
}