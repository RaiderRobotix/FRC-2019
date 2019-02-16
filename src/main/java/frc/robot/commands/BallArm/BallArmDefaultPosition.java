/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BallArm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;

public class BallArmDefaultPosition extends CommandGroup {
  /**
   * Add your docs here.
   */
  public BallArmDefaultPosition() {
    addSequential(new ContractBallArm());
    addSequential(new GoToWristPosition(Constants.WRIST_MID_SCORE_POSITION));
    addSequential(new TiltBallArmUp());
  }
}
