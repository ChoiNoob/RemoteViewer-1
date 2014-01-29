package com.damintsev.server.v2.v3.connections.telnet;

/**
 * Created by adamintsev
 * Date: 12.08.13 15:29
 * //todo интегрировать с другими классами!
 */

import com.damintsev.common.uientity.Response;
import com.damintsev.server.v2.v3.exceptions.ConnectionException;
import org.apache.commons.net.telnet.*;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * This is a simple example of use of TelnetWorker.
 * An external option handler (SimpleTelnetOptionHandler) is used.
 * Initial configuration requested by TelnetWorker will be:
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
public class TelnetWorker extends Thread implements TelnetNotificationHandler {

    private static final Logger logger = Logger.getLogger(TelnetWorker.class);
    private org.apache.commons.net.telnet.TelnetClient tc = null;
    private OutputStream outstr;
    private ByteArrayOutputStream stream;
    private InputStream instr;
    private String host;
    private String port;
    private String login;
    private String password;
    private boolean keepAlive = false;
    private Long timeout;
    private boolean allowTimeout = true;
    private boolean authentif = true;

    public static void main(String[] args) throws ConnectionException {
        TelnetWorker example = new TelnetWorker();
        example.setLogin("root");
        example.setPassword("hicom");
        example.setHost("192.0.2.3");
        example.setPort("1202");
        example.connect();
    }

    public TelnetWorker() {
    }

    public Response connect() throws ConnectionException {
        Response response = new Response();
        tc = new org.apache.commons.net.telnet.TelnetClient();
        TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
        EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
        SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
        try {
            tc.addOptionHandler(ttopt);
            tc.addOptionHandler(echoopt);
            tc.addOptionHandler(gaopt);
        } catch (InvalidTelnetOptionException e) {
            logger.error("Error registering option handlers: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            logger.debug("Trying to connect to server host=" + host + " port=" + port + " login=" + login + " pswd=" + password);
            tc.connect(host, Integer.parseInt(port));
            tc.registerNotifHandler(this);
            tc.setKeepAlive(keepAlive);
            if(allowTimeout)
                if(timeout == null) tc.setSoTimeout(60000);//1 minute
                else tc.setSoTimeout(timeout.intValue());

            instr = tc.getInputStream();
            outstr = tc.getOutputStream();
            stream = new ByteArrayOutputStream();

            if (authentif) {
                Thread reader = new Thread(this);
                reader.start();
                Thread.sleep(3000);

                write("");    //todo customize this!
                write(login);
                write(password);
            }
               logger.debug("connection sucsessful!");
               response.setResult(true);
               response.setResultText("Success");
//           }
        } catch (Exception e) {
            logger.error("Exception while connecting to server host=" + host + " port=" + port + " login=" + login + " pswd=" + password + e.getMessage());
            response.setResultText("Exception while connecting to server host=" + host + " port=" + port + " login=" + login + " pswd=" + password + e.getMessage());
            response.setResult(false);
            throw new ConnectionException(e);
        }
        return response;
    }

    private Response write(String command) throws ConnectionException {
        clearReaded();
        command += "\r\n";
        logger.debug("***Sending command to remote server: " + command);
        try {
            outstr.write(command.getBytes());
            outstr.flush();
            Thread.sleep(5000);
        } catch (Exception e) {
           logger.debug("Exception when reading command" + e.getMessage(), e);
            throw new ConnectionException(e);
        }
        String result = getReaded();
        logger.debug("***Readed from server: " + result);
        return new Response(result);
    }

    public Response execute(String command) throws ConnectionException {
        return write(command);
    }

//todo change
    public void run() {
//        System.out.println("Start reader!!!!");
        try {
            byte[] buff = new byte[1024];
            int ret_read = 0;
            do {
                ret_read = instr.read(buff);
                if (ret_read > 0) {
                    logger.debug("\t\t\t" + new String(buff, 0, ret_read));
                    stream.write(buff, 0, ret_read);
                }
            }
            while (ret_read >= 0);
        } catch (IOException e) {
            logger.error("Exception while reading socket:" + e.getMessage(), e);
            try {
                logger.info("Disconnecting from server");
                tc.disconnect();
            } catch (IOException e1) {
                logger.error("Exception while disconnecting from socket:" + e.getMessage(), e);
            }
        }
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
            super.interrupt();
            tc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public Response sendAYT() {
//        clearReaded();
//        boolean boolRes = false;
//        try {
//            logger.info("Sending AYT command!");
//            boolRes = tc.sendAYT(3000);
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        String result = getReaded();
//        logger.info("Test server. Send AYT. result=" + result.contains("yes") + " boolean result=" + boolRes);
//        Response response = new Response();
//        response.setResultText(result);
//        response.setResult(result.contains("yes") && boolRes);
//        if(result.contains("yes") && boolRes)
//            response.setStatus(Status.WORK);
//        else
//            response.setStatus(Status.ERROR);
//        return response;
//    }

    /**
     * Callback method called when TelnetWorker receives an option
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

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public boolean isAllowTimeout() {
        return allowTimeout;
    }

    public void setAllowTimeout(boolean allowTimeout) {
        this.allowTimeout = allowTimeout;
    }

    public void setAutnentication(boolean authentif) {
        this.authentif = authentif;
    }

    public InputStream getInputStream() {
        return instr;
    }

    public boolean isConnected() {
        return tc != null && tc.isConnected();
    }
}
