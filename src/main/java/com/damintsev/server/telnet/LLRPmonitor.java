//package com.damintsev.server.telnet;
//
//import java.io.BufferedInputStream;
//import java.io.PrintStream;
//
//import org.apache.commons.net.telnet.TelnetClient;
//
///**
// * User:  Damintsev  Andrey
// * Date:  07.08.13
// * Time:  1:11
// */
//
//public class LLRPmonitor {
//
//    private TelnetClient telnet = new TelnetClient();
//    private BufferedInputStream input;
//    private PrintStream output;      //  output  stream
//    private final int waitTime = 500;  //wait  time  response  from  reader,  in  ms
//
//    public LLRPmonitor() {
//        super();
//    }
//
//    //                  /**
////                     *  Connect  to  reader
////                     *
////                     *  @param  userName
////                     *    :  The  user  name  for  login
////                     *  @param  password
////                     *    :  The  password  for  login
////                     *@param  IPReader
////                     *    :  IP  address  of  reader
////                     *  @return  boolean.  connect  OK  or  Fail
////                     *  @throws  IOException
////                     *  Any  problems  during  connect
////                     *  @author  ThangLe
////                     */
//    private boolean connect(String IPReader, String userName, String password) {
//        try {
//
//            if (telnet != null && telnet.isConnected()) {
//                telnet.disconnect();
//            }
//            if (input != null) {
//                input.close();
//            }
//            if (output != null) {
//                output.flush();
//                output.close();
//            }
//
//            //  Connect  to  the  specified  server
//            telnet.setConnectTimeout(5000);//  Timeout  5s
//            telnet.connect(IPReader, 23);
//
//            //  Get  input  and  output  stream  references       
//            input = new BufferedInputStream(telnet.getInputStream());
//            output = new PrintStream(telnet.getOutputStream());
//
//            Thread.sleep(waitTime);  //  wait  for  responding  from  reader
//            //  Log  the  user  on
//            if (readUntil("login:  ") == null) {
//                }
//            Thread.sleep(waitTime);
//            write(userName);
//            Thread.sleep(waitTime);  //  wait  for  responding  from  reader
//            if (readUntil("Password:  ") == null)
//            write(password);
//            Thread.sleep(waitTime);  //  wait  for  responding  from  reader
//            //  Advance  to  a  prompt
//            //  readUntil(prompt  +  "  ");
//            Thread.sleep(5000);
//            write("ls -l");
//            Thread.sleep(500);
//            readUntil("&!");
//
//
//            return true;  //  connect  OK
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;  //  connect  fail
//        }
//    }
//
//
//    //  ================================
//                  /*
//   *
//   */
//
//
//    private String readUntil(String pattern) {
//        System.out.println("read until");
//        try {
//            char lastChar = pattern.charAt(pattern.length() - 1);
//            StringBuffer sb = new StringBuffer();
//            int numRead = 0;
////
////            if (input.available() <= 1) {//reader  always  returns  more  5  chars
////                return null;
////            }
//            char ch = (char) input.read();
//
//            while (true) {
//                System.out.print(ch);
//                numRead++;
//                sb.append(ch);
//                if (ch == lastChar) {
//                    if (sb.toString().endsWith(pattern)) {
//                        return sb.toString();
//                    }
//                }
//
//                if (input.available() == 0) {
//                    break;
//                }
//                ch = (char) input.read();
//
//                if (numRead > 2000) {
//                    break;  //  can  not  read  the  pattern
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    //  =================================
//
//
//    private void write(String value) {
//        try {
//            output.println(value);
//            output.flush();
//              System.out.println(value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    //  ==================================
//
//
//    private void disconnect() {
//        try {
//            telnet.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //  ===================to  Test==================
//
//
//    public static void main(String[] args) {
//        try {
//            LLRPmonitor llrpMonitor = new LLRPmonitor();
//            llrpMonitor.connect("192.168.110.128", "sasha", "1");
//            llrpMonitor.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
