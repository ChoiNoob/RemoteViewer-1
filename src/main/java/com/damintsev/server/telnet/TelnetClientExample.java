package com.damintsev.server.telnet;

/**
 * Created by adamintsev
 * Date: 12.08.13 15:29
 */

import java.io.*;

import com.damintsev.server.Callback;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;


/**
 * This is a simple example of use of TelnetClient.
 * An external option handler (SimpleTelnetOptionHandler) is used.
 * Initial configuration requested by TelnetClient will be:
 * WILL ECHO, WILL SUPPRESS-GA, DO SUPPRESS-GA.
 * VT100 terminal type will be subnegotiated.
 * <p/>
 * Also, use of the sendAYT(), getLocalOptionState(), getRemoteOptionState()
 * is demonstrated.
 * When connected, type AYT to send an AYT command to the server and see
 * the result.
 * Type OPT to see a report of the state of the first 25 options.
 * <p/>
 *
 * @author Bruno D'Avanzo
 *         *
 */
public class TelnetClientExample implements Runnable, TelnetNotificationHandler {
    private TelnetClient tc = null;
    private OutputStream outstr;
    private PrintWriter writer;
    InputStream instr;


    public static void main(String[] args) {
        TelnetClientExample example = new TelnetClientExample();
        example.connect("192.168.110.128", "23");
    }

    /**
     * Main for the TelnetClientExample.
     * *
     */
    public void connect(String host, String port) {

        tc = new TelnetClient();

        TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
        EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
        SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);

        try {
            tc.addOptionHandler(ttopt);
            tc.addOptionHandler(echoopt);
            tc.addOptionHandler(gaopt);
        } catch (InvalidTelnetOptionException e) {
            System.err.println("Error registering option handlers: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
            try {
                tc.connect(host, Integer.parseInt(port));
                tc.registerNotifHandler(new TelnetClientExample());
                tc.setKeepAlive(true);

                instr = tc.getInputStream();
                outstr = tc.getOutputStream();
                writer = new PrintWriter(outstr);
                stream = new ByteArrayOutputStream();
                Thread reader = new Thread(this);
                reader.start();
                Thread.sleep(5000);
                write("sasha", new Callback() {
                    @Override
                    public void call() {
                        System.out.println("READED:" + getReaded());
                        write("1", new Callback() {
                            @Override
                            public void call() {
                                System.out.println("READED:" + getReaded());
                            }
                        });
                    }
                });

                write("ls -l", new Callback() {
                    @Override
                    public void call() {
                        System.out.println(getReaded());
                    }
                });
//                System.out.println("TelnetClientExample");
//                System.out.println("Type AYT to send an AYT telnet command");
//                System.out.println("Type OPT to print a report of status of options (0-24)");
//                System.out.println("Type REGISTER to register a new SimpleOptionHandler");
//                System.out.println("Type UNREGISTER to unregister an OptionHandler");
//                System.out.println("Type SPY to register the spy (connect to port 3333 to spy)");
//                System.out.println("Type UNSPY to stop spying the connection");



//                byte[] buff = new byte[1024];
//                int ret_read = 0;

//                do {
//                    try {
//                        ret_read = System.in.read(buff);
//                        if (ret_read > 0) {
//                            if ((new String(buff, 0, ret_read)).startsWith("AYT")) {
//                                try {
//                                    System.out.println("Sending AYT");
//
//                                    System.out.println("AYT response:" + tc.sendAYT(5000));
//                                } catch (IOException e) {
//                                    System.err.println("Exception waiting AYT response: " + e.getMessage());
//                                }
//                            } else if ((new String(buff, 0, ret_read)).startsWith("OPT")) {
//                                System.out.println("Status of options:");
//                                for (int ii = 0; ii < 25; ii++) {
//                                    System.out.println("Local Option " + ii + ":" + tc.getLocalOptionState(ii) + " Remote Option " + ii + ":" + tc.getRemoteOptionState(ii));
//                                }
//                            } else if ((new String(buff, 0, ret_read)).startsWith("REGISTER")) {
//                                StringTokenizer st = new StringTokenizer(new String(buff));
//                                try {
//                                    st.nextToken();
//                                    int opcode = Integer.parseInt(st.nextToken());
//                                    boolean initlocal = Boolean.parseBoolean(st.nextToken());
//                                    boolean initremote = Boolean.parseBoolean(st.nextToken());
//                                    boolean acceptlocal = Boolean.parseBoolean(st.nextToken());
//                                    boolean acceptremote = Boolean.parseBoolean(st.nextToken());
//                                    SimpleOptionHandler opthand = new SimpleOptionHandler(opcode, initlocal, initremote,
//                                            acceptlocal, acceptremote);
//                                    tc.addOptionHandler(opthand);
//                                } catch (Exception e) {
//                                    if (e instanceof InvalidTelnetOptionException) {
//                                        System.err.println("Error registering option: " + e.getMessage());
//                                    } else {
//                                        System.err.println("Invalid REGISTER command.");
//                                        System.err.println("Use REGISTER optcode initlocal initremote acceptlocal acceptremote");
//                                        System.err.println("(optcode is an integer.)");
//                                        System.err.println("(initlocal, initremote, acceptlocal, acceptremote are boolean)");
//                                    }
//                                }
//                            } else if ((new String(buff, 0, ret_read)).startsWith("UNREGISTER")) {
//                                StringTokenizer st = new StringTokenizer(new String(buff));
//                                try {
//                                    st.nextToken();
//                                    int opcode = (new Integer(st.nextToken())).intValue();
//                                    tc.deleteOptionHandler(opcode);
//                                } catch (Exception e) {
//                                    if (e instanceof InvalidTelnetOptionException) {
//                                        System.err.println("Error unregistering option: " + e.getMessage());
//                                    } else {
//                                        System.err.println("Invalid UNREGISTER command.");
//                                        System.err.println("Use UNREGISTER optcode");
//                                        System.err.println("(optcode is an integer)");
//                                    }
//                                }
//                            } else if ((new String(buff, 0, ret_read)).startsWith("SPY")) {
//                                tc.registerSpyStream(fout);
//                            } else if ((new String(buff, 0, ret_read)).startsWith("UNSPY")) {
//                                tc.stopSpyStream();
//                            } else {
//                                try {
//                                    outstr.write(buff, 0, ret_read);
//                                    outstr.flush();
//                                } catch (IOException e) {
//                                    end_loop = true;
//                                }
//                            }
//                        }
//                    } catch (IOException e) {
//                        System.err.println("Exception while reading keyboard:" + e.getMessage());
//                        end_loop = true;
//                    }
//                }
//                while ((ret_read > 0) && (end_loop == false));
//
//                try {
//                    tc.disconnect();
//                } catch (IOException e) {
//                    System.err.println("Exception while connecting:" + e.getMessage());
//                }
            } catch (IOException e) {
                System.err.println("Exception while connecting:" + e.getMessage());
                System.exit(1);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
//        }
    }


    /**
     * Callback method called when TelnetClient receives an option
     * negotiation command.
     * <p/>
     *
     * @param negotiation_code - type of negotiation command received
     *                         (RECEIVED_DO, RECEIVED_DONT, RECEIVED_WILL, RECEIVED_WONT)
     *                         <p/>
     * @param option_code      - code of the option negotiated
     *                         <p/>
     *                         *
     */
//    @Override
    public void receivedNegotiation(int negotiation_code, int option_code) {
        String command = null;
        if (negotiation_code == TelnetNotificationHandler.RECEIVED_DO) {
            command = "DO";
        } else if (negotiation_code == TelnetNotificationHandler.RECEIVED_DONT) {
            command = "DONT";
        } else if (negotiation_code == TelnetNotificationHandler.RECEIVED_WILL) {
            command = "WILL";
        } else if (negotiation_code == TelnetNotificationHandler.RECEIVED_WONT) {
            command = "WONT";
        }
        System.out.println("Received " + command + " for option code " + option_code);
    }

    public void write(String command, Callback callback) {
        readData();
        command += "\n\r";
        System.out.println("write!");
        try {
            outstr.write(command.getBytes());
            outstr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//            writer.write(command);
//        writer.flush();
        System.out.println("flush");
        readData();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callback.call();
    }

    /**
     * Reader thread.
     * Reads lines from the TelnetClient and echoes them
     * on the screen.
     * *
     */
//    @Override
    public void run() {
        System.out.println("Start reader!!!!");
        try {
            byte[] buff = new byte[1024];
            int ret_read = 0;

            do {
                ret_read = instr.read(buff);
                if (ret_read > 0) {
//                    System.out.print(new String(buff, 0, ret_read));
                    stream.write(buff);
                }
            }
            while (ret_read >= 0);
        } catch (IOException e) {
            System.err.println("Exception while reading socket:" + e.getMessage());
        }

//        try {
            System.out.println("exit  бля");
//            tc.disconnect();
//        } catch (IOException e) {
//            System.err.println("Exception while closing telnet:" + e.getMessage());
//        }
    }
    ByteArrayOutputStream stream;
    public void readData() {
        stream.reset();
    }

    public String getReaded() {
        return stream.toString();
    }
}
