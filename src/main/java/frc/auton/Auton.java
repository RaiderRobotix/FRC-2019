package frc.auton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import frc.robot.*;
import static frc.auton.Move.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * Parses JSON file into an {@link AutonMode} array Method "auton" actually
 * executes the chosen mode
 * 
 * @author Varun Chari
 *
 */
final class Auton {
	
	private static final Auton instance = new Auton();
	
	private final int SWITCH_BOX_PORT = 3;
	private final String autonpath = "/home/lvuser/deploy/auton.txt";

	// Hardware
	private final DriveBase drives = DriveBase.getInstance();
	private final Timer timer = new Timer();
	private final Joystick switchBox = new Joystick(SWITCH_BOX_PORT);
	
	
	/**
	 * ArrayList of autonomous modes, with indices 
	 * corresponding to the order read from file
	 */
	private final AutonSet autons = new AutonSet();
	private int step = 0;
	public int autonChosen = 0;

	/**
	 * Parses auton file for instructions
	 */
	private Auton() {
		try (Scanner in = new Scanner(new FileInputStream(autonpath))) {
			int line = 1;
			while (in.hasNextLine()) {
				String name = in.nextLine();
				line++;
				AutonMode mode = new AutonMode(name);
				while (in.hasNextLine()) {
					String[] args = in.nextLine().split(" ");
					Move current;
					switch (args[0].toLowerCase()) {
					case "reset":
						current = new Reset();
						break;
					case "drive":
						current = new Drive(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
						break;
					case "turn":
						current = new Turn(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
						break;
					case "wait":
						current = new Wait(Double.parseDouble(args[1]));
						break;
					case "stop":
						current = new Stop();
						break;
					case "goto":
						current = new Goto(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
						break;
					default : 
						throw new RuntimeException("Wrong Format,line: " + line);
					}
					line++;
					mode.add(current);
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find " + autonpath);
		}
	}
	
	public static Auton getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param autonChosen the AutonMode that would be liked to be executed. Should
	 *                    be the return state of the switchbox The {@link Robot}
	 *                    class must read the switchBox and pass it
	 */
	public void auto() {
		boolean proceed = true;
		if (step < autons.get(autonChosen).size()) {
			Move current = autons.get(autonChosen).get(step);
			switch (current.getOpt()) {
			case DRIVE:
				proceed = drives.driveStraight(((Move.Drive) current).distance, ((Move.Drive) current).speed, false);
				break;
			case STOP:
				drives.setSpeed(0.0);
				break;
			case RESET:
				this.reset();
				drives.resetSensors();
				break;
			case TURN:
				proceed = drives.turn(((Move.Turn) current).angle, ((Move.Turn) current).speed);
				break;
			case WAIT:
				proceed = timer.get() >= ((Move.Wait) current).time;
				break;
			case GOTO:
				step = ((Move.Goto) current).step - 1;
				// Subtract 1 because it will be incremented
				autonChosen = ((Move.Goto) current).mode;
			default:
				break;
			}
			if (proceed)
				this.nextStep();
		}
	}

	public void reset() {
		step = 0;
	}

	public void nextStep() {
		timer.start();
		timer.reset();
		step++;
	}


	/**
	 * @return Autonomous mode chosen by switch-box. (in between 0 & 63)
	 */
	public short getAutonChosen() {
		short ret = 0;
		for (int i = 1; i <= 6; i++)
			if (getSwitch(i))
				ret += 1 << i - 1;
		return ret;
	}

	/**
	 * @param n - (int) Switch to check if is flipped on switch-box.
	 * @return True, if flipped
	 */
	public boolean getSwitch(int n) {
		switch (n) {
		case 1:
			return switchBox.getRawButton(5);
		case 2:
			return switchBox.getRawButton(12);
		case 3:
			return switchBox.getRawButton(7);
		case 4:
			return switchBox.getRawButton(11);
		case 5:
			return switchBox.getRawButton(6);
		case 6:
			return switchBox.getRawButton(8);
		default:
			return false;
		}
	}

}
