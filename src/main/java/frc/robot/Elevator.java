package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

import java.util.ArrayList;

public class Elevator {

  private static Elevator m_instance;

  private final CANSparkMax leftMotor;
  private final CANSparkMax rightMotor;

  private final Encoder encoder;

  private Solenoid tiltSolenoid;

  private Elevator() {
    this.leftMotor = new CANSparkMax(Constants.LEFT_ELEVATOR_CAN_ID, MotorType.kBrushless);
    this.rightMotor = new CANSparkMax(Constants.RIGHT_ELEVATOR_CAN_ID, MotorType.kBrushless);

    leftMotor.setInverted(Constants.LEFT_ELEVATOR_INVERTED);
    rightMotor.setInverted(Constants.RIGHT_ELEVATOR_INVERTED);

    this.tiltSolenoid = new Solenoid(Constants.PCM_CAN_ADDRESS, Constants.ELEVATOR_TILT_SOLENOID);
    this.tiltSolenoid.set(false);

    encoder = new Encoder(
      Constants.ELEVATOR_ENCODER_PWM_A, 
      Constants.ELEVATOR_ENCODER_PWM_B, 
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
    this.rightMotor.set(speed);
  }

  /**
   * @return The elevator height, in encoder ticks, scaled appropriately to
   *         account for the trolley section on the last stage.
   */
  public double getHeight() {
    return this.encoder.getDistance();
    // TODO: Update constants for new trolley position and use the below logic.
    // return this.encoder.getPosition() >
    // Constants.ELEVATOR_DOUBLE_HEIGHT_THRESHOLD
    // ? 2 * this.encoder.getPosition() - Constants.ELEVATOR_DOUBLE_HEIGHT_THRESHOLD
    // : this.encoder.getPosition();
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
    pairs.add(new String[]{"Elevator CAN ID Left " + Constants.LEFT_ELEVATOR_CAN_ID, this.leftMotor.getFirmwareString()});
    pairs.add(new String[]{"Elevator CAN ID Right " + Constants.RIGHT_ELEVATOR_CAN_ID, this.rightMotor.getFirmwareString()});
   return pairs;
  }
}