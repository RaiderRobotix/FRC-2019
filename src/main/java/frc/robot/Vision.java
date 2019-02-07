package frc.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
// import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionThread;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class Vision {

  private static volatile Vision instance = null;

  // private final NetworkTableInstance table = NetworkTableInstance.getDefault();
  // private final NetworkTable contours = table.getTable("GRIP/contours");

  private final CameraServer camserv = CameraServer.getInstance();
  private final UsbCamera cam = camserv.startAutomaticCapture();

  private Integer pixelsOff = 0;

  private final int img_height = 360;
  private final int img_width = 540;
  private final int fps = 20;

  private final double inchesPerPixel = 1; //TODO
  private final double distanceFromTarget = 1; //TODO

  /**
   * Thread that get contours from camera output and will perform some operation using them.
   */
  private final VisionThread visio = new VisionThread(cam, new GripPipeline(), pipeline -> {
    synchronized (pixelsOff) {
      if (!pipeline.filterContoursOutput().isEmpty()) {
        Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
        pixelsOff = r.x + (r.width / 2) - img_width/2;
      } else 
        pixelsOff = 0; // If no countours found, don't set something erroneous
      }
  });

  /**
   * Thread that serves videostream
   */
  private final Thread imgupdate = new Thread() {
    CvSink cvSink = camserv.getVideo();
    CvSource outputStream = new CvSource("VideoStream", PixelFormat.kMJPEG, img_width, img_height, fps);
    Mat source = new Mat();
    public void run() {
      while (!Thread.interrupted()) {
        cvSink.grabFrame(source);
        outputStream.putFrame(source);
      }
      outputStream.close();
    }
  };

  private Vision() {
    cam.setResolution(img_width, img_height);
    cam.setFPS(fps);
    imgupdate.start();
    visio.start();
  }

  /**
   * @return The Vision singleton.
   */
  public static Vision getInstance() {
    if (instance == null) { // Thread safety
      synchronized (Vision.class) { // Multiple threads can reach here
        if (instance == null) {
          instance = new Vision();
        }
      }
    }
    return instance;
  }

  /**
   * Update.
   */
  /**
  public void update() {
    double[] areas = contours.getEntry("area").getDoubleArray(new double[0]);
    for (int i = 0; i < areas.length; i++) {
      System.out.println("areas " + i + " : " + areas[i]);
    }
  }
  */

  /**
   * 
   * @return The angle, in degrees, from a retroflective target
   */
  public double turnCorrection() {
    synchronized (pixelsOff) {
      return -Math.atan(pixelsOff*inchesPerPixel/distanceFromTarget) * 180 / Math.PI;
    }
  }
}
