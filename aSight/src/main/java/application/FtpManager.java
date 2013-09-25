package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpManager {

	FTPClient ftp = null;

	public FtpManager( String host, String user, String pwd ) throws Exception {
		ftp = new FTPClient();

		ftp.addProtocolCommandListener( new PrintCommandListener( new PrintWriter( System.out ) ) );
		int reply;
		ftp.connect( host );
		reply = ftp.getReplyCode();
		if ( !FTPReply.isPositiveCompletion( reply ) ) {
			ftp.disconnect();
			throw new Exception( "Exception in connecting to FTP Server" );
		}
		ftp.login( user, pwd );
		ftp.setFileType( FTP.BINARY_FILE_TYPE );
		ftp.enterLocalPassiveMode();
	}

	public void uploadFile( String localFileFullName, String fileName, String hostDir ) throws Exception {
		try ( InputStream input = new FileInputStream( new File( localFileFullName ) ) ) {
			this.ftp.storeFile( hostDir + fileName, input );
		}
	}

	public void deleteFile( String fileName, String hostDir ) throws Exception {
		this.ftp.deleteFile( hostDir + fileName );

	}

	public void disconnect() {
		if ( this.ftp.isConnected() ) {
			try {
				this.ftp.logout();
				this.ftp.disconnect();
			} catch ( IOException f ) {
				// do nothing as file is already saved to server
			}
		}
	}

	public static String uploadFileToFtp( String host, String user, String pwd, String localFileName, String fileName,
			String hostDir ) {
		try {
			FtpManager ftpUploader = new FtpManager( host, user, pwd );
			ftpUploader.uploadFile( localFileName, fileName, hostDir );
			ftpUploader.disconnect();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		// http://www.asight.bplaced.net/searchImage/Image1380135838979.jpg
		// return "ftp://" + user + ":" + pwd + "@" + host + hostDir + "/" + fileName;
		return "http://www.asight.bplaced.net/" + fileName; 
	}

	public static void deleteFileFromFtp( String host, String user, String pwd, String fileName, String hostDir ) {
		try {
			FtpManager ftpUploader = new FtpManager( host, user, pwd );
			ftpUploader.deleteFile( fileName, hostDir );
			ftpUploader.disconnect();
		} catch ( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
