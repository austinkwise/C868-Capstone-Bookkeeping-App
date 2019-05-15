package main;

import main.Model.User;

public class ReportsController {
    private User currentUser;

    public void setupReports(User currentUser) {
        this.currentUser = currentUser;
    }
}
