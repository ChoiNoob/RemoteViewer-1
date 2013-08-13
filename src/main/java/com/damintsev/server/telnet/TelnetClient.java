package com.damintsev.server.telnet;

/**
 * Created by adamintsev
 * Date: 12.08.13 15:29
 */

import java.io.*;

import com.damintsev.client.devices.TestResponse;
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
public class TelnetClient implements Runnable, TelnetNotificationHandler {
    private org.apache.commons.net.telnet.TelnetClient tc = null;
    private OutputStream outstr;
    private ByteArrayOutputStream stream;
    private InputStream instr;
    private String host;
    private String port;
    private String login;
    private String password;


    public static void main(String[] args) throws IOException {
        TelnetClient example = new TelnetClient();
        example.setLogin("sasha");
        example.setPassword("1");
        example.setHost("192.168.110.129");
        example.setPort("23");
        example.connect();
    }

    /**
     * Main for the TelnetClient.
     * *
     */
    public boolean connect() throws IOException {

        tc = new org.apache.commons.net.telnet.TelnetClient();
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
            tc.registerNotifHandler(new TelnetClient());
            tc.setKeepAlive(true);

            instr = tc.getInputStream();
            outstr = tc.getOutputStream();
            stream = new ByteArrayOutputStream();
            Thread reader = new Thread(this);
            reader.start();
            Thread.sleep(3000);
            write(login);
            write(password);
//            System.out.println(write("ls -l"));

            testConnection();
        } catch (IOException e) {
            System.err.println("Exception while connecting:" + e.getMessage());
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String write(String command) {
        clearReaded();
        command += "\n\r";
        System.out.println("Sending command to remote server: " + command);
        try {
            outstr.write(command.getBytes());
            outstr.flush();
            Thread.sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = getReaded();
        System.out.println("Readed from server: " + result);
        return result;
    }

    public String execute(String command) {
        return write(command);
    }

    /**
     * Reader thread.
     * Reads lines from the TelnetClient and echoes them
     * on the screen.
     * *
     */
    public void run() {
        System.out.println("Start reader!!!!");
        try {
            byte[] buff = new byte[1024];
            int ret_read = 0;
            do {
                ret_read = instr.read(buff);
                if (ret_read > 0) {
//                    System.out.print("\t\t\t" + new String(buff, 0, ret_read));
                    stream.write(buff, 0, ret_read);
                }
            }
            while (ret_read >= 0);
        } catch (IOException e) {
            System.err.println("Exception while reading socket:" + e.getMessage());
        }
        System.out.println("exit  бля");
    }

    private void clearReaded() {
        stream.reset();
    }

    private String getReaded() {
        return stream.toString();
    }

    public void disconnect() {
        try {
            outstr.close();
            instr.close();
            stream.close();
            tc.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TestResponse testConnection() {
        clearReaded();
        try {
            System.out.println("Sending AYT command!");
            tc.sendAYT(3000);
            Thread.sleep(9000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = getReaded();
        System.out.println("Test server. Send AYT. result=" + result.contains("yes"));
        TestResponse response = new TestResponse();
        response.setResultText(result);
        response.setResult(result.contains("yes"));
        return response;
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
//        System.out.println("Received " + command + " for option code " + option_code);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
