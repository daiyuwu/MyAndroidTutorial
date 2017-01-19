package net.macdidi.myandroidtutorial.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import net.macdidi.myandroidtutorial.Item;
import net.macdidi.myandroidtutorial.ItemAdapter;
import net.macdidi.myandroidtutorial.ItemDAO;
import net.macdidi.myandroidtutorial.MainActivity;
import net.macdidi.myandroidtutorial.R;
import net.macdidi.myandroidtutorial.model.MenuItems;

import java.util.HashMap;
import java.util.List;

/**
 * Created by TY on 2016/9/26.
 */
public class MainService {

    private ItemDAO itemDAO;

    /*
            Listeners
     */

    public void itemClickListener() {
        // how ?
    }

//    public AdapterView.OnItemClickListener itemClickListener(final ItemAdapter itemAdapter, final MenuItems menuItems, final MainActivity mainActivity) {
//        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
//            // 第一個參數是使用者操作的ListView物件
//            // 第二個參數是使用者選擇的項目
//            // 第三個參數是使用者選擇的項目編號，第一個是0
//            // 第四個參數在這裡沒有用途
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
////                Toast.makeText(MainActivity.this,
////                        data.get(position), Toast.LENGTH_LONG).show();
//
//                // 讀取選擇的記事物件
//                Item item = itemAdapter.getItem(position);
//
//                // 如果已經有勾選的項目
//                if (menuItems.getSelectedCount() > 0) {
//                    // 處理是否顯示已選擇項目
//                    processMenu(menuItems, item);
//                    // 重新設定記事項目
//                    itemAdapter.set(position, item);
//                }
//                else {
//                    // 使用Action名稱建立啟動另一個Activity元件需要的Intent物件
//                    Intent intent = new Intent("net.macdidi.myandroidtutorial.EDIT_ITEM");
//
//                    // 設定記事編號與標題
//                    intent.putExtra("position", position);
//                    intent.putExtra("net.macdidi.myandroidtutorial.Item", item);
//
//                    // 呼叫「startActivityForResult」，第二個參數「1」表示執行修改
//                    mainActivity.startActivityForResult(intent, 1);
//                }
//            }
//        };
//
//        return itemClickListener;
//    }

    public AdapterView.OnItemClickListener itemClickListener(final ItemAdapter itemAdapter, final MenuItems menuItems) {
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            // 第一個參數是使用者操作的ListView物件
            // 第二個參數是使用者選擇的項目
            // 第三個參數是使用者選擇的項目編號，第一個是0
            // 第四個參數在這裡沒有用途
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                           int position, long id) {
//                Toast.makeText(MainActivity.this,
//                        "Long: " + data.get(position), Toast.LENGTH_LONG).show();
//                return false;

                // 讀取選擇的記事物件
                Item item = itemAdapter.getItem(position);
                // 處理是否顯示已選擇項目
                processMenu(menuItems, item);
                // 重新設定記事項目
                itemAdapter.set(position, item);
            }
        };

        return itemListener;
    }

    public View.OnClickListener appNameClickListener(final Context context) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder d =
                        new AlertDialog.Builder(context);
                d.setTitle(R.string.app_name)
                        .setMessage(R.string.about)
                        .show();
            }
        };

        return clickListener;
    }

    public View.OnLongClickListener appNameLongClickListener(final Context context) {
        View.OnLongClickListener longListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog =
                        new AlertDialog.Builder(context);
                dialog.setTitle(R.string.app_name)
                        .setMessage(R.string.about)
                        .show();
                return false;
            }
        };

        return longListener;
    }

    /*
            Other
     */

    public void setMainMenu(Menu menu, MenuItems menuItems) {
        // 取得選單項目物件
        HashMap<String, MenuItem> menuItemHM = new HashMap();
        menuItemHM.put("add_item", menu.findItem(R.id.add_item));
        menuItemHM.put("search_item", menu.findItem(R.id.search_item));
        menuItemHM.put("revert_item", menu.findItem(R.id.revert_item));
        menuItemHM.put("delete_item", menu.findItem(R.id.delete_item));

        menuItems.setMenuItemHM(menuItemHM);
    }

    // 處理是否顯示已選擇項目
    public void processMenu(MenuItems menuItems, Item item) {

        // 如果需要設定記事項目
        if (item != null) {
            // 設定已勾選的狀態
            item.setSelected(!item.isSelected());

            // 計算已勾選數量
            if (item.isSelected()) {
                menuItems.plusSelectedCount( 1 );
            }
            else {
                menuItems.minusSelectedCount( 1 );
            }
        }

        int selectedCount = menuItems.getSelectedCount();

        System.out.println("selectedCount: " + selectedCount);

        // 根據選擇的狀況，設定是否顯示選單項目
        HashMap<String, MenuItem> menuItemHM = menuItems.getMenuItemHM();
        menuItemHM.get("add_item").setVisible(selectedCount == 0);
        menuItemHM.get("search_item").setVisible(selectedCount == 0);
        menuItemHM.get("revert_item").setVisible(selectedCount > 0);
        menuItemHM.get("delete_item").setVisible(selectedCount > 0);
    }

    public void insertNote(ItemAdapter itemAdapter, List<Item> items, Item item) {
        // 設定記事物件的編號與日期時間
//                item.setId(items.size() + 1);
//                item.setDatetime(new Date().getTime());
        item = itemDAO.insert(item);

        // 加入新增的記事物件
        items.add(item);
//                this.data.add(titleText);

        // 通知資料改變
        itemAdapter.notifyDataSetChanged();
    }

    public void updateNote(Intent data, ItemAdapter itemAdapter, List<Item> items, Item item) {
        // 讀取記事編號
        int position = data.getIntExtra("position", -1);

        if (position != -1) {
            itemDAO.update(item);

            // 設定修改的記事物件
            items.set(position, item);
            itemAdapter.notifyDataSetChanged();
        }
    }

    public void addOfMenuItem(MainActivity mainActivity) {
        // 建立啟動另一個Activity元件需要的Intent物件
//                Intent intent = new Intent(this, ItemActivity.class);
        // 使用Action名稱建立啟動另一個Activity元件需要的Intent物件
        Intent intent = new Intent("net.macdidi.myandroidtutorial.ADD_ITEM");
        // 呼叫「startActivityForResult」，第二個參數「0」目前沒有使用
        mainActivity.startActivityForResult(intent, 0);
    }

    public void revertOfMenuItem(ItemAdapter itemAdapter, MenuItems menuItems) {
        for (int i = 0; i < itemAdapter.getCount(); i++) {
            Item ri = itemAdapter.getItem(i);

            if (ri.isSelected()) {
                ri.setSelected(false);
                itemAdapter.set(i, ri);
            }
        }

        menuItems.setSelectedCount(0);
        processMenu(menuItems, null);
    }

    public void deleteOfMenuItem(MenuItems menuItems, String message, final ItemAdapter itemAdapter, MainActivity mainActivity) {
        // 建立與顯示詢問是否刪除的對話框
        AlertDialog.Builder d = new AlertDialog.Builder(mainActivity);
        d.setTitle(R.string.delete)
                .setMessage(String.format(message, menuItems.getSelectedCount()));
        d.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 取得最後一個元素的編號
                        int index = itemAdapter.getCount() - 1;

                        while (index > -1) {
                            Item item = itemAdapter.get(index);

                            if (item.isSelected()) {
//                                  itemDAO.delete(item.getId());
                                delete(item.getId());
                                itemAdapter.remove(item);
                            }

                            index--;
                        }

                        // 通知資料改變
                        itemAdapter.notifyDataSetChanged();
//                                selectedCount = 0;
//                                processMenu(null);
                    }
                });
        d.setNegativeButton(android.R.string.no, null);
        d.show();
    }

    public List<Item> initDAO(Context context) {
        itemDAO = new ItemDAO(context);

        if(itemDAO.getCount() == 0) {
            itemDAO.sample();
        }

        return itemDAO.getAll();
    }

    public void delete(long itemId) {
        itemDAO.delete(itemId);
    }
}
