/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

  private static OperatorInterface m_instance;

  // Robot Subsystems
  private BallArm ballArm;
  private DriveBase drives;
  private Elevator elevator;
  private HatchGrabber grabber;

  // Joysticks
  private final Joystick leftStick;
  private final Joystick rightStick;
  private final Joystick operatorStick;

  // State Variables
  // private boolean elevatorPresetDone = true;
  // private double elevatorPresetHeight;

  private OperatorInterface() {
    this.ballArm = BallArm.getInstance();
    this.drives = DriveBase.getInstance();
    this.elevator = Elevator.getInstance();
    this.grabber = HatchGrabber.getInstance();

    this.leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
    this.rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
    this.operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT);
  }

  /**
   * @return The OperatorInterface singleton.
   */
  public static OperatorInterface getInstance() {
    if (m_instance == null) {
      m_instance = new OperatorInterface();
    }

    return m_instance;
  }

  /**
   * Maps driver/operator joystick inputs to robot functions.
   */
  public void teleop() {
    // =========== DRIVES ===========
    this.drives.setSpeed(this.getLeftY(), this.getRightY());


    // =========== HATCH GRABBER ===========    
    if (getRightButton(10)) {
      grabber.grab(); 
    }
    else if (getRightButton(11)) {
      grabber.release();
    }

    // =========== BALL ARM =========== 
    if (getOperatorButton(9)) {
      ballArm.extend();; 
    }
    else if (getOperatorButton(10)) {
      ballArm.contract();;
    }

    if (getOperatorButton(11)) {
      ballArm.tiltDown();;
    }
    else if (getOperatorButton(12)) {
      ballArm.tiltUp();;
    }

    if (getOperatorButton(4)) {
      ballArm.wristDown();;
    }
    else if (getOperatorButton(6)) {
      ballArm.wristUp();; 
    }

    if (getOperatorTrigger()) {
      ballArm.intake(0.20);
    } else if (getOperatorButton(2)) {
      ballArm.eject(1.0);
    } else {
      ballArm.stop();
    }

    // =========== ELEVATOR ==========
    if (getOperatorButton(5)) {
      elevator.tiltForward();
    } 
    else if (getOperatorButton(3)) {
      elevator.tiltBack();
    }

    // Temporary Manual Control for testing until encoder values can be retrieved
    // so we can use the commented out logic below.
    this.elevator.setSpeed(getOperatorY());

    if (rightStick.getRawButton(8)) {
      this.elevator.resetEncoder();
    }
    
    // if (this.getOperatorY() > Constants.JOYSTICK_DEADBAND 
    //     && (this.elevator.getHeight() > Constants.ELEVATOR_LOWER_LIMIT
    //       || getOperatorButton(Constants.OPERATOR_OVERRIDE_BUTTON))) {
    //   elevatorPresetDone = true;
    //   this.elevator.setSpeed(this.getOperatorY() * Constants.ELEVATOR_MANUAL_DOWN_RATE * -1.0);
    // } else if (this.getOperatorY() < -1.0 * Constants.JOYSTICK_DEADBAND
    //     && (this.elevator.getHeight() < Constants.ELEVATOR_UPPER_LIMIT
    //         || getOperatorButton(Constants.OPERATOR_OVERRIDE_BUTTON))) {
    //   elevatorPresetDone = true;
    //   this.elevator.setSpeed(this.getOperatorY() * Constants.ELEVATOR_MANUAL_UP_RATE * -1.0);
    // } else if (getOperatorButton(11)) { // Lower Rocket Height
    //   elevatorPresetDone = false;
    //   elevatorPresetHeight = Constants.ELEVATOR_LOW_PRESET;
    // } else if (getOperatorButton(9)) { // Middle Rocket Height
    //   elevatorPresetDone = false;
    //   elevatorPresetHeight = Constants.ELEVATOR_MIDDLE_PRESET;
    // } else if (getOperatorButton(7)) { // Top Rocket Height
    //   elevatorPresetDone = false;
    //   elevatorPresetHeight = Constants.ELEVATOR_HIGH_PRESET;
    // } else if (!elevatorPresetDone) { // Moving Automatically
    //   elevatorPresetDone = this.elevator.goToHeight(elevatorPresetHeight);
    // } else {
    //   this.elevator.setSpeed(0.0);
    // }
  }

  public double getLeftY() {
    double ret = leftStick.getY();
    if (Math.abs(ret) > Constants.JOYSTICK_DEADBAND) {
      return ret;
    }

    return 0.0;
  }

  public double getRightY() {
    double ret = rightStick.getY();
    if (Math.abs(ret) > Constants.JOYSTICK_DEADBAND) 
    {
      return ret;
    }

    return 0.0;
  }

  /**
   * Down on Joystick is positive, up is negative
   * @return
   */
  public double getOperatorY() {
    return this.operatorStick.getY();
  }

  public boolean getRightButton(int button) {
    return this.rightStick.getRawButton(button);
  }

  public boolean getOperatorButton(int button) {
    return this.operatorStick.getRawButton(button);
  }

  public boolean getOperatorTrigger() {
    return this.operatorStick.getTrigger();
  }
}
