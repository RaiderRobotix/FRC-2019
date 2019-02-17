/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Elevator;

import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class DefaultElevatorCommand extends Command {

  private Elevator elevator;
  private OperatorInterface oi;

  public DefaultElevatorCommand() {
    elevator = Elevator.getInstance();
    oi = OperatorInterface.getInstance();

    requires(elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (oi.getRightButton(Constants.SENSOR_RESET_BUTTON)) {
      elevator.resetEncoder();
    }

    if (oi.getOperatorY() > Constants.JOYSTICK_DEADBAND
        && (elevator.getHeight() <= Constants.ELEVATOR_UPPER_LIMIT
            || oi.getOperatorButton(Constants.OPERATOR_OVERRIDE_BUTTON))) {
      elevator.setSpeed(oi.getOperatorY()); // manual up
    } else if (oi.getOperatorY() < -1.0 * Constants.JOYSTICK_DEADBAND
        && (elevator.getHeight() > Constants.ELEVATOR_LOWER_LIMIT
            || oi.getOperatorButton(Constants.OPERATOR_OVERRIDE_BUTTON))) {
      elevator.setSpeed(oi.getOperatorY() * Constants.ELEVATOR_MANUAL_DOWN_RATE); // manual down
    } else {
      elevator.setSpeed(0.0);
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
    elevator.setSpeed(0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
