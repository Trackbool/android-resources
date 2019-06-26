package com.example.remotecontrollertutorial;

public interface InputContract {
    interface View{
        String getIpAddressValue();
        void setIpAddressFieldEnable(boolean enable);
        void showMessage(String message);
        void setSwitchControllerEnable(boolean enable);
    }
    interface Presenter{
        void onDestroy();
        void onInputControllerEnableChange(boolean checked);
        void onJoystickTouch(int direction);
        void onJoystickLeave();
    }
}
