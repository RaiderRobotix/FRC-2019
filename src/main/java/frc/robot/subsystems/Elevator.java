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
  private enum ElevatorRange {
		AT_TARGET, 
		UP_FAR_FROM_TARGET, 
		UP_SCALED_RANGE, 
		UP_NEAR_TARGET, 
		DOWN_FAR_FROM_TARGET, 
		DOWN_NEAR_TARGET
	}

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
    this.leftMotor.set(speed);
  }

  /**
	 * Function to go to desired preset height based on 2011 mast code.
	 * 
	 * @param targetHeight - The target encoder value to go to, height in inches
	 * 
	 * @return true, when complete and stopped; false when mast is still moving
	 */
	public boolean goToHeight(double targetHeight) {
		double currentHeight = this.getHeight();
		boolean movingUp = currentHeight < targetHeight;
		boolean movingDown = currentHeight > targetHeight;
		double positionDelta = 0.0;
		ElevatorRange elevatorRange = ElevatorRange.AT_TARGET;

		// If the elevator needs to change heights, figure out how far away from
		// the target it is
		boolean positionChange = !this.encoderValueWithinRange(targetHeight, Constants.ALLOWED_ELEVATOR_DEVIATION);
		if (positionChange) {
		 	if (movingUp) {
				positionDelta = targetHeight - currentHeight;

		 		if (positionDelta > Constants.ELEVATOR_UP_SCALED_RANGE_START) {
					elevatorRange = ElevatorRange.UP_FAR_FROM_TARGET;
				} else if (positionDelta > Constants.ELEVATOR_UP_SCALED_RANGE_END) {
					elevatorRange = ElevatorRange.UP_SCALED_RANGE;
				} else {
		 			elevatorRange = ElevatorRange.UP_NEAR_TARGET;
		 		}
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
			this.setSpeed(0.0);
			if (this.getHeight() < Constants.ELEVATOR_LOWER_LIMIT + 1.0) {
				this.resetEncoder();
			}
			return true;
		} else if (movingDown && (currentHeight > (targetHeight + Constants.ALLOWED_ELEVATOR_DEVIATION + 1.0))) {
			if (elevatorRange == ElevatorRange.DOWN_NEAR_TARGET) {
				this.setSpeed(Constants.ELEVATOR_DOWN_SPEED_NEAR_TARGET);
			} else if (elevatorRange == ElevatorRange.DOWN_FAR_FROM_TARGET) {
				this.setSpeed(Constants.ELEVATOR_DOWN_SPEED);
			}
		} else if (movingUp && (currentHeight < (targetHeight - Constants.ALLOWED_ELEVATOR_DEVIATION))) {
			if (elevatorRange == ElevatorRange.UP_FAR_FROM_TARGET) {
				this.setSpeed(Constants.ELEVATOR_SCALE_START_SPEED);
			} else if (elevatorRange == ElevatorRange.UP_SCALED_RANGE) {
				double speedDelta = Constants.ELEVATOR_SCALE_START_SPEED - Constants.ELEVATOR_SCALE_END_SPEED;
				double scaledSpeed = Constants.ELEVATOR_SCALE_END_SPEED + (speedDelta * (positionDelta - Constants.ELEVATOR_UP_SCALED_RANGE_END)
						/ (Constants.ELEVATOR_UP_SCALED_RANGE_START - Constants.ELEVATOR_UP_SCALED_RANGE_END));
				this.setSpeed(scaledSpeed);
			} else if (elevatorRange == ElevatorRange.UP_NEAR_TARGET) {
				this.setSpeed(Constants.ELEVATOR_SCALE_END_SPEED);
			}
		}
		return false;
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
