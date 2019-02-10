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
  
  private static final Elevator elevator = Elevator.getInstance();
  
  private static final double UP_SCALED_RANGE_START = 20.0;
  private static final double UP_SCALED_RANGE_END
      = 0.5 * UP_SCALED_RANGE_START;
  private static final double ALLOWED_DEVIATION = 1.0; // in inches
	private static final double LOWER_LIMIT = 0.0;

  private double targetHeight;
  private boolean isDone;

  public RaiseElevatorToHeight(double height) {
    
    requires(elevator);

    targetHeight = height;
  }
  
  private enum Range {
		UP_FAR(0.9), 
		UP_SCALED(0.7), // Just a default speed, is overwritten
		UP_NEAR(0.5), 
		AT_TARGET(0),
		DOWN_NEAR(-0.3),
		DOWN_FAR(-0.5);
		
		public final double speed;
		
		Range(double speed) {
			this.speed = speed;
		}
		
		private static double getSpeed(double delta) {
			Range range = Range.AT_TARGET;

			if ( Math.abs(delta) > ALLOWED_DEVIATION ) {
				if (delta > 0) {
					if (delta > UP_SCALED_RANGE_START) {
						range = Range.UP_FAR;
					}
					else if (delta > UP_SCALED_RANGE_END) {
						range = Range.UP_SCALED;
					}
					else {
						range = Range.UP_NEAR;
					}
				} else
					if (delta > -10.0) {
						range = Range.DOWN_NEAR;
					}
					else {
						range = Range.DOWN_FAR;
					}
			} else {
				range = Range.AT_TARGET;
				}
			double retSpeed  = range.speed;
			if (range == Range.UP_SCALED) {
				double speedDelta = Range.UP_FAR.speed - Range.UP_NEAR.speed;
				retSpeed = Range.UP_NEAR.speed + (speedDelta * (delta - UP_SCALED_RANGE_END)
						/ (UP_SCALED_RANGE_START - UP_SCALED_RANGE_END));
			}
			return retSpeed;
		}
	}

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double currentHeight = elevator.getHeight();
	double delta = targetHeight - currentHeight;
	double speed = Range.getSpeed(delta);
	
	if (currentHeight < LOWER_LIMIT + ALLOWED_DEVIATION) {
		elevator.resetEncoder();
	}
	elevator.setSpeed(speed);
	isDone = speed == 0;
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
