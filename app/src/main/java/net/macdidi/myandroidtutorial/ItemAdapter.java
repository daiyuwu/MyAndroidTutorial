package net.macdidi.myandroidtutorial;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.macdidi.myandroidtutorial.service.MainService;

import java.util.List;

/**
 * Created by TY on 2016/9/5.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    // 畫面資源編號
    private int resource;
    // 包裝的記事資料
    private List<Item> items;
    //
    MainService mainService;
    //
    CheckClickListener checkClickListener = null;

    public ItemAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    public ItemAdapter(Context context, int resource, List<Item> items, CheckClickListener listener) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
        this.checkClickListener = listener;
    }

    public interface CheckClickListener {
        public abstract void onCheckClick(int position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final Item item = getItem(position);

        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());

            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)
                    getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        // 讀取記事顏色、已選擇、標題與日期時間元件
        RelativeLayout typeColor = (RelativeLayout) itemView.findViewById(R.id.type_color);
        ImageView selectedItem = (ImageView) itemView.findViewById(R.id.selected_item);
        TextView titleView = (TextView) itemView.findViewById(R.id.title_text);
        TextView dateView = (TextView) itemView.findViewById(R.id.date_text);

//        final ItemAdapter t = this;

        // test listener
        typeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(null != checkClickListener)
//                    checkClickListener.onCheckClick(position);
            }
        });
//        typeColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("61");
////                RelativeLayout rl = (RelativeLayout)view;
////                selectedItem.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);
//                // 讀取選擇的記事物件
//                Item item = t.getItem(position);
//                // 處理是否顯示已選擇項目
//                mainService.processMenu(menuItems, item);
//                // 重新設定記事項目
////                itemAdapter.set(position, item);
//
//                System.out.println("63");
////                ImageView selectedItem = (ImageView) itemView.findViewById(R.id.selected_item);
////                AlertDialog.Builder d =
////                        new AlertDialog.Builder(MainActivity.class);
////                d.setTitle(R.string.app_name)
////                        .setMessage(R.string.about)
////                        .show();
//            }
//        });

        // 設定記事顏色
        GradientDrawable background = (GradientDrawable)typeColor.getBackground();
        background.setColor(item.getColor().parseColor());

        // 設定標題與日期時間
        titleView.setText(item.getTitle());
        dateView.setText(item.getLocaleDatetime());

        // 設定是否已選擇
        selectedItem.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);

        return itemView;
    }

    // 設定指定編號的記事資料
    public void set(int index, Item item) {
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
            notifyDataSetChanged();
        }
    }

    // 讀取指定編號的記事資料
    public Item get(int index) {
        return items.get(index);
    }

}
