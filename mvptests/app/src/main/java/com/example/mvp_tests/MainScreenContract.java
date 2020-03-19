package com.example.mvp_tests;

import java.util.List;

public interface MainScreenContract {
    interface View{
        void showClients(List<String> clients);
        void showTitle();
        void hideTitle();
        boolean isTitleVisible();
    }
    interface Presenter{
        void listClients();
        void onHideTitleButtonPressed();
        void onViewDestroy();
        void onViewCreated();
    }
    interface Model{
        List<String> getClients();
    }
}
