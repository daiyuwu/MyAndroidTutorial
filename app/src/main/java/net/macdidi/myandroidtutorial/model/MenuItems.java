package net.macdidi.myandroidtutorial.model;

import android.view.MenuItem;

import java.util.HashMap;

/**
 * Created by TY on 2016/9/26.
 */
public class MenuItems {
    private int selectedCount;

    private HashMap<String, MenuItem> menuItemHM = new HashMap();

    public MenuItems(){}

    public void plusSelectedCount(int num) {
        selectedCount += num;
    }

    public void minusSelectedCount(int num) {
        selectedCount -= num;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public HashMap<String, MenuItem> getMenuItemHM() {
        return menuItemHM;
    }

    public void setMenuItemHM(HashMap<String, MenuItem> menuItemHM) {
        this.menuItemHM = menuItemHM;
    }
}
