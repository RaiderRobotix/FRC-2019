/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BallArm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.BallArm;

public class DefaultBallArmCommand extends Command {

  private BallArm ballArm;
  private OperatorInterface oi;

  public DefaultBallArmCommand() {
    oi = OperatorInterface.getInstance();
    ballArm = BallArm.getInstance();

    requires(ballArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (oi.getRightButton(Constants.SENSOR_RESET_BUTTON)) {
      ballArm.resetEncoder();
    }

    if (oi.getLeftButton(7)) {
      ballArm.retractBallPopper();
    }

    if (oi.getOperatorButton(6) && 
        (ballArm.getWristDistance() < Constants.WRIST_UPPER_LIMIT || oi.getOperatorButton(Constants.OPERATOR_OVERRIDE_BUTTON))) {
      ballArm.wristDown(0.8);
    } else if (oi.getOperatorButton(4) && 
        (ballArm.getWristDistance() > Constants.WRIST_LOWER_LIMIT + Constants.WRIST_TOLERANCE || oi.getOperatorButton(Constants.OPERATOR_OVERRIDE_BUTTON))) {
      ballArm.wristUp(1.0);
    } else {
      ballArm.stopWrist();
    }

    if (oi.getOperatorTrigger()) {
      ballArm.intake(0.20);
    } else if (oi.getOperatorButton(2) || oi.getOperatorButton(7)) {
      ballArm.eject(0.2);
    } else {
      ballArm.stopRollers();
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
    ballArm.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
