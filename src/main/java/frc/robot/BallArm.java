package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class BallArm {

  private static BallArm instance;

  private final Spark topRoller;
  private final Spark bottomRoller;

  private BallArm() {
    topRoller = new Spark(Constants.TOP_ROLLER_PWM);
    bottomRoller = new Spark(Constants.BOTTOM_ROLLER_PWM);
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

}