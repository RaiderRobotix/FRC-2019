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

public class ShootBallSequence extends Command {

  private BallArm ballArm;
  private Timer timer;
  private boolean isDone;

  public ShootBallSequence() {
    timer = new Timer();

    ballArm = BallArm.getInstance();
    requires(ballArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.start();
    timer.reset();
    isDone = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    ballArm.retractBallPopper();
    if (timer.get() < 0.3) {
      ballArm.intake(0.2);
    } else if (timer.get() > 0.8) { // 0.7
      ballArm.eject(0.8);
    }
    if (timer.get() > 2.3) {  // 1.5
      ballArm.popBallOut();
      isDone = true;
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
    ballArm.stopRollers();
    ballArm.popBallOut();
    timer.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
