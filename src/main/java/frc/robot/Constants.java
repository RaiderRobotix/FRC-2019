package frc.robot;

public final class Constants {

  /**
   * CAN IDs & PWM ports.
   */
  public static final int LEFT_FRONT_DRIVE_CAN_ID = 2;
  public static final int LEFT_BACK_DRIVE_CAN_ID = 1;
  public static final int RIGHT_FRONT_DRIVE_CAN_ID = 3;
  public static final int RIGHT_BACK_DRIVE_CAN_ID = 4;

  public static final int PCM_CAN_ADDRESS = 0;

  public static final int LEFT_ELEVATOR_CAN_ID = 5;
  public static final int RIGHT_ELEVATOR_CAN_ID = 6;

  public static final int BOTTOM_ROLLER_CAN_ID = 7;
  public static final int TOP_ROLLER_CAN_ID = 8;

  public static final int WRIST_PWM = 0;

  // Pneumatic Solenoid Ports
  public static final int ELEVATOR_TILT_SOLENOID = 0;
  public static final int HATCH_GRABBER_SOLENOID = 7;

  /**
   * OPERATOR INTERFACE.
   */
  public static final int LEFT_JOYSTICK_PORT = 0;
  public static final int RIGHT_JOYSTICK_PORT = 1;
  public static final int OPERATOR_JOYSTICK_PORT = 2;
  public static final int SWITCH_BOX_PORT = 3;

  public static final double JOYSTICK_DEADBAND = 0.15;
  public static final int OPERATOR_OVERRIDE_BUTTON = 8;

  /**
   * DRIVEBASE.
   */
  public static final boolean RIGHT_DRIVE_MOTORS_INVERTED = false;
  public static final boolean LEFT_DRIVE_MOTORS_INVERTED = true;
  
  // Auto-Driving Constants (in degrees)
  public static final double TURN_TOLERANCE = 1.0;
  public static final double DRIVE_STRAIGHT_ANGLE_TOLERANCE = 1.0;
  public static final double DRIVE_SPEED_CORRECTION = 0.15;
  public static final double DRIVE_STRAIGHT_DISTANCE_TOLERANCE = 1.0;
  public static final double SLOW_SPEED_WEAK = 0.12;
  public static final double SLOW_SPEED_STRONG = 0.18;
  public static final double DRIVE_STRAIGHT_SLOW_RANGE = 12.0;

  // Encoder distance calculations
  private static final double TIRE_CIRCUMFERENCE = 28.375;
  private static final double COUNTS_PER_REVOLUTION = 128;

  // (Gear ratio: 16 down to (42 : 18 transmission) to 144 wheel (down))
  private static final double GEAR_RATIO = 0.047619; //0.0714286;
  public static final double INCHES_PER_REVOLUTION = GEAR_RATIO * TIRE_CIRCUMFERENCE;
  //public static final double INCHES_PER_COUNT = INCHES_PER_REVOLUTION / COUNTS_PER_REVOLUTION;
  public static final double ELEVATOR_INCHES_PER_REVOLUTION = 29.125;
   public static final double ELEVATOR_INCHES_PER_COUNT
       = ELEVATOR_INCHES_PER_REVOLUTION / COUNTS_PER_REVOLUTION;

  /**
   * ELEVATOR.
   */
  public static final int ELEVATOR_ENCODER_DIO_A = 0;
  public static final int ELEVATOR_ENCODER_DIO_B = 1;
  public static boolean ELEVATOR_ENCODER_INVERTED = false;
  
  public static boolean LEFT_ELEVATOR_INVERTED = true;
  public static boolean RIGHT_ELEVATOR_INVERTED = !LEFT_ELEVATOR_INVERTED;

  // Elevator range and tolerance constants
  public static final double ELEVATOR_UP_SCALED_RANGE_START = 20.0;
  public static final double ELEVATOR_UP_SCALED_RANGE_END
      = 0.5 * ELEVATOR_UP_SCALED_RANGE_START;
  public static final double ELEVATOR_SCALE_START_SPEED = 0.9;
  public static final double ELEVATOR_SCALE_END_SPEED = 0.5;

  public static final double ELEVATOR_MANUAL_DOWN_RATE = 0.5;

  public static final double ELEVATOR_DOWN_SPEED_NEAR_TARGET = -0.3;
  public static final double ELEVATOR_DOWN_SPEED = -0.5;
  
  public static final double ELEVATOR_LOWER_LIMIT = 0.0;
  public static final double ELEVATOR_UPPER_LIMIT = 100.0; // TODO
  public static final double ALLOWED_ELEVATOR_DEVIATION = 1.0; // in inches
  
  public static final double ELEVATOR_DOUBLE_HEIGHT_THRESHOLD = 52.0; // TODO: update for trolley

  // ELEVATOR PRESET HEIGHT POSITIONS
  public static final double ELEVATOR_LOW_PRESET = 1.0; // TODO
  public static final double ELEVATOR_MIDDLE_PRESET = 64.0; // TODO
  public static final double ELEVATOR_HIGH_PRESET = 95.0; // TODOs
}
