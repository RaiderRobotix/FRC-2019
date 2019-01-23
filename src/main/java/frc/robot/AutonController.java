package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public final class AutonController {

  private AutonController m_instance;

  private int m_step;
	private final DriveBase drives;
	private final Elevator elevator;
	//private final GripPipeline grippipeline;
	private final Timer timer;
	private final Joystick switchBox;

	private AutonController() {
		drives = DriveBase.getInstance();
		elevator = Elevator.getInstance();
		//grippipeline = GripPipeline.getInstance(); //TO DO
		switchBox = new Joystick(Constants.SWITCH_BOX_PORT);
    timer = new Timer(); 
    m_step = 0;
  }

	/*
	public static AutonController getInstance() {
		if (m_instance == null) {
			m_instance = new AutonController();
		}
    return m_instance;
  }
  
  */

	public void resetStep() {
		m_step = 0;
	}

	public void reset() {
		timer.start();
		timer.reset();
	}

	public void nextStep() {
		this.reset();
		m_step++;
	}

	public void doNothing() {
		drives.setSpeed(0.0);
		elevator.setSpeed(0.0);
	}

  /*
	public void goStraight() {
		if (m_step == 0) {
			drives.resetSensors();
			this.nextStep();
		} else if (m_step == 1) {
			grippipeline.grab();
			if (timer.get() > 0.25) {
				this.nextStep();
			}
		} else if (m_step == 2) {
			elevator.straighten();
			this.nextStep();
		} else if (m_step == 3) {
			if (drives.driveStraightFast(120.0, 0.35)) {
				this.nextStep();
			}
		} else {
			this.doNothing();
		}
	}
	*/
}
