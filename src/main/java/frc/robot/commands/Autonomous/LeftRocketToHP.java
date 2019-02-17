/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.DriveBase.DriveStraight;
import frc.robot.commands.DriveBase.ResetDriveSensors;
import frc.robot.commands.DriveBase.Turn;

public class LeftRocketToHP extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LeftRocketToHP() {
    addSequential(new ResetDriveSensors());
    addSequential(new DriveStraight(-20, 0.5));
    addSequential(new Turn(-155, 0.4));
    addSequential(new DriveStraight(120, 0.5));
  }
}
