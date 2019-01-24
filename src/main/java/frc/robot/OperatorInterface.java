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

    // =========== ELEVATOR ==========

    // Temporary Manual Control for testing until encoder values can be retrieved
    // so we can use the commented out logic below.
    if (operatorStick.getRawButtonPressed(11)) {
    	Constants.LEFT_ELEVATOR_INVERTED = !Constants.LEFT_ELEVATOR_INVERTED;
    }
    if (operatorStick.getRawButtonPressed(12)) {
    	Constants.RIGHT_ELEVATOR_INVERTED = !Constants.RIGHT_ELEVATOR_INVERTED;
    }
    double operatorY = this.getOperatorY();
    //if (Math.abs(operatorY) > Constants.JOYSTICK_DEADBAND) {
      this.elevator.setSpeed(operatorY);
    //} else {
      //this.elevator.setSpeed(0.0);
    //}
    
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
  }

  public double getLeftY() {
    return this.leftStick.getY();
  }

  public double getRightY() {
    return this.rightStick.getY();
  }

  public double getOperatorY() {
    return this.operatorStick.getY();
  }

  public boolean getOperatorButton(int button) {
    return this.operatorStick.getRawButton(button);
  }
}
