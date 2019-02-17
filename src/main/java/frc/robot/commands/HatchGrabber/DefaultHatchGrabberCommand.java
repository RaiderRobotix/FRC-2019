/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.HatchGrabber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.HatchGrabber;

public class DefaultHatchGrabberCommand extends Command {

  private HatchGrabber hatchGrabber;
  private OperatorInterface oi;

  public DefaultHatchGrabberCommand() {
    oi = OperatorInterface.getInstance();
    hatchGrabber = HatchGrabber.getInstance();
    requires(hatchGrabber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (oi.getRightButton(10)) {
      hatchGrabber.grab();
    } else if (oi.getRightButton(11)) {
      hatchGrabber.release();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
