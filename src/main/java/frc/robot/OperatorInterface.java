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

    if (rightStick.getRawButton(10)) {
      elevator.grab(); 
    }
    else if (rightStick.getRawButton(10)) {
      elevator.release();
    }
    if (getOperatorButton(5)) {
      elevator.tiltSolenoid(true);
    } 
    else if (getOperatorButton(3)) {
      elevator.tiltSolenoid(false);
    }
    if (getOperatorButton(9)) {
      ballArm.mastExtend(true); 
    }
    else if (getOperatorButton(10)) {
      ballArm.mastExtend(false);
    }
    if (getOperatorButton(11)) {
      ballArm.mastTilt(true);
    }
    else if (getOperatorButton(12)) {
      ballArm.mastTilt(false);
    }
    if (getOperatorButton(4)) {
      ballArm.wristTilt(true);
    }
    else if (getOperatorButton(6)) {
      ballArm.wristTilt(false); 
    }

    /*
    if (getOperatorButton(3)) {
      ballArm.intake(0.5, -0.5);
      System.out.println("Outtake");
    } else if (getOperatorButton(4)) {
      ballArm.outtake(0.5, -0.5);
      System.out.println("Intake");
    } else if (getOperatorButton(6)) {
      ballArm.stop();
      System.out.println("Stopping");
    }
    */

    // =========== ELEVATOR ==========

    // Temporary Manual Control for testing until encoder values can be retrieved
    // so we can use the commented out logic below.
    this.elevator.setSpeed(getOperatorY());
    
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
    // } else if (getOperatorButton(12)) { // Lower Rocket Height
    //   elevatorPresetDone = false;
    //   elevatorPresetHeight = Constants.ELEVATOR_LOW_PRESET;
    // } else if (getOperatorButton(10)) { // Middle Rocket Height
    //   elevatorPresetDone = false;
    //   elevatorPresetHeight = Constants.ELEVATOR_MIDDLE_PRESET;
    // } else if (getOperatorButton(8)) { // Top Rocket Height
    //   elevatorPresetDone = false;
    //   elevatorPresetHeight = Constants.ELEVATOR_HIGH_PRESET;
    // } else if (!elevatorPresetDone) { // Moving Automatically
    //   elevatorPresetDone = this.elevator.goToHeight(elevatorPresetHeight);
    // } else {
    //   this.elevator.setSpeed(0.0);
    // }

    // if (getOperatorButton(8)) {
    //   this.ballArm.intake(0.5, 0.5);
    // }
  }

  public double getLeftY() {
    double ret = leftStick.getY();
    if (Math.abs(ret) > Constants.JOYSTICK_DEADBAND) return ret;
    return 0;
  }

  public double getRightY() {
    double ret = rightStick.getY();
    if (Math.abs(ret) > Constants.JOYSTICK_DEADBAND) return ret;
    return 0;
  }

  public double getOperatorY() {
    return this.operatorStick.getY();
  }

  public boolean getOperatorButton(int button) {
    return this.operatorStick.getRawButton(button);
  }

  public boolean getOperatorTrigger() {
    return this.operatorStick.getTrigger();
  }
}
