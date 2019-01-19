package frc.robot;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionThread;

public class Vision {

	private static volatile Vision instance = null;

	private final NetworkTableInstance table = NetworkTableInstance.getDefault();
	private final NetworkTable contours = table.getTable("GRIP/contours");

	private final CameraServer camserv = CameraServer.getInstance();
	private final UsbCamera cam = camserv.startAutomaticCapture();
	
	/**
	 * Thread that get contours from camera output, 
	 * and will perform some operation using them
	 */
	VisionThread visio = new VisionThread(cam, new GripPipeline(), pipeline -> {
		if (!pipeline.filterContoursOutput().isEmpty()) {
			/*
			Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
			synchonized() {}
			*/
		}
	});
	
	/**
	 * Thread that get contours from camera output, 
	 * and will perform some operation using them
	 */
	private Thread imgupdate = new Thread(() -> {
		CvSink cvSink = camserv.getVideo();
		CvSource outputStream = camserv.putVideo("VideoStream", 320, 240);
		Mat source = new Mat();
		Mat output = new Mat();
		while (!Thread.interrupted()) {
			cvSink.grabFrame(source);
			Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
			outputStream.putFrame(output);
		}
	});

	private Vision() {
		contours.addEntryListener(
				(table,key,entry,value,huh) -> {

		}, 0);
		cam.setResolution(320, 240);
		cam.setFPS(20);
		imgupdate.start();
		visio.start();
	}

	public static Vision getInstance() {
		if (instance == null) // Thread safety
			synchronized (Vision.class) { // Multiple threads can reach here
				if (instance == null)
					instance = new Vision();
			}
		return instance;
	}

	public void update() {
		double[] areas = contours.getEntry("area").getDoubleArray(new double[0]);
		for (int i = 0; i < areas.length; i++)
			System.out.println("areas " + i + " : " + areas[i]);
	}
}
