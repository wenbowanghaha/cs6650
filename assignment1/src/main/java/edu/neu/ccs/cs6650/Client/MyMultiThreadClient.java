package edu.neu.ccs.cs6650.Client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Represents a client of multiple threads to run iterations of POST and GET requests.
 *
 * @author wenbowang
 */
public class MyMultiThreadClient extends Thread {
  // resource path
  public static final String PATH = "assignment1/webapi/myresource";

  private String url;
  private int thread;
  private int iterations;
  private List<Measurement> measurements;

  /**
   * Creates a new multi-thread client.
   * @param url the url to get to.
   * @param thread the number of threads.
   * @param iterations the number of iterations.
   * @param measurements the list of the measurement wrapper class object to record metrics.
   */
  public MyMultiThreadClient(String url,
                             int thread,
                             int iterations,
                             List<Measurement> measurements) {
    this.url = url;
    this.thread = thread;
    this.iterations = iterations;
    this.measurements = measurements;
  }

  /**
   * Run a certain phase basing on the number of threads to run.
   *
   * @param phase the name of the phase.
   * @param threadNumber the number of threads to run in the phase.
   * @param webTarget the webTarget get from the url and path.
   */
  private void runPhase(String phase, int threadNumber, WebTarget webTarget) {
    // some initializations
    ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
    long start = System.currentTimeMillis();

    // create threads
    for (int i = 0; i < threadNumber; i++) {
      // make a single thread client
      Measurement measurement = new Measurement();
      this.measurements.add(measurement);
      MySingleThreadClient single =
          new MySingleThreadClient(webTarget, this.iterations, measurement);

      // add to executor
      executorService.submit(single);
    }

    System.out.println(phase + " phase: All threads running...");
    executorService.shutdown();
    while (!executorService.isTerminated());

    long end = System.currentTimeMillis();
    System.out.println(
        phase + " phase complete: Time " + (float)(end - start) / 1000. + " seconds");
  }

  /**
   * Run the multi-thread client.
   */
  @Override
  public void run() {
    // start to run 4 phases
    long start = System.currentTimeMillis();
    System.out.println(
        "Client starting...Time: " + new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss").format(start));
    System.out.println("=====================================");

    // create a client to start with
    Client client = ClientBuilder.newClient();
    WebTarget webTarget = client.target(this.url).path(PATH);

    // warmup phase
    runPhase("Warmup", this.thread / 10, webTarget);
    // loading phase
    runPhase("Loading", this.thread / 2, webTarget);
    // peak phase
    runPhase("Peak", this.thread, webTarget);
    // cooldown phase
    runPhase("Cooldown", this.thread / 4, webTarget);

    System.out.println("=====================================");

    // get the end time and wall time
    long end = System.currentTimeMillis();
    System.out.println(
        "Client ends...Time: " + new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss").format(end));
    System.out.println("=====================================");

    // now calculate the required measurements
    calculateMeasurements(this.measurements);

    // print out wall time
    System.out.println("Test Wall time: " + (float)(end - start) / 1000. + " seconds");
  }

  /**
   * To calculate the required measurements.
   * @param measurements the collected raw measurements as input.
   */
  private void calculateMeasurements(List<Measurement> measurements) {
    int totalRequests = 0;
    int totalSuccessRequests = 0;
    long latencySum = 0L;
    List<Long> allLatencies = new ArrayList<>();

    for (Measurement m : this.measurements) {
      totalRequests += m.getTotalRequests();
      totalSuccessRequests += m.getTotalSuccessRequest();
      allLatencies.addAll(m.getLatencies());
      latencySum += m.getTotalLacency();
    }

    // print out some metrics
    System.out.println("Total number of request sent: " + totalRequests);
    System.out.println("Total number of successful reponses: " + totalSuccessRequests);


  }

  // Test the multi-thread client
  public static void main(String[] args) {
    int thread = 100;
    int iterations = 100;
    String address = "54.237.220.154";
    String port = "8080";

//    if (args.length != 4) {
//      System.out.println("Invalid command line arguments input, please input:");
//      System.out.println("[thread number] [iteration number] [ip address] [port]");
//      System.exit(0);
//    } else {
//      thread = Integer.parseInt(args[0]);
//      iterations = Integer.parseInt(args[1]);
//      address = args[2];
//      port = args[3];
//    }

    System.out.println("Threads number: " + thread);
    System.out.println("Iterations number: " + iterations);
    System.out.println("IP Address: " + address);
    System.out.println("Port: " + port + "\n\n");

    String url = "http://" + address + ":" + port;
    List<Measurement> measurements = new ArrayList<>();
    MyMultiThreadClient multi = new MyMultiThreadClient(url, thread, iterations, measurements);

    // run the multi-thread client
    multi.run();
  }
}