package edu.northeastern.team36;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

public class LoadingAlertDialog {

    private Activity activity;
    private AlertDialog loadingAlertDialog;
    LoadingAlertDialog(Activity myActivity) {
        activity = myActivity;
    }

    public void startLoadingAlertDialog() {
        AlertDialog.Builder loadingAlertDialogBuilder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        loadingAlertDialogBuilder.setView(
                inflater.inflate(R.layout.loading_alert_dialog, null));
        loadingAlertDialogBuilder.setCancelable(false);

        loadingAlertDialog = loadingAlertDialogBuilder.create();
        loadingAlertDialog.show();
    }

    public void stopLoadingAlertDialog() {
        loadingAlertDialog.dismiss();
    }
}
