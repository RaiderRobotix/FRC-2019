package frc.robot;

// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

public class Elevator {

  private static Elevator m_instance;

  private final Spark leftMotor;
  private final Spark rightMotor;

  // private final Encoder encoder = new Encoder(
  //     Constants.ELEVATOR_ENCODER_PWM_A, 
  //     Constants.ELEVATOR_ENCODER_PWM_B, 
  //     Constants.ELEVATOR_ENCODER_INVERTED);

  private Elevator() {
    this.leftMotor = new Spark(Constants.LEFT_ELEVATOR_PWM);
    this.rightMotor = new Spark(Constants.RIGHT_ELEVATOR_PWM);

    leftMotor.setInverted(Constants.LEFT_ELEVATOR_INVERTED);
    rightMotor.setInverted(Constants.RIGHT_ELEVATOR_INVERTED);

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
    // return this.encoder.getPosition();
    return 0.0;
    // TODO: Update constants for new trolley position and use the below logic.
    // return this.encoder.getPosition() >
    // Constants.ELEVATOR_DOUBLE_HEIGHT_THRESHOLD
    // ? 2 * this.encoder.getPosition() - Constants.ELEVATOR_DOUBLE_HEIGHT_THRESHOLD
    // : this.encoder.getPosition();
  }
}