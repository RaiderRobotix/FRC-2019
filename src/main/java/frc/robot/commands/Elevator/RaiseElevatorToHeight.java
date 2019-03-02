/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Elevator;

import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RaiseElevatorToHeight extends Command {
  
  private Elevator elevator;
  private Timer timer;

  private double targetHeight;
  private boolean isDone;

  private final double TIMEOUT = 3.0;

  public RaiseElevatorToHeight(double height) {
    elevator = Elevator.getInstance();
    requires(elevator);

    timer = new Timer();
    this.targetHeight = height;
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
    isDone = elevator.goToHeight(targetHeight) || timer.get() > TIMEOUT;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isDone;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    elevator.setSpeed(0.0);
    timer.stop();

    if (targetHeight == Constants.ELEVATOR_LOW_PRESET) {
      elevator.resetEncoder();
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
