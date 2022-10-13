package no.ntnu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Communicate with the UDP task server, solve the problems according to the protocol
 * Solution for UDP Socket programming exercise in IDATA2304 Computer networks
 * https://github.com/ntnu-datakomm/enigma-72
 */
public class UdpSocketHandler {
  /**
   *
   * IP address of the UDP task server
   */
  public static final String SERVER_IP_ADDRESS = "129.241.152.12";
  /**
   * UDP port number for the UDP task server
   */
  public static final int SERVER_UDP_PORT = 1234;

  // UDP socket used for communication
  DatagramSocket clientSocket;
  // Buffer where the incoming data from UDP socket will be stored
  byte[] responseDataBuffer = new byte[1024]; // Reserve a bit more space than one would normally need
  // We will reuse this packet for incoming UDP packets
  DatagramPacket receivePacket = new DatagramPacket(responseDataBuffer, responseDataBuffer.length);
  /**
   * Communicate with the UDP server according to the protocol
   */
  public void run() {
    try {
      clientSocket = new DatagramSocket();
    } catch (Exception e) {
      System.out.println("Something went wrong " + e);
    }
    Scanner scanner = new Scanner(System.in);
  while (true) {
    System.out.println("Starting UDP socket handler. Write 'task' to get a task: ");
    String userInput = scanner.nextLine();
  if (sendTaskRequest() && userInput.equals("task")) {
    System.out.println("Task request sent successfully");
    String task = listenForResponse();
    System.out.println("Got the following task from the server: " + task);
    String answer = TaskLogic.solveTask(task);
    if (answer != null) {
      System.out.println("Our answer is " + answer);
      sendToServer(answer);
      String response = listenForResponse();
      if (TaskLogic.hasServerApproved(response)) {
        System.out.println("Good, task solved!");
      } else {
        System.out.println("Oops, something wrong, server said: " + response);
      }
    }
  }
  System.out.println(" ");
}
  }



  /**
   * Wait for a response from the UDP task server
   *
   * @return The response from the server; null on error
   */
  private String listenForResponse() {
    String response = null;
    // Code adapted from https://github.com/ntnu-datakomm/server-side/blob/main/example-udp-server/src/main/java/no/ntnu/UdpClient.java
    try {
      clientSocket.receive(receivePacket);
      response = new String(receivePacket.getData(), 0, receivePacket.getLength());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return response;
  }

  /**
   * Send a new task request to the server
   *
   * @return True on success, false on error
   */
  private boolean sendTaskRequest() {
    return sendToServer(TaskLogic.TASK_REQUEST);
  }

  /**
   * Send a UDP message to the server
   *
   * @param message The message to send
   * @return True on success, false on error
   */
  private boolean sendToServer(String message) {
    boolean success = false;

    // Code adapted from
    // https://github.com/ntnu-datakomm/server-side/blob/main/example-udp-server/src/main/java/no/ntnu/UdpClient.java
    byte[] dataToSend = message.getBytes();
    try {
      InetAddress serverAddress = InetAddress.getByName(SERVER_IP_ADDRESS);
      DatagramPacket sendPacket = new DatagramPacket(dataToSend, dataToSend.length, serverAddress, SERVER_UDP_PORT);
      clientSocket.send(sendPacket);
      success = true;
    } catch (Exception e) {
      System.out.println("Could not send message to server: " + e.getMessage());
    }

    return success;
  }
}