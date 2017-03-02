package chapter31;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Exercise31_9ClientController implements Initializable{

	 @FXML
	    private TextArea taClient;

	    @FXML
	    private TextField tfClient;
	    
	    private DataOutputStream outputToServer;
	    
	    private DataInputStream inputFromServer;
	    
	    Socket soServer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
					
		
		new Thread(()->{
			
			tfClient.setOnAction(e->{
				
				try{
				
				//get text from user
				String textToServer = tfClient.getText().trim();
				
				
				//send text from client to server
				
				outputToServer.writeUTF(textToServer);
				tfClient.clear();
				outputToServer.flush();
				taClient.appendText("Client: " + textToServer + '\n');
			
				}//end try
			
			catch(IOException e1){
				e1.printStackTrace();
			}//end catch
				
			});//end setOnAction
			
		try{
			Socket skt = new Socket ("localhost", 8000);
			inputFromServer = new DataInputStream(skt.getInputStream());
			outputToServer = new DataOutputStream(skt.getOutputStream());
			
			taClient.appendText("Connected to server at: " + new Date() + '\n');
			
		}
		
		catch(IOException ex){
			taClient.appendText(ex.toString());
		}
		
		try{
			
			while(true){
				
			//recieve text from server
			String textFromServer = inputFromServer.readUTF().trim();
			taClient.appendText("Server: " + textFromServer + '\n');
		
			
			}//end while
			
		}//end try
	catch(IOException ex){
			ex.printStackTrace();
	}//end catch
	
}).start();
	

		
	}//end initialize
	
}//end Client Controller
