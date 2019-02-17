/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Level1ToLeftRocketToHP extends CommandGroup {

  private Trigger trigger;
  /**
   * Add your docs here.
   */
  public Level1ToLeftRocketToHP() {
    addSequential(new Level1ToLeftRocket());
    addSequential(new AddHatchReleaseTrigger(new LeftRocketToHP()));
  }
}
