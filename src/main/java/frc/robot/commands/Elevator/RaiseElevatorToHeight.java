/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Elevator;

import frc.robot.subsystems.Elevator;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseElevatorToHeight extends Command {

	private Elevator elevator;
	private double targetHeight;
	private boolean isDone;

	private enum ElevatorRange {
		AT_TARGET, DOWN_FAR_FROM_TARGET, DOWN_NEAR_TARGET
	}

	public RaiseElevatorToHeight(double height) {
		elevator = Elevator.getInstance();
		requires(elevator);

		targetHeight = height;
	}

	// Called just before elevator Command runs the first time
	@Override
	protected void initialize() {
		isDone = false;
	}

	// Called repeatedly when elevator Command is scheduled to run
	@Override
	protected void execute() {
		double currentHeight = elevator.getHeight();
		boolean movingUp = currentHeight < targetHeight;
		boolean movingDown = currentHeight > targetHeight;
		double positionDelta = 0.0;
		ElevatorRange elevatorRange = ElevatorRange.AT_TARGET;

		// If the elevator needs to change heights, figure out how far away from
		// the target it is
		boolean positionChange = !elevator.encoderValueWithinRange(targetHeight, Constants.ALLOWED_ELEVATOR_DEVIATION);
		if (positionChange) {
			if (movingUp) {
				positionDelta = targetHeight - currentHeight;
			} else if (movingDown) {
				positionDelta = currentHeight - targetHeight;

				if (positionDelta < 10.0) {
					elevatorRange = ElevatorRange.DOWN_NEAR_TARGET;
				} else {
					elevatorRange = ElevatorRange.DOWN_FAR_FROM_TARGET;
				}
			}
		} else {
			elevatorRange = ElevatorRange.AT_TARGET;
		}

		// Set the elevator speed based on its distance from target
		if (elevatorRange == ElevatorRange.AT_TARGET
				|| (movingDown && currentHeight <= Constants.ELEVATOR_LOWER_LIMIT + 1.0)
				|| (movingUp && currentHeight >= Constants.ELEVATOR_UPPER_LIMIT)) {
			elevator.setSpeed(0.0);
			if (elevator.getHeight() < Constants.ELEVATOR_LOWER_LIMIT + 1.0) {
				elevator.resetEncoder();
			}
			isDone = true;
			return;
		} else if (movingDown && (currentHeight > (targetHeight + Constants.ALLOWED_ELEVATOR_DEVIATION))) {
			if (elevatorRange == ElevatorRange.DOWN_NEAR_TARGET) {
				elevator.setSpeed(Constants.ELEVATOR_DOWN_SPEED_NEAR_TARGET);
			} else if (elevatorRange == ElevatorRange.DOWN_FAR_FROM_TARGET) {
				elevator.setSpeed(Constants.ELEVATOR_DOWN_SPEED);
			}
		} else if (movingUp && (currentHeight < (targetHeight - Constants.ALLOWED_ELEVATOR_DEVIATION))) {
			elevator.setSpeed(0.9);
		}
	}

	// Make elevator isDone = true when elevator Command no longer needs to run
	// execute()
	@Override
	protected boolean isFinished() {
		return isDone;
	}

	// Called once after isFinished isDone =s true
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
