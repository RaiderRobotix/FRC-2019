/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.BallArm;
import frc.robot.subsystems.Elevator;

public class TiltElevatorForward extends Command {

  private BallArm ballArm;
  private Elevator elevator;

  private Timer timer;
  private boolean isDone;

  public TiltElevatorForward() {
    elevator = Elevator.getInstance();
    ballArm = BallArm.getInstance();

    requires(elevator);
    requires(ballArm);

    timer = new Timer();
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
    ballArm.wristDown(0.8);
    if (timer.get() > 0.25) 
    {
      elevator.tiltForward();
      ballArm.stopWrist();
      isDone = true;
      return;
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
