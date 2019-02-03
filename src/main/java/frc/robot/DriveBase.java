/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveBase {

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
    this.leftFrontSpark.set(leftSpeed * (Constants.LEFT_DRIVE_MOTORS_INVERTED ? -1.0 : 1.0));
    this.rightFrontSpark.set(rightSpeed * (Constants.RIGHT_DRIVE_MOTORS_INVERTED ? -1.0 : 1.0));
  }

  public double getLeftDistance() {
    return this.leftEncoder.getPosition() * Constants.INCHES_PER_COUNT;
  }

  public double getRightDistance() {
    return this.rightEncoder.getPosition() * Constants.INCHES_PER_COUNT * (Constants.RIGHT_DRIVE_MOTORS_INVERTED ? 1.0 : -1.0);
  }
}