package no.ntnu;

/**
 * MAin entrypoint for the application
 */
public class Main {
  public static void main(String[] args) {
    UdpSocketHandler sender = new UdpSocketHandler();
    sender.run();
  }
}