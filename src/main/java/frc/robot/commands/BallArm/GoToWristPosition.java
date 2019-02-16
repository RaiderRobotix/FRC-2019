/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BallArm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.BallArm;

public class GoToWristPosition extends Command {

  private final BallArm ballArm;

  private boolean isDone;
  private final double target;

  public GoToWristPosition(double targetPosition) {
    ballArm = BallArm.getInstance();
    this.target = targetPosition;
    requires(ballArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (ballArm.encoderValueWithinRange(target)) {
      isDone = true;
      ballArm.stopWrist();
    } else {
      boolean goingForward = ballArm.getWristDistance() < target;
      if (goingForward) {
        ballArm.wristDown(0.7);
      } else {
        ballArm.wristUp(0.8);
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isDone;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ballArm.stopWrist();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
