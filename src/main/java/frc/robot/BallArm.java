package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;

import java.util.ArrayList;

public class BallArm {

  private static BallArm instance;

  private final CANSparkMax topRoller;
  private final CANSparkMax bottomRoller;

  private final Spark wrist;

  private DoubleSolenoid ballPopper;
  private DoubleSolenoid mastTilt;
  private DoubleSolenoid mastExtend;

  private BallArm() {
    topRoller = new CANSparkMax(Constants.TOP_ROLLER_CAN_ID, MotorType.kBrushless);
    bottomRoller = new CANSparkMax(Constants.BOTTOM_ROLLER_CAN_ID, MotorType.kBrushless);

    wrist = new Spark(Constants.WRIST_PWM);

    ballPopper = new DoubleSolenoid(1, 5);
    mastTilt = new DoubleSolenoid(3, 4);
    mastExtend = new DoubleSolenoid(2, 6);
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

  public void popBallOut() {
    ballPopper.set(DoubleSolenoid.Value.kForward);
  }

  public void retractBallPopper() {
    ballPopper.set(DoubleSolenoid.Value.kReverse);
  }

  public void wristDown(double speed)
  {
    wrist.set(speed);
  }

  public void wristUp(double speed)
  {
    wrist.set(-speed);
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

  public ArrayList<String[]> getCanIdFirmwarePairs() {
    ArrayList<String[]> pairs = new ArrayList<String[]>();
    pairs.add(new String[]{"Ball Top Roller CAN ID Left " + Constants.TOP_ROLLER_CAN_ID, this.topRoller.getFirmwareString()});
    pairs.add(new String[]{"Ball Btm Roller CAN ID Right " + Constants.BOTTOM_ROLLER_CAN_ID, this.bottomRoller.getFirmwareString()});
   return pairs;
  }
}