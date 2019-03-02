/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BallArm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.BallArm;

public class GoToWristPosition extends Command {

  private final BallArm ballArm;
  private final Timer timer;

  private boolean isDone;
  private final double target;

  private final double TIMEOUT = 1.0;

  public GoToWristPosition(double targetPosition) {
    ballArm = BallArm.getInstance();
    requires(ballArm);

    timer = new Timer();
    this.target = targetPosition;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    timer.start();
    timer.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (timer.get() > TIMEOUT || ballArm.encoderValueWithinRange(target)) {
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
    timer.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
