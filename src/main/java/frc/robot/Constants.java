package frc.robot;

final class Constants { // TODO go through all TODOs in file for
						// season

	// FTP Information
	/*
	 * TODO Delete last years auton files from the roboRio run tests to make sure
	 * auton deployment still works
	 */
	public static final String RIO_FTP = "ftp://roboRIO-25-frc.local";
	public static final String RIO_DIR = "/home/lvuser/autons/";
	public static final String LOCAL_DIR = "C:/Users/raiderrobotix/Desktop/Workspace/FRC-2018/autons/";
	 
	// Auto-Driving Constants
	/** In Degrees */
	public static final double TURN_TOLERANCE = 1.0;
	public static final double VEER_TOLERANCE = 1.0;
	public static final double DRIVE_SPEED_CORRECTION = 0.15;
	public static final double DRIVE_TOLERANCE = 1.0;
	public static final double SLOW_SPEED_WEAK = 0.12;
	public static final double SLOW_SPEED_STRONG = 0.18;
	public static final double DRIVE_STRAIGHT_SLOW_RANGE = 12.0;

	// PWMs (Control) TODO check during season
	public static final int RIGHT_BRAKE_PWM = 1;
	public static final int RIGHT_DRIVES_PWM = 0;
	public static final int LEFT_BRAKE_PWM = 7;
	public static final int LEFT_DRIVES_PWM = 8;

	public static final int RIGHT_ELEVATOR_CAN = 2;
	public static final int LEFT_ELEVATOR_CAN = 9;

	public static final int RIGHT_CLIMBER_PWM = 3;
	public static final int LEFT_CLIMBER_PWM = 6;

	// Brake Positions TODO check during season
	public static final double RIGHT_BRAKES_ON = 0.18;
	public static final double RIGHT_BRAKES_OFF = 1.0; // TODO
	public static final double LEFT_BRAKES_ON = 0.63;
	public static final double LEFT_BRAKES_OFF = 0.20;

	// Auton Information
	public static final double TIRE_CIRCUMFERENCE = 29.898; // TODO
	public static final double COUNTS_PER_REVOLUTION = 128;
	public static final double GEAR_RATIO = 0.0714286; // (Driver: Encoder Gear, Driven: Wheel Gear)
	public static final double INCHES_PER_REVOLUTION = GEAR_RATIO * TIRE_CIRCUMFERENCE;
	public static final double INCHES_PER_COUNT = INCHES_PER_REVOLUTION / COUNTS_PER_REVOLUTION;
	public static final double ELEVATOR_INCHES_PER_REVOLUTION = 14.3567;
	public static final double ELEVATOR_INCHES_PER_COUNT = ELEVATOR_INCHES_PER_REVOLUTION / COUNTS_PER_REVOLUTION; // TODO
	public static final double AUTON_TIME = 15.0; // (In Seconds)

	// Joysticks TODO make sure these are correct during season
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;
	public static final int OPERATOR_JOYSTICK_PORT = 2;
	public static final int SWITCH_BOX_PORT = 3;
	public static final double JOYSTICK_DEADBAND = 0.2;

	// Button Constants
	public static final int OPERATOR_OVERRIDE_BUTTON = 7;

	// CAN Addresses TODO get during season
	public static final int PCM_CAN_ADDRESS = 0;
	public static final int PDP_CAN_ADDRESS = 15;

	// Solenoid Numbers
	public static final int RIGHT_TILT_SOLENOID = 7;
	public static final int LEFT_TILT_SOLENOID = 0;
	public static final int GRABBER_SOLENOID = 3;

	// Inversions TODO get during season, set correct inversions for elevator
	// motors (probably already correct) and encoder
	public static final boolean RIGHT_DRIVE_MOTORS_INVERTED = false;
	public static final boolean LEFT_DRIVE_MOTORS_INVERTED = true;
	public static final boolean LEFT_ENCODER_INVERTED = false; // TODO
	public static final boolean RIGHT_ENCODER_INVERTED = false; // TODO

	public static final boolean LEFT_ELEVATOR_INVERTED = true;
	public static final boolean RIGHT_ELEVATOR_INVERTED = true;

	public static final boolean LEFT_CLIMBER_MOTOR_INVERTED = false; // TODO
	public static final boolean RIGHT_CLIMBER_MOTOR_INVERTED = true; // TODO

	public static final boolean ELEVATOR_ENCODER_INVERTED = true;

	// Elevator range and tolerance constants
	public static final double ELEVATOR_UP_SCALED_RANGE_START = 24.0; // TODO
	public static final double ELEVATOR_UP_SCALED_RANGE_END = 0.5 * ELEVATOR_UP_SCALED_RANGE_START;

	public static final double ELEVATOR_DOWN_SPEED_NEAR_TARGET = -0.2; // TODO
	public static final double ELEVATOR_DOWN_SPEED = -0.35; // TODO

	public static final double ELEVATOR_BOTTOM = 1;
	public static final double ELEVATOR_TOP = 100;
	public static final double ELEVATOR_TOLERANCE = 1; // in inches

	public static final double ELEVATOR_MANUAL_DOWN_RATE = 0.40;
	public static final double ELEVATOR_MANUAL_UP_RATE = 0.75;

	public static final double ELEVATOR_HEIGHT_THRESHOLD = 52; // TODO

	public static final double ELEVATOR_DOWN_PRESET = 1.0;
	public static final double ELEVATOR_SWITCH_PRESET = 32.0;
	public static final double ELEVATOR_SCALE_PRESET = 90.0;
	public static final int LEFT_HANGER_CAN_ADDRESS = 0;// TODO
	public static final int RIGHT_HANGER_CAN_ADDRESS = 0;// TODO
	public static final int DEPLOYER_SOLENOID = 0;// TODO
	public static final boolean LEFT_HANGER_MOTOR_INVERTED = false;// TODO
	public static final boolean RIGHT_HANGER_MOTOR_INVERTED = false;// TODO
}
