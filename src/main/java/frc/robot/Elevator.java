package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Elevator {

  private static Elevator m_instance;

  private final CANSparkMax leftMotor;
  private final CANSparkMax rightMotor;

  private final CANEncoder encoder;

  private Elevator() {
    this.leftMotor = new CANSparkMax(Constants.LEFT_ELEVATOR_CAN, MotorType.kBrushless);
    this.rightMotor = new CANSparkMax(Constants.RIGHT_ELEVATOR_CAN, MotorType.kBrushless);

    this.encoder = this.leftMotor.getEncoder();
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
    this.leftMotor.set(speed * (Constants.LEFT_ELEVATOR_INVERTED ? -1.0 : 1.0));
    this.rightMotor.set(speed * (Constants.RIGHT_ELEVATOR_INVERTED ? -1.0 : 1.0));
  }

  /**
   * @return The elevator height, in encoder ticks, scaled appropriately to account
    for the trolley section on the last stage.
   */
  public double getHeight() {
    return this.encoder.getPosition();
    
    // TODO: Update constants for new trolley position and use the below logic.
    // return this.encoder.getPosition() > Constants.ELEVATOR_DOUBLE_HEIGHT_THRESHOLD
    //     ? 2 * this.encoder.getPosition() - Constants.ELEVATOR_DOUBLE_HEIGHT_THRESHOLD
    //     : this.encoder.getPosition();
  }
}