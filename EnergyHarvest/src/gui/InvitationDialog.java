package gui;

import node.Callback;
import node.Server;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.energyharvest.R;

/**
 * @version 1.1.4 (10/05/2014)
 * @author Kjell Bunjes
 *
 */

public class InvitationDialog extends Dialog implements android.view.View.OnClickListener{
	
	private Button btnConfirm, btnDecline;
	
	public InvitationDialog(Context context) {
		super(context);
		Log.i("test", "entered");
		setContentView(R.layout.dialog_invite);
    	setTitle("Du wurdest eingeladen!");
    	setCanceledOnTouchOutside(false);
    	btnConfirm = (Button) findViewById(R.id.dialog_button_confirm);
    	btnDecline = (Button) findViewById(R.id.dialog_button_decline);
    	btnConfirm.setOnClickListener(this);
    	btnDecline.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		/***
         * ACCEPT / DECLINE THE CHALLENGE
         * **/
		if(v.getId() == R.id.dialog_button_confirm) {			
            Server.getInstance().challengeResponse(true,
                /** success [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {

                    }
                },
                /** fail [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {

                    }
                });
            dismiss();
		}
		else if(v.getId() == R.id.dialog_button_decline) {
			Server.getInstance().challengeResponse(false,
	                /** success [no input] **/
	                new Callback() {
	                    @Override
	                    public void callback(Object... input) {

	                    }
	                },
	                /** fail [no input] **/
	                new Callback() {
	                    @Override
	                    public void callback(Object... input) {

	                    }
	                });
			dismiss();
		}
	}

}
