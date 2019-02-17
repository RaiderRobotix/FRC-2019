/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.commands.DriveBase.DriveStraight;
import frc.robot.commands.DriveBase.Turn;
import frc.robot.commands.Elevator.TiltElevatorForward;
import frc.robot.commands.HatchGrabber.GrabHatch;

public class Level2ToLeftRocket extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Level2ToLeftRocket() {
    addSequential(new NotifyDriver("Wait..."));
    addSequential(new GrabHatch());
    addSequential(new TimedCommand("Wait", 0.25));
    addSequential(new TiltElevatorForward());
    addSequential(new DriveStraight(100.0, 0.75));
    addSequential(new Turn(-90.0, 0.4));
    addSequential(new DriveStraight(80.0, 0.5));
    addSequential(new Turn(60.0, 0.4));
    addSequential(new DriveStraight(55.0, 0.5));
    addSequential(new NotifyDriver("DRIVE NOW"));
  }
}
