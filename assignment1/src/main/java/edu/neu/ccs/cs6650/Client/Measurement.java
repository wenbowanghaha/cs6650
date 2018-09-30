package edu.neu.ccs.cs6650.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a wrapper class that records all the metrics generated after running the client.
 *
 * @author wenbowang
 */
public class Measurement {
  private List<Long> latencies;
  private int totalRequests;
  private int totalSuccessRequest;

  public Measurement(List<Long> latencies, int totalRequests, int totalSuccessRequest) {
    this.latencies = new ArrayList<>();
    this.totalRequests = 0;
    this.totalSuccessRequest = 0;
  }

  /**
   * Getter for the property 'latencies'.
   *
   * @return the value of property 'latencies'.
   */
  public List<Long> getLatencies() {
    return this.latencies;
  }

  /**
   * Getter for the property 'totalRequests'.
   *
   * @return the value of property 'totalRequests'.
   */
  public int getTotalRequests() {
    return this.totalRequests;
  }

  /**
   * Getter for the property 'totalSuccessRequest'.
   *
   * @return the value of property 'totalSuccessRequest'.
   */
  public int getTotalSuccessRequest() {
    return this.totalSuccessRequest;
  }

  /**
   * Add a latency to the list of latencies.
   * @param latency the latency to be added.
   */
  public void addLatency(long latency) {
    this.latencies.add(latency);
  }

  /**
   * Increase total requests by 1.
   */
  public void addRequest() {
    this.totalRequests++;
  }

  /**
   * Increase total successful requests by 1.
   */
  public void addSuccessRequest() {
    this.totalSuccessRequest++;
  }

  /**
   * Get the sum of total latencies.
   * @return the sum of latencies.
   */
  public long getTotalLacency() {
    long total = 0;
    for (long latency: this.latencies) {
      total += latency;
    }
    return total;
  }
}
