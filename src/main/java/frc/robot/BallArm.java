package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;;

import edu.wpi.first.wpilibj.Spark;

public class BallArm {

  private static BallArm instance;

  private final Spark topRoller;
  private final Spark bottomRoller;


  private DoubleSolenoid wrist = new DoubleSolenoid(1, 2);
  private DoubleSolenoid mastTilt = new DoubleSolenoid(3, 4);
  private DoubleSolenoid mastExtend = new DoubleSolenoid(5,6);



  private BallArm() {
    topRoller = new Spark(Constants.TOP_ROLLER_PWM);
    bottomRoller = new Spark(Constants.BOTTOM_ROLLER_PWM);

    topRoller.setInverted(Constants.TOP_ROLLER_INVERTED);
    bottomRoller.setInverted(Constants.BOTTOM_ROLLER_INVERTED);
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

  public void intake(double topSpeed, double bottomSpeed) {
    topRoller.set(topSpeed);
    bottomRoller.set(-bottomSpeed);
  }

  public void outtake(double topSpeed, double bottomSpeed) {
    topRoller.set(-topSpeed);
    bottomRoller.set(bottomSpeed);
  }

  public void stop() {
    intake(0, 0);
  }

  public void wristTilt(boolean tiltUp) {
    wrist.set( tiltUp ? Value.kForward : Value.kReverse);
  }

  public void mastExtend(boolean extended) {
    mastExtend.set( extended ? Value.kForward : Value.kReverse);
  }

  public void mastTilt(boolean forward) {
    mastTilt.set(forward ? Value.kForward : Value.kReverse);
    
  }
}