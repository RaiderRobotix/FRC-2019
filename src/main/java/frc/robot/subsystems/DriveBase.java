package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.DriveBase.DriveWithJoysticks;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.ArrayList;

public class DriveBase extends Subsystem {
  
  private static DriveBase m_instance;

  private final CANSparkMax leftFrontSpark;
  private final CANSparkMax leftBackSpark;
  private final CANSparkMax rightFrontSpark;
  private final CANSparkMax rightBackSpark;

  private final CANEncoder leftEncoder;
  private final CANEncoder rightEncoder;

  private DriveBase() {
    this.leftFrontSpark = new CANSparkMax(Constants.LEFT_FRONT_DRIVE_CAN_ID, MotorType.kBrushless);
    this.leftBackSpark = new CANSparkMax(Constants.LEFT_BACK_DRIVE_CAN_ID, MotorType.kBrushless);
    this.rightFrontSpark
      = new CANSparkMax(Constants.RIGHT_FRONT_DRIVE_CAN_ID, MotorType.kBrushless);
    this.rightBackSpark = new CANSparkMax(Constants.RIGHT_BACK_DRIVE_CAN_ID, MotorType.kBrushless);

    this.leftFrontSpark.setInverted(Constants.LEFT_DRIVE_MOTORS_INVERTED);
    this.rightFrontSpark.setInverted(Constants.RIGHT_DRIVE_MOTORS_INVERTED);

    leftBackSpark.follow(leftFrontSpark);
    rightBackSpark.follow(rightFrontSpark);

    leftEncoder = leftFrontSpark.getEncoder();
    rightEncoder = rightFrontSpark.getEncoder();
  }

  /**
   * @return The DriveBase singleton.
   */
  public static DriveBase getInstance() {
    if (m_instance == null) {
      m_instance = new DriveBase();
    }

    return m_instance;
  }

  public void setSpeed(double speed) {
    setSpeed(speed, speed);
  }

  public void setSpeed(double leftSpeed, double rightSpeed) {
    this.leftFrontSpark.set(leftSpeed);
    this.rightFrontSpark.set(rightSpeed);
  }

  public double getLeftDistance() {
    return this.leftEncoder.getPosition() * Constants.INCHES_PER_REVOLUTION;
  }

  public double getRightDistance() {
    return this.rightEncoder.getPosition() * Constants.INCHES_PER_REVOLUTION * -1.0;
  }

  public ArrayList<String[]> getCanIdFirmwarePairs() {
    ArrayList<String[]> pairs = new ArrayList<String[]>();
    pairs.add(new String[]{"Drive CAN ID Left Front " + Constants.LEFT_FRONT_DRIVE_CAN_ID, this.leftFrontSpark.getFirmwareString()});
    pairs.add(new String[]{"Drive CAN ID Left Back  " + Constants.LEFT_BACK_DRIVE_CAN_ID, this.leftBackSpark.getFirmwareString()});
    pairs.add(new String[]{"Drive CAN ID Right Front" + Constants.RIGHT_FRONT_DRIVE_CAN_ID, this.rightFrontSpark.getFirmwareString()});
    pairs.add(new String[]{"Drive CAN ID Right Back " + Constants.RIGHT_BACK_DRIVE_CAN_ID, this.rightBackSpark.getFirmwareString()});
    return pairs;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new DriveWithJoysticks());
  }
}