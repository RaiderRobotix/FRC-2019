/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BallArm;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.BallArm;

public class TiltBallArmDown extends TimedCommand {

  private BallArm ballArm;

  public TiltBallArmDown() {
    super(1.0);
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
    if (!ballArm.isExtended()) {
      ballArm.tiltDown();
    }
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
