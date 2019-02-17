/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Trigger;
import frc.robot.Constants;
import frc.robot.OperatorInterface;

/**
 * Add your docs here.
 */
public class AutonHatchReleaseButtonTrigger extends Trigger {

  private OperatorInterface oi;

  public AutonHatchReleaseButtonTrigger() {
    super();
    oi = OperatorInterface.getInstance();
  }

  @Override
  public boolean get() {
    return DriverStation.getInstance().isAutonomous() && oi.getRightButton(Constants.HATCH_RELEASE_BUTTON);
  }
}
