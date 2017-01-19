package net.macdidi.myandroidtutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import net.macdidi.myandroidtutorial.model.MenuItems;
import net.macdidi.myandroidtutorial.service.MainService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView item_list;
    private TextView show_app_name;

    // 刪除原來的宣言
//    private ArrayList<String> data = new ArrayList<>();
//    private ArrayAdapter<String> adapter;

    // ListView 使用的自訂 Adapter 物件
    private ItemAdapter itemAdapter;
    // 儲存所有記事本的 List 物件
    private List<Item> items;

    // 選單項目物件
    private MenuItems menuItems = new MenuItems();

    private MainService mainService = new MainService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 設定畫面配置資源
        // 指定的參數在「R.layout.」後面是檔案名稱
        setContentView(R.layout.activity_main);
        processViews();
        items = mainService.initDAO(getApplicationContext());

        // 建立自訂 Adapter 物件
        itemAdapter = new ItemAdapter(this, R.layout.single_item, items, new ItemAdapter.CheckClickListener() {
            @Override
            public void onCheckClick(int position) {
                System.out.println("position: " + position);
            }
        });
        item_list.setAdapter(itemAdapter);

        processControllers();
    }

    private void processViews() {
        item_list = (ListView)findViewById(R.id.item_list);
        show_app_name = (TextView) findViewById(R.id.show_app_name);
    }

    private void processControllers() {
        // 建立 & 註冊 選單項目點擊監聽物件
//        AdapterView.OnItemClickListener itemClickListener = mainService.itemListClickListener(itemAdapter, menuItems, this);
//        item_list.setOnItemClickListener(itemClickListener);
//        item_list.getSelectedItem();
        // 建立 & 註冊 選單項目長按監聽物件
//        AdapterView.OnItemLongClickListener itemLongListener = mainService.itemListLongClickListener(itemAdapter, menuItems);
//        item_list.setOnItemLongClickListener(itemLongListener);
        // 建立 & 註冊 點擊監聽物件
        View.OnClickListener clickListener = mainService.appNameClickListener(MainActivity.this);
        show_app_name.setOnClickListener(clickListener);
        // 建立 & 註冊 長按監聽物件
        View.OnLongClickListener longListener = mainService.appNameLongClickListener(MainActivity.this);
        show_app_name.setOnLongClickListener(longListener);
        // 建立記事項目 點擊監聽物件
        AdapterView.OnItemClickListener itemListener = mainService.itemClickListener(itemAdapter, menuItems);
        item_list.setOnItemClickListener(itemListener);
    }

    // 載入選單資源
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        // 取得選單項目物件
//        HashMap<String, MenuItem> menuItemHM = new HashMap();
//        menuItemHM.put("add_item", menu.findItem(R.id.add_item));
//        menuItemHM.put("search_item", menu.findItem(R.id.search_item));
//        menuItemHM.put("revert_item", menu.findItem(R.id.revert_item));
//        menuItemHM.put("delete_item", menu.findItem(R.id.delete_item));
//
//        menuItems.setMenuItemHM(menuItemHM);
        mainService.setMainMenu(menu, menuItems);

        // 設定選單項目
        mainService.processMenu(menuItems, null);

        return true;
    }

//    public void checkout(View view) {
////        Intent intent = new Intent(this, AboutActivity.class);
////        startActivity(intent);
//        RelativeLayout rl = (RelativeLayout)view;
////        Item item = (Item)rl.getParent();
//        ImageView img = (ImageView)rl.findViewById(R.id.selected_item);
//        if (View.VISIBLE == img.getVisibility()) {
//            img.setVisibility(View.INVISIBLE);
//            menuItems.minusSelectedCount(1);
//        } else {
//            img.setVisibility(View.VISIBLE);
//            menuItems.plusSelectedCount(1);
//        }
//        mainService.processMenu(menuItems, null);
//    }

    // 方法名稱與onClick的設定一樣，參數的型態是android.view.View
    public void aboutApp(View view) {
        // 顯示訊息框，指定三個參數
        // Context：通常指定為「this」
        // String或int：設定顯示在訊息框裡面的訊息或文字資源
        // int：設定訊息框停留在畫面的時間
//        Toast.makeText(this, R.string.app_name, Toast.LENGTH_LONG).show();

        // 建立啟動另一個Activity元件需要的Intent物件
        // 建構式的第一個參數：「this」
        // 建構式的第二個參數：「Activity元件類別名稱.class」
        Intent intent = new Intent(this, AboutActivity.class);
        // 呼叫「startActivity」，參數為一個建立好的Intent物件
        // 這行敘述執行以後，如果沒有任何錯誤，就會啟動指定的元件
        startActivity(intent);
    }

    // 使用者選擇所有的選單項目都會呼叫這個方法
    public void clickMenuItem(MenuItem menuItem) {
        // 使用參數取得使用者選擇的選單項目元件編號
        int itemId = menuItem.getItemId();

        switch (itemId) {
            case R.id.search_item:
                break;
            case R.id.add_item:
                mainService.addOfMenuItem(this);
                break;
            case R.id.revert_item:
                mainService.revertOfMenuItem(itemAdapter, menuItems);
                break;
            case R.id.delete_item:
//                String message = getString(R.string.delete_item);
                // 沒有選擇
                if (menuItems.getSelectedCount() == 0) {
                    break;
                }
                mainService.deleteOfMenuItem(menuItems, getString(R.string.delete_item), itemAdapter, this);
                break;
//            case R.id.googleplus_item:
//                break;
//            case R.id.facebook_item:
//                break;
        }

        // 測試用的程式碼，完成測試後記得移除
//        AlertDialog.Builder dialog =
//                new AlertDialog.Builder(MainActivity.this);
//        dialog.setTitle("MenuItem Test")
//                .setMessage(item.getTitle())
//                .setIcon(item.getIcon())
//                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mainService.processResult(requestCode, resultCode, data, items, itemAdapter);
        // 如果被啟動的Activity元件傳回確定的結果
        if (resultCode == Activity.RESULT_OK) {
            // 讀取記事物件
            Item item = (Item) data.getExtras().getSerializable(
                    "net.macdidi.myandroidtutorial.Item");
//            String titleText = data.getStringExtra("titleText");

            // 如果是新增記事
            if (requestCode == 0) {
                mainService.insertNote(itemAdapter, items, item);
            }
            // 如果是修改記事
            else if (requestCode == 1) {
                mainService.updateNote(data, itemAdapter, items, item);
            }
        }
    }

    // 設定
    public void clickPreferences(MenuItem item) {
        // 啟動設定元件
        startActivity(new Intent(this, PrefActivity.class));
    }
}
