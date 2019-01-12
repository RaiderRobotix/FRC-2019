/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Add your docs here.
 */
public class OI {

    public static final OI instance = new OI();

    DriveBase drives = DriveBase.getInstance();
    private final Joystick leftStick;
    private final Joystick rightStick;
    
    private OI() {
        leftStick  = new Joystick(Constants.LEFT_JOYSTICK_PORT);
        rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
    }

    public static OI getInstance() {
        return instance;
    }
    
    public void teleop() {
        if (!drives.brake()) {
			drives.setSpeed(getLeftY(), getRightY());
		} else {
			drives.setSpeed(0.0);
		}
    }

    public double getLeftY() {
        return leftStick.getY();
    }

    public double getRightY() {
        return rightStick.getY();
    }
}
