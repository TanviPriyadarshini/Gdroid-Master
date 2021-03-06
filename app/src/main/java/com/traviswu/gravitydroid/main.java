package com.traviswu.gravitydroid;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class main extends Activity {

    final Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonFb).setOnTouchListener(OnTouch);
        findViewById(R.id.buttonCall).setOnTouchListener(OnTouch);
        findViewById(R.id.buttonMail).setOnTouchListener(OnTouch);
        findViewById(R.id.buttonTwitter).setOnTouchListener(OnTouch);
        findViewById(R.id.buttonPlanet).setOnDragListener(dragListener);

        //Button buttonGen = (Button) findViewById(R.id.qrCode);
        //buttonGen.setOnClickListener(startActivities(new Intent););

        Button buttonPlanet = (Button) findViewById(R.id.buttonPlanet);
        buttonPlanet.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        startActivity(new Intent(main.this, QRScan.class));
                        //Read QR Code
                        //scanQR(v);



                        //Generate QR Code
                        /*
                        ImageView imageView = (ImageView) findViewById(R.id.qrCode);

                        String qrData = "Data I want to encode in QR code";
                        int qrCodeDimention = 500;

                        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,
                                Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

                        try {
                            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                            imageView.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
        );
    ImageButton buttonMessage = (ImageButton)findViewById(R.id.twilio);
        buttonMessage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                startActivity(new Intent(main.this, HelloMonkeyActivity.class));
            }
//            @Override
//            public void onClick(View arg0) {
//                //get prompt.xml view
//                LayoutInflater li = LayoutInflater.from(context);
//                View promptsView = li.inflate(R.layout.twilioprompt,null);
//
//                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//
//                // set prompt.xml to alergdialog builder
//                alertDialogBuilder.setView(promptsView);
//                //set dialog message
//               final EditText userInput = (EditText) promptsView.findViewById(R.id.promptnumber);
//
//               alertDialogBuilder
//                       .setPositiveButton("send",
//                             new DialogInterface.OnClickListener(){
//                                 public void onClick(DialogInterface dialog, int id) {
//                                     //get user input and set it to results
//                                     //edit text
//                                     Toast toast = Toast.makeText(getApplicationContext(),
//                                             userInput.getText(), Toast.LENGTH_SHORT);
//                                     toast.show();
//                                     //textSender(userInput.getText().toString());
//                                     startActivity(new Intent(main.this, HelloMonkeyActivity.class));
//                                 };
//                             })
//                       .create()
//                       .show();
//
//
//            };
        });
    }

//
//    public void textSender(String phoneNumber){
//        try {
//            SmsSender.send(phoneNumber);
//        } catch (TwilioRestException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//    public void scanQR(View v){
//        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
//        scanIntegrator.initiateScan();
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            Toast toast = Toast.makeText(getApplicationContext(),
                    scanContent, Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    View.OnLongClickListener longListen = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v) {
            DragShadow dragShadow = new DragShadow(v);
            ClipData data = ClipData.newPlainText("","");
            v.startDrag(data,dragShadow, v, 0);
            return false;
        }
    };


    private class DragShadow extends View.DragShadowBuilder {
        ColorDrawable greybox;

        public DragShadow(View view) {

            super(view);
            greybox = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            View v = getView();
            int height = (int) v.getHeight();
            int width = (int) v.getWidth();

            greybox.setBounds(0, 0, width, height);
            shadowSize.set(width, height);
            shadowTouchPoint.set((int) width / 2, (int) height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            greybox.draw(canvas);
        }
    }
//    View.OnTouchListener OnTouch = new View.OnTouchListener() {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//                view.startDrag(null, shadowBuilder, view, 0);
//                view.setVisibility(View.INVISIBLE);
//                return true;
//            } else {
//                return false;
//            }
//
//        }
//    };
private PointF touchDown;

    View.OnTouchListener OnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    touchDown = new PointF(event.getRawX(), event.getRawY());
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(null, shadowBuilder, v, 0);
                    v.setVisibility(View.INVISIBLE);
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();

                    int gridCellSize = 10;
                    float yDeff = ((event.getRawY() - touchDown.y)   / gridCellSize) * gridCellSize;
                    float xDeff = ((event.getRawX() - touchDown.x)  / gridCellSize) * gridCellSize;

                    if(Math.abs(xDeff) >= gridCellSize)
                    {
                        par.leftMargin += (int)(xDeff / gridCellSize) * gridCellSize;
                        touchDown.x = event.getRawX() - (xDeff % gridCellSize);
                    }

                    if(Math.abs(yDeff) >= gridCellSize)
                    {
                        par.topMargin += (int)(yDeff / gridCellSize) * gridCellSize;
                        touchDown.y = event.getRawY() - (yDeff % gridCellSize);
                    }

                    v.setLayoutParams(par);
                    break;
                }
                default :
                {

                    break;
                }
            }


            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener(){
    @Override
    public boolean onDrag(View layoutview, DragEvent dragevent) {

        int action = dragevent.getAction();
        View view = (View) dragevent.getLocalState();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
//            case DragEvent.ACTION_DROP:
//                ViewGroup owner = (ViewGroup) view.getParent();
//                owner.removeView(view);
//                LinearLayout container = (LinearLayout) layoutview;
//                container.addView(view);
//                view.setVisibility(View.VISIBLE);
//                if(container.getId()==R.id.buttonPlanet){
//                    view.setOnTouchListener(null);
//                    owner.setOnDragListener(null);
//                }
//                break;
            case DragEvent.ACTION_DROP:
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                LinearLayout container = (LinearLayout) layoutview;
                container.addView(view);
                view.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                System.out.println("test");
                if (dropEventNotHandled(dragevent)){
                    view.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
        return true;
    }
    private boolean dropEventNotHandled(DragEvent dragEvent) {
        return !dragEvent.getResult();
    }
};
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
