package com.example.remotecontrollertutorial;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.remotecontrollertutorial.models.Joystick;

public class MainActivity extends AppCompatActivity implements InputContract.View{
    private Switch switchEnable;
    private EditText editTextIpAddress;
    private RelativeLayout jostickView;
    private Joystick joystick;
    private InputContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        initializeListeners();
    }

    private void initialize(){
        presenter = new InputPresenter(this);

        switchEnable = findViewById(R.id.switchEnable);
        editTextIpAddress = findViewById(R.id.editTextIpAddress);
        jostickView = findViewById(R.id.joystick);

        joystick = new Joystick(this, jostickView, R.drawable.image_button);
        joystick.setStickSize(120, 120);
        joystick.setLayoutAlpha(150);
        joystick.setStickAlpha(100);
        joystick.setOffset(90);
        joystick.setMinimumDistance(50);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeListeners(){
        switchEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onInputControllerEnableChange(switchEnable.isChecked());
            }
        });
        jostickView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                joystick.drawStick(event);

                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    presenter.onJoystickTouch(joystick.get8Direction());
                }else {
                    presenter.onJoystickLeave();
                }
                return true;
            }
        });
    }

    @Override
    public String getIpAddressValue() {
        return String.valueOf(editTextIpAddress.getText());
    }

    @Override
    public void setIpAddressFieldEnable(boolean enable) {
        editTextIpAddress.setEnabled(enable);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSwitchControllerEnable(boolean enable) {
        switchEnable.setChecked(enable);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
