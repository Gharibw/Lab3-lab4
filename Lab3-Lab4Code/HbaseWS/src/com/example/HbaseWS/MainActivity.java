package com.example.HbaseWS;

import java.io.File;

import com.example.hbaseclientapplication.R;

import net.neoremind.sshxcute.core.ConnBean;
import net.neoremind.sshxcute.core.SSHExec;
import net.neoremind.sshxcute.exception.TaskExecFailException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	Button createTableButton;
	Button insertTableButton;
	Button retrieveTableButton;
	Button deleteTableButton;
	Button upload;
	Button TrainFileButton;
	Button TestButton;
	
	EditText tableName;
	EditText familyName;
	Uri uri;
	Intent intent;
	Intent chooser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createTableButton = (Button) findViewById(R.id.createTableButton);
		insertTableButton = (Button) findViewById(R.id.insertTableButton);
		retrieveTableButton = (Button) findViewById(R.id.retrieveTableButton);
		deleteTableButton = (Button) findViewById(R.id.deleteTableButton);
		TestButton = (Button) findViewById(R.id.TestButton);
		TrainFileButton = (Button) findViewById(R.id.TrainFileButton);
		upload = (Button)findViewById(R.id.upload);
		
		tableName = (EditText)findViewById(R.id.tableName);
		familyName = (EditText)findViewById(R.id.coloumFamily);

		createTableButton.setOnClickListener(this);
		insertTableButton.setOnClickListener(this);
		retrieveTableButton.setOnClickListener(this);
		deleteTableButton.setOnClickListener(this);
		upload.setOnClickListener(this);
		TrainFileButton.setOnClickListener(this);
		TestButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		
		String tabName = tableName.getText().toString();
		
		String family = familyName.getText().toString();

		if (v.getId() == R.id.createTableButton) {
			
			uri = Uri
					.parse("http://134.193.136.127:8080/HbaseWS/jaxrs/generic/hbaseCreate/"+tabName+"/"+family);

			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);

		} else if (v.getId() == R.id.insertTableButton) {

			uri = Uri
					.parse("http://134.193.136.127:8080/HbaseWS/jaxrs/generic/hbaseInsert/"+tabName+"/-home-group4-GharibiData.txt/"+family);

			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);

		} else if (v.getId() == R.id.retrieveTableButton) {
			uri = Uri
					.parse("http://134.193.136.127:8080/HbaseWS/jaxrs/generic/hbaseRetrieveAll/"+tabName);

			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);

		} else if (v.getId() == R.id.deleteTableButton) {
			uri = Uri
					.parse("http://134.193.136.127:8080/HbaseWS/jaxrs/generic/hbasedeletel/"+tabName);

			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);

		}else if (v.getId() == R.id.upload){
			
			connectSSH();
		}
		
		
		else if (v.getId() == R.id.TrainFileButton) {
			
			uri = Uri.parse("http://134.193.136.127:8080/HMMWS/jaxrs/generic/TrainFileOperation/-home-group4-GRTL.txt/-home-group4-GRTL.seq");

			uri = Uri.parse("http://134.193.136.127:8080/HMMWS/jaxrs/generic/TrainFileOperation/-home-group4-GLTR.txt/-home-group4-GLTR.seq");

			uri = Uri.parse("http://134.193.136.127:8080/HMMWS/jaxrs/generic/TrainFileOperation/-home-group4-GBTT.txt/-home-group4-GBTT.seq");

			
			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);
			}
		
		
		else if (v.getId() == R.id.TestButton) {
			
			uri = Uri.parse("http://134.193.136.127:8080/HMMWS/jaxrs/generic/HMMTrainingTest/-home-group4-GBTT.seq:-home-group4-GRTL.seq:-home-group4-GLTR.seq/btp:rtol:ltor/-home-group4-TestGharib.seq");

			
			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);
			}
	}
	
	 public  void connectSSH()
		{
			// Initialize a SSHExec instance without referring any object. 
			SSHExec ssh = null;
			// Wrap the whole execution jobs into try-catch block   
			try {
			    // Initialize a ConnBean object, parameter list is ip, username, password
			    ConnBean cb = new ConnBean("134.193.136.127", "group4","group4");
			    // Put the ConnBean instance as parameter for SSHExec static method getInstance(ConnBean) to retrieve a real SSHExec instance
			    ssh = SSHExec.getInstance(cb);              
			
			    // Connect to server
			    ssh.connect();
			    // Upload sshxcute_test.sh to /home/tsadmin on remote system
			    File file = new File("/storage/sdcard0/Lab4/punch.txt");
			    if(file.exists()){
			    ssh.uploadSingleDataToServer("/storage/sdcard0/Lab4/punch.txt", "/home/group4/");
			    }else{
			    	System.out.println("no file exits");
			    }
		
			} 
			catch (TaskExecFailException e) 
			{
			    System.out.println(e.getMessage());
			    e.printStackTrace();
			} 
			catch (Exception e) 
			{
			    System.out.println(e.getMessage());
			    e.printStackTrace();
			} 
			finally 
			{
			    ssh.disconnect();   
			}
		}
}
