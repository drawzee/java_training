package stqa.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

    private final AppManager app;
    private FTPClient ftp;

    public FtpHelper(AppManager app) {
        this.app = app;
        ftp = new FTPClient();
    }

    //upload() creates new Mantis config with disabled captcha, restore() rolls back config
    public void upload(File file, String target, String backup) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.pass"));
        ftp.deleteFile(backup);
        ftp.rename(target, backup);
        ftp.enterLocalPassiveMode();
        ftp.storeFile(target, new FileInputStream(file));
        ftp.disconnect();
    }

    public void restore(String backup, String target) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.pass"));
        ftp.deleteFile(target);
        ftp.rename(backup, target);
        ftp.disconnect();
    }

}
