package edu.neu.ccs.cs6650.Client;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


/**
 * Represents a single thread client.
 *
 * @author wenbowang
 */
public class MySingleThreadClient extends Thread {
  private WebTarget webTarget;
  private MyClient client;
  private int iterations;
  private Measurement measurement;

  // for the testing
//  private String url;

  /**
   * To create a single thread client.
   *
   * @param webTarget the webTarget get from url and path.
   * @param iterations the number of iterations of POST and GET requests to implement.
   * @param measurement the wrapper class object to record all the required metrics.
   */
  public MySingleThreadClient(WebTarget webTarget,
                              int iterations,
                              Measurement measurement) { //  String url
    this.webTarget = webTarget;
    this.client = new MyClient(this.webTarget); // this.webTarget
    this.iterations = iterations;
    this.measurement = measurement;

    // for the testing
//    this.url = url;
  }

  /**
   * Run the client.
   */
  @Override
  public void run() {
    for (int i = 0; i < iterations; i++) {

      // POST request
      long start = System.currentTimeMillis();
      Response response = client.postText("hello world!");
      long end = System.currentTimeMillis();
      this.measurement.addLatency(end - start);
      this.measurement.addRequest();
      if (response.readEntity(Integer.class) == 12) {
        this.measurement.addSuccessRequest();
      }
      response.close();

      // GET request
      start = System.currentTimeMillis();
      String result = client.getStatus();
      end = System.currentTimeMillis();
      this.measurement.addLatency(end - start);
      this.measurement.addRequest();
      String rightAnswer = "What's up! Welcome to CS6650 BSDS";
      if (result.equals(rightAnswer)) {
        this.measurement.addSuccessRequest();
      }
    }

    // for testing
//    for (int i = 0; i < iterations; i++) {
//      System.out.printf("\nIteration No.%d\n", i);
//
//      // POST request
//      System.out.println("*********************************");
//      System.out.println("Now do the POST request: ");
//      long start = System.currentTimeMillis();
//      System.out.printf("start time: %d\n", start);
//
//      Response response = client.postText("hello world!");
//
//      long end = System.currentTimeMillis();
//      System.out.printf("end time: %d\n", end);
//      System.out.printf("Time duration: %d\n", (end - start));
//
//      if (response.readEntity(Integer.class) == 12) {
//        System.out.println("POST request is a Success!");
//      }
//      System.out.println("POST request ends.");
//      System.out.println("*********************************\n");
//      response.close();
//
//      // GET request
//      System.out.println("*********************************");
//      System.out.println("Now do the GET request: ");
//      start = System.currentTimeMillis();
//      System.out.printf("start time: %d\n", start);
//
//      String result = client.getStatus();
//      end = System.currentTimeMillis();
//      System.out.printf("end time: %d\n", end);
//      System.out.printf("Time duration: %d\n", (end - start));
//
//      String rightAnswer = "What's up! Welcome to CS6650 BSDS";
//      if (result.equals(rightAnswer)) {
//        System.out.println("GET request is a Success!");
//      }
//
//      System.out.println("GET request ends.");
//      System.out.println("*********************************");
//    }
  }

//  /**
//   * Test the single thread client.
//   * @param args the args.
//   */
//  public static void main(String[] args) {
//    MySingleThreadClient client = new MySingleThreadClient(
//        "http://54.237.220.154:8080/assignment1", 10);
//
//    client.run();
//  }
}

