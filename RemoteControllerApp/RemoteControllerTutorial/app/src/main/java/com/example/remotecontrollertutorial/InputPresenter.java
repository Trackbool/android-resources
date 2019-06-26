package com.example.remotecontrollertutorial;

import android.graphics.Point;

import com.example.remotecontrollertutorial.models.InputModel;
import com.example.remotecontrollertutorial.models.StickDirection;
import com.example.remotecontrollertutorial.utils.SocketUtils;

import java.net.InetAddress;

public class InputPresenter implements InputContract.Presenter {

    private InputContract.View view;
    private InputControllerDataSender dataSender;
    private InputModel joystickData;

    public InputPresenter(InputContract.View view) {
        this.view = view;

        joystickData = new InputModel();
        dataSender = new InputControllerDataSender(6000, joystickData);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onInputControllerEnableChange(boolean checked) {
        if (checked) {
            String serverAddress = view.getIpAddressValue();
            if(!serverAddress.isEmpty()) {
                InetAddress address = SocketUtils.getAddress(serverAddress);
                if(address != null) {
                    dataSender.setAddress(address);
                    dataSender.sendData();
                    view.setIpAddressFieldEnable(false);
                }else{
                    disableController();
                    view.showMessage("Invalid IP Address");
                }
            }else{
                disableController();
                view.showMessage("IP address is empty");
            }
        } else {
            disableController();
        }
    }

    private void disableController(){
        dataSender.stopSendingData();
        view.setIpAddressFieldEnable(true);
        view.setSwitchControllerEnable(false);
    }

    @Override
    public void onJoystickTouch(int direction) {
        joystickData.setAxes(StickDirection.getDirectionAxes(direction));
    }

    @Override
    public void onJoystickLeave() {
        joystickData.setAxes(new Point(0, 0));
    }
}
