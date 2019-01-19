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
  
  // Joysticks
  private final Joystick leftStick;
  private final Joystick rightStick;
  private final Joystick operatorStick;

  private OperatorInterface() {
    this.drives = DriveBase.getInstance();

    this.leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
    this.rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
    this.operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT)
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

  public void teleop() {
    // =========== DRIVES ===========
    this.drives.setSpeed(this.getLeftY(), this.getRightY());
  }

  public double getLeftY() {
    return this.leftStick.getY();
  }

  public double getRightY() {
    return this.rightStick.getY();
  }
}
