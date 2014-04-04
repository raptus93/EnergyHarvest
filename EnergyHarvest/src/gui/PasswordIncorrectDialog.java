package gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.energyharvest.R;

public class PasswordIncorrectDialog extends DialogFragment {
	
	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(R.string.password_incorrect)
	               .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // FIRE ZE MISSILES!
	                	   dialog.dismiss();
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
}
