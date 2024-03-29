/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.Autonomous.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private final OperatorInterface oi;
  private final Compressor compressor;

  private final BallArm ballArm;
  private final DriveBase drives;
  private final Elevator elevator;
  private final Vision vision;

  private SendableChooser<Command> autonomousChooser;
  private Command autonomousCommand;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  Robot() {
    this.oi = OperatorInterface.getInstance();

    this.compressor = new Compressor(Constants.PCM_CAN_ADDRESS);
    this.compressor.setClosedLoopControl(true);

    this.ballArm = BallArm.getInstance();
    this.drives = DriveBase.getInstance();
    this.elevator = Elevator.getInstance();
    this.vision = Vision.getInstance();
  }

  @Override
  public void robotInit() {
    compressor.start();

    autonomousChooser = new SendableChooser<Command>();
    autonomousChooser.setDefaultOption("Do Nothing", new DoNothing());
    autonomousChooser.addOption("Cross HAB Level 1", new CrossHabLineFromLevel1());
    autonomousChooser.addOption("Level 1 Front Cargo Ship", new Level1FrontCargoShip());
    autonomousChooser.addOption("Level 1 to Left Rocket", new Level1ToLeftRocket());
    autonomousChooser.addOption("Level 1 to Left Rocket to HP", new Level1ToLeftRocketToHP());
    autonomousChooser.addOption("Level 2 to Left Rocket", new Level2ToLeftRocket());
    autonomousChooser.addOption("Level 2 to Center", new Level2ToCenter());
    
    SmartDashboard.putData("Autonomous mode chooser", autonomousChooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Left Encoder", this.drives.getLeftDistance());
    SmartDashboard.putNumber("Right Encoder", this.drives.getRightDistance());
    SmartDashboard.putNumber("Gyro Angle", this.drives.getGyroAngle());
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();

    ArrayList<String[]> subsystemCanIdFirmwarePairs = new ArrayList<>();
    subsystemCanIdFirmwarePairs.addAll(this.drives.getCanIdFirmwarePairs());
    subsystemCanIdFirmwarePairs.addAll(this.ballArm.getCanIdFirmwarePairs());
    subsystemCanIdFirmwarePairs.addAll(this.elevator.getCanIdFirmwarePairs());
    
      for (String[] pair : subsystemCanIdFirmwarePairs) {
        SmartDashboard.putString(pair[0] + " ", pair[1]);
    }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    super.autonomousInit();

    autonomousCommand = autonomousChooser.getSelected();
    autonomousCommand.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    SmartDashboard.putNumber("Elevator Encoder", this.elevator.getHeight());
    SmartDashboard.putNumber("Wrist Encoder", this.ballArm.getWristDistance());
    SmartDashboard.putNumber("Ultrasonic", this.drives.getUltrasonicDistance());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

}
