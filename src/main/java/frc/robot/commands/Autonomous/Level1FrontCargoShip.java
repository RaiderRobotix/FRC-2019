/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.DriveBase.DriveStraight;
import frc.robot.commands.Elevator.TiltElevatorForward;
import frc.robot.commands.HatchGrabber.GrabHatch;

public class Level1FrontCargoShip extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Level1FrontCargoShip() {
    addSequential(new NotifyDriver("Wait..."));
    addSequential(new GrabHatch());
    addSequential(new TiltElevatorForward());
    addSequential(new DriveStraight(125.0, 0.5));
    addSequential(new NotifyDriver("DRIVE NOW"));
  }
}
