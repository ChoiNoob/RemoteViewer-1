package com.damintsev.server.telnet;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

/**
* User:  Damintsev  Andrey
* Date:  07.08.13
* Time:  1:11
*/

public class Telnet extends Thread {

    private TelnetClient telnet;
    private InputStream input;
    private PrintStream output;      //  output  stream
    private final int waitTime = 1000;  //wait  time  response  from  reader,  in  ms
    private String host;
    private String port;
    private String login;
    private String password;

    public Telnet() {
        telnet = new TelnetClient();
    }

    public boolean connect() {
        try {
            if (telnet != null && telnet.isConnected()) {
                telnet.disconnect();
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.flush();
                output.close();
            }

//            telnet.setConnectTimeout(5000);
            telnet.connect(host, Integer.parseInt(port));

            input = (telnet.getInputStream());
            output = new PrintStream(telnet.getOutputStream());

            Thread.sleep(waitTime);
            if (readUntil("login:  ") == null) {
            }
            Thread.sleep(waitTime);
            write(login);
            Thread.sleep(waitTime);
            if (readUntil("Password:  ") == null)
                write(password);
            Thread.sleep(waitTime);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String readUntil(String pattern) {
        try {
            char lastChar = 0;
            if (pattern != null) lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            int numRead = 0;
            char ch = (char) input.read();
            while (true) {
                System.out.print(ch);
                numRead++;
                sb.append(ch);
                if (pattern != null) {
                    if (ch == lastChar) {
                        if (sb.toString().endsWith(pattern)) {
                            return sb.toString();
                        }
                    }
                }
                if (input.available() == 0) {
                    break;
                }
                ch = (char) input.read();

                if (numRead > 5000) {
                    break;  //  can  not  read  the  pattern
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String read() {
        StringBuilder sb = new StringBuilder();
        try {
        
            
            byte[] buff = new byte[1024];
            int ret_read = 0;
            do {
                ret_read = input.read(buff);
                if (ret_read > 0) {
                    System.out.print(new String(buff, 0, ret_read));
                    sb.append(new String(buff, 0, ret_read));
                }
            }
            while (ret_read >= 0);
        } catch (Exception e) {
            System.err.println("Exception while reading socket:" + e.getMessage());
        }
        return sb.toString();
    }

    private void write(String value) {
        try {
            output.println(value);
            output.flush();
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String executeCommand(String query) {
        write(query);
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return read();
    }

    public static void main(String[] args) {
        Telnet telnet1 = new Telnet();
//        telnet1.setHost("192.168.110.128");
//        telnet1.setPort("23");
//        telnet1.setLogin("sasha");
//        telnet1.setPassword("1");
        telnet1.setHost("127.0.0.1");
        telnet1.setPort("23");
        telnet1.connect();
        System.out.println(telnet1.executeCommand("ls -l"));
    }
}

