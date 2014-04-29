package gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.energyharvest.R;

/**
 * @version 1.1.3 (12/04/2014)
 * @author Kjell Bunjes
 *
 */

public class UsernameNotExistingDialog extends DialogFragment {
	
	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(R.string.username_not_found)
	               .setPositiveButton(R.string.button_confirm, new DialogInterface.OnClickListener() {
	                   @Override
					public void onClick(DialogInterface dialog, int id) {
	                       // FIRE ZE MISSILES!
	                	   dialog.dismiss();
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
}
