package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.Elevator.DriveWithJoystick;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

import java.util.ArrayList;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Elevator extends Subsystem {

  private static Elevator m_instance;

  private final CANSparkMax leftMotor;
  private final CANSparkMax rightMotor;

  private final Encoder encoder;

  private Solenoid tiltSolenoid;

  private Elevator() {
    this.leftMotor = new CANSparkMax(Constants.ELEVATOR_LEFT_CAN_ID, MotorType.kBrushless);    
		this.rightMotor = new CANSparkMax(Constants.ELEVATOR_RIGHT_CAN_ID, MotorType.kBrushless);
		
    leftMotor.follow(rightMotor, true);

    this.tiltSolenoid = new Solenoid(Constants.PCM_CAN_ADDRESS, Constants.ELEVATOR_TILT_SOLENOID);
    this.tiltSolenoid.set(false);

    encoder = new Encoder(
      Constants.ELEVATOR_ENCODER_DIO_A, 
      Constants.ELEVATOR_ENCODER_DIO_B, 
      Constants.ELEVATOR_ENCODER_INVERTED
      );
    encoder.setDistancePerPulse(Constants.ELEVATOR_INCHES_PER_COUNT);
  }

  /**
   * @return The Elevator singleton.
   */
  public static Elevator getInstance() {
    if (m_instance == null) {
      m_instance = new Elevator();
    }

    return m_instance;
  }

  public void setSpeed(double speed) {
    this.rightMotor.set(speed);
  }
  
  public boolean encoderValueWithinRange(double targetHeight, double allowedDeviation) {
		return this.getHeight() >= (targetHeight - allowedDeviation)
				&& this.getHeight() <= (targetHeight + allowedDeviation);
	}

  /**
   * @return The elevator height, in inches
   */
  public double getHeight() {
    return this.encoder.getDistance();
  }

  public void tiltForward() {
    this.tiltSolenoid.set(true);   
  }

  public void tiltBack() {
    this.tiltSolenoid.set(false);
  }

  public void resetEncoder() {
    this.encoder.reset();
	}
	
  public ArrayList<String[]> getCanIdFirmwarePairs() {
    ArrayList<String[]> pairs = new ArrayList<String[]>();
    pairs.add(new String[]{"Elevator CAN ID Left " + Constants.ELEVATOR_LEFT_CAN_ID, this.leftMotor.getFirmwareString()});
    pairs.add(new String[]{"Elevator CAN ID Right " + Constants.ELEVATOR_RIGHT_CAN_ID, this.rightMotor.getFirmwareString()});
    return pairs;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveWithJoystick());
  }
}
