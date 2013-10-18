package com.damintsev.server.old.ftp;

import com.damintsev.client.old.devices.BillingInfo;
import com.damintsev.client.old.devices.FTPSettings;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 14.08.13
 * Time: 23:32
 */
public class FTPService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FTPService.class);
    private static FTPService instance;

    public static FTPService getInstance() {
        if(instance==null) instance = new FTPService();
        return instance;
    }

    private FTPService() {
        worker = new FTPWorker();
    }

    private FTPSettings settings;
    private FTPWorker worker;

    public void setSettings(FTPSettings settings) {
        this.settings = settings;
    }

    public synchronized List<BillingInfo> getBills() {
        if(settings==null) {
            logger.info("Settings is null returning empty array");
            return new ArrayList<BillingInfo>();
        }
        logger.info("Checking is connected =" + worker.isConnected());
        if(!worker.isConnected())
            worker.connect();
        String bills = worker.getFile(settings.getDir());

      return parse(bills);
    }

    public List<BillingInfo> parse(String bills) {
        logger.info("Start parsing returned info");
        //todo
        //todo
        //todo
        //todo
        return new ArrayList<BillingInfo>();
    }

    private class FTPWorker {
        private FTPClient ftpConnection;

        public void connect() {
            try {
                logger.info("Trying to connetc to host=" + settings.getHost() + " name=" + settings.getLogin() + " pswd=" + settings.getPassword());
                ftpConnection = new FTPClient();
                ftpConnection.connect(InetAddress.getByName(settings.getHost()));
                ftpConnection.login(settings.getLogin(), settings.getPassword());
                int reply = ftpConnection.getReplyCode();

                if(!FTPReply.isPositiveCompletion(reply)) {
                    ftpConnection.disconnect();
                }
            } catch (IOException e) {
                logger.error("Error connecting to ftp server " + e.getLocalizedMessage());
//                e.printStackTrace();
            }
        }

        public boolean isConnected() {
            return ftpConnection.isConnected();
        }

        public boolean checkDirectory(String basePath, String dirName) {
            FTPFile[] files = null;
            try {
                files = ftpConnection.listDirectories(basePath);
                for (FTPFile file : files) {
                    if (file.isDirectory() && file.getName().equals(dirName)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }

        public ArrayList<String> getFilesMatchesRegEx(String path, String regex) {
            FTPFile[]ftpFiles = new FTPFile[0];
            try {
                ftpFiles = ftpConnection.listFiles(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList <String> parsedFiles = new ArrayList<String>(ftpFiles.length);
            for (FTPFile file : ftpFiles) {
                if(!file.isDirectory()) {
                    String fileName = file.getName();
                    if(fileName.matches(regex)) {
                        parsedFiles.add(file.getName());
                    }
                }
            }
            return parsedFiles;
        }

        public FTPFile[] getDirContentFTPFile(String path) throws IOException {
            return ftpConnection.listFiles(path);
        }

        public String getFile(String filePath) {
            logger.info("Calling getFile with file=" + filePath);
            InputStream in = null;
            InputStreamReader inbf = null;
            BufferedReader buff = null;
            StringBuilder sb = new StringBuilder();
            try {
                in = ftpConnection.retrieveFileStream(filePath);
                inbf = new InputStreamReader(in);
                buff = new BufferedReader(inbf);
                String line;
                while ((line = buff.readLine()) != null) {
                    sb.append(line);
                }
                if(!ftpConnection.completePendingCommand()) {
                    ftpConnection.logout();
                    ftpConnection.disconnect();
                }
            } catch (Exception e) {
                if (ftpConnection.getReplyCode() == 550) {
                    return null;
                } else {
                    System.out.println(ftpConnection.getReplyCode());
                    e.printStackTrace();
//                throw new RuntimeException(e);
                }
            } finally {
                try {
                    if (buff != null) {
                        buff.close();
                    }
                    if (inbf != null) {
                        inbf.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logger.info("Readed from file: " + sb.toString());
            return sb.toString();
        }
    }
}
