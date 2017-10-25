package com.example.alejandro.inventariouabjo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Alejandro on 01/03/2017.
 */

public class barcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {




    private ZXingScannerView mScannerView;
    Intent intent= new Intent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode);
        intent.putExtra("resultado","Error de Lectura");
        setResult(RESULT_CANCELED,intent);

        QrScanner();
    }

    public void QrScanner(){


        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(final Result rawResult) {
        // Do something with the result here
        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        // show the scanner result into dialog box.
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/
        intent.putExtra("resultado",rawResult.getText());
        setResult(RESULT_OK,intent);
        finish();
        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }
}
