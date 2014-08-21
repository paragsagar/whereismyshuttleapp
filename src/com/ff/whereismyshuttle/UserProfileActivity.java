package com.ff.whereismyshuttle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.ff.whereismyshuttle.domain.User;

public class UserProfileActivity extends BaseActivity {
	
	private EditText etuserEmail ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String userEmail = readEmail();
		setContentView(R.layout.activity_user_profile);
		etuserEmail = (EditText)findViewById(R.id.etEmail);
		
		if(userEmail!=null && userEmail.length()>0)
		{
			etuserEmail.setText(userEmail);
		}
	}
	
	public void addEmailAndNext(View v)
	{
		writeEmail();	
		goSelectRoutes();
	}
	
	private String readEmail() {
		return User.getEmail(getFilesDir());
	}

	private void goSelectRoutes() {
		// TODO Auto-generated method stub
		Intent i = new Intent(UserProfileActivity.this, RoutesActivity.class);
		  // put "extras" into the bundle for access in the second activity
		  startActivity(i);
	}
		
	private void writeEmail() {
		// TODO Auto-generated method stub
		String userEmail = etuserEmail.getText().toString();
		(new User()).setEmail(userEmail,getFilesDir());
		writeToServer();
        Toast.makeText(this, userEmail +" Saved!", Toast.LENGTH_SHORT).show();

	}
	
	private void writeToServer() {
		// TODO Auto-generated method stub
		
	}

	
}
