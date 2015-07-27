package com.google.research.ic.ferret.comm;

import com.google.research.ic.ferret.MainFrame;
import com.google.research.ic.ferret.test.Debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class RemoteConnector implements Runnable {

  private String host;

  private int port;

  private Socket client;

  private LinkedList<DeviceLogEventListener> sampleObservers;

  public RemoteConnector(String host, int port) {
    this.host = host;
    this.port = port;
    client = null;
    sampleObservers = new LinkedList<DeviceLogEventListener>();
  }

  public void addEventListener(DeviceLogEventListener observer) {
    sampleObservers.addFirst(observer);
  }

  public void removeSampleObserver(DeviceLogEventListener observer) {
    sampleObservers.remove(observer);
  }

  @Override
  public void run() {
    Debug.log("Running RemoteConnector Thread");
    while (!Thread.currentThread().isInterrupted()) {
      client = null;
      System.out.println("Gonna try to connect to the client at " + host + ":" + port);
      try {
        client = new Socket(host, port);
        System.out.println("connected to client: " + client.getLocalPort() );
        PrintStream out = new PrintStream(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String line = null;
        boolean connected = false;
        //System.out.print("Searching for connection...");
        while (!Thread.currentThread().isInterrupted()) {
          line = in.readLine();
          if (line == null) {
            //System.out.print(".");
            break;
          }
          if (line.startsWith("hi from server")) {
            Debug.log("Connected to the device:" + line);
            connected = true;
            break;
          }
        }

        if (line != null) {
          //System.err.println(line);

          String demoMode = "false";
          if (MainFrame.getMainFrame() != null) {
            demoMode = "" + MainFrame.getMainFrame().isDemoMode();
          }
          out.println("DEMO=" + demoMode);
          out.flush();
          if (connected) {
            System.out.println("connected");
            while (!Thread.currentThread().isInterrupted()) {
              line = in.readLine();
              if (line == null) {
                //System.out.println("socket reads null");
                break;
              }
              int n = Integer.parseInt(line);
              for (int i = 0; i < n; ++i) {
                line = in.readLine();
                if (line == null) {
                  //System.out.println("socket reads null");
                  break;
                }
                //System.out.println("Read a line from the device: " + line);
                for (DeviceLogEventListener sampleObserver : sampleObservers) {
                  sampleObserver.onEvent(line);
                }
              }
              out.println(n);
              out.flush();
            }
          }
        }
      } catch (UnknownHostException e) {
        e.printStackTrace();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }

      if (client != null) {
        try {
          client.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        client = null;
      }
    }
  }
}
