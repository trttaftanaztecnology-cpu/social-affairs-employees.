package com.socialaffairs.chat;

import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    LinearLayout root, content;
    final int GOLD = Color.rgb(145,115,38);
    final int CREAM = Color.rgb(250,247,239);
    final int DARK = Color.rgb(62,53,35);

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        showLogin();
    }

    TextView title(String s, int size) {
        TextView t = new TextView(this);
        t.setText(s); t.setTextSize(size); t.setTextColor(DARK);
        t.setGravity(Gravity.CENTER); t.setPadding(12,12,12,12);
        return t;
    }

    Button btn(String s) {
        Button b = new Button(this);
        b.setText(s); b.setTextSize(16); b.setAllCaps(false);
        b.setTextColor(Color.WHITE); b.setBackgroundColor(GOLD);
        b.setPadding(12,8,12,8);
        return b;
    }

    void base() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(CREAM);
        root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(root);
    }

    void showLogin() {
        base();
        Space sp = new Space(this);
        root.addView(sp, new LinearLayout.LayoutParams(1, 55));
        ImageView logo = new ImageView(this);
        logo.setImageResource(com.socialaffairs.chat.R.drawable.logo);
        logo.setAdjustViewBounds(true);
        root.addView(logo, new LinearLayout.LayoutParams(-1, 180));
root.addView(title("مديرية الشؤون الاجتماعية\nوالعمل في محافظة إدلب", 20));        root.addView(title("موظفو مبنى مديرية الشؤون الاجتماعية", 24));
        EditText name = new EditText(this);
        name.setHint("أدخل اسم الموظف");
        name.setTextSize(17); name.setGravity(Gravity.RIGHT);
        name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        root.addView(name, new LinearLayout.LayoutParams(-1, 65));
        Button enter = btn("دخول");
        root.addView(enter, new LinearLayout.LayoutParams(-1, 65));
        enter.setOnClickListener(v -> showHome(name.getText().toString().trim()));
        root.addView(title("معاً ... لبيئة عمل أفضل", 14));
    }

    void header(String s) {
        TextView h = new TextView(this);
        h.setText(s); h.setTextSize(22); h.setTextColor(Color.WHITE);
        h.setGravity(Gravity.CENTER); h.setPadding(10,18,10,18);
        h.setBackgroundColor(GOLD);
        root.addView(h, new LinearLayout.LayoutParams(-1, 75));
    }

    void showHome(String user) {
        base(); header("موظفو مبنى مديرية الشؤون الاجتماعية");
        content = new LinearLayout(this); content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(16,16,16,10); root.addView(content, new LinearLayout.LayoutParams(-1,0,1));
        TextView welcome = title("مرحباً " + (user.isEmpty() ? "بالزميل" : user), 21);
        content.addView(welcome);
        EditText search = new EditText(this); search.setHint("ابحث عن موظف..."); search.setGravity(Gravity.RIGHT);
        content.addView(search, new LinearLayout.LayoutParams(-1,60));
        String[] people = {"أحمد محمد — قسم الشؤون — متصل","محمد علي — قسم الإدارة — متصل","ياسر عبدالله — قسم المالية — متصل","سارة أحمد — الخدمات الاجتماعية — غير متصل","خالد عبدالله — قسم المتابعة — متصل","منى حسن — قسم الإحصاء — غير متصل"};
        for(String p: people) {
            TextView row = title("●  " + p, 17);
            row.setGravity(Gravity.RIGHT); row.setPadding(15,15,15,15);
            content.addView(row);
            row.setOnClickListener(v -> showChat("أحمد محمد"));
        }
        nav(user);
    }

    void nav(String user) {
        LinearLayout n = new LinearLayout(this); n.setGravity(Gravity.CENTER); n.setOrientation(LinearLayout.HORIZONTAL);
        String[] labels = {"الرئيسية","الأقسام","المحادثات","الإعدادات"};
        for(String s: labels) {
            Button b = new Button(this); b.setText(s); b.setAllCaps(false); b.setTextSize(12);
            n.addView(b, new LinearLayout.LayoutParams(0,65,1));
            if(s.equals("الأقسام")) b.setOnClickListener(v -> showDepartments());
            if(s.equals("المحادثات")) b.setOnClickListener(v -> showChatList());
            if(s.equals("الإعدادات")) b.setOnClickListener(v -> showSettings(user));
            if(s.equals("الرئيسية")) b.setOnClickListener(v -> showHome(user));
        }
        root.addView(n);
    }

    void simple(String head, String body) {
        base(); header(head);
        TextView t = title(body, 19); t.setGravity(Gravity.RIGHT); t.setPadding(25,25,25,25);
        root.addView(t, new LinearLayout.LayoutParams(-1,0,1));
        Button back = btn("العودة للرئيسية"); root.addView(back, new LinearLayout.LayoutParams(-1,65));
        back.setOnClickListener(v -> showHome(""));
    }

    void showDepartments() {
        simple("الأقسام","الإدارة — 6 موظفين\n\nالشؤون — 7 موظفين\n\nالمالية — 4 موظفين\n\nالخدمات الاجتماعية — 6 موظفين\n\nالإحصاء — 3 موظفين\n\nالمتابعة — 4 موظفين\n\nإجمالي الموظفين: 30");
    }

    void showChatList() {
        simple("المحادثات","أحمد محمد — آخر رسالة: تمام، شكراً\n\nمحمد علي — آخر رسالة: قريباً\n\nياسر عبدالله — آخر رسالة: تم الاستلام\n\nسارة أحمد — آخر رسالة: شكراً");
    }

    void showChat(String person) {
        simple("محادثة مع " + person,"السلام عليكم\n\nوعليكم السلام، كيف حالك؟\n\nبخير، شكراً.\n\nاكتب رسالتك من خلال النسخة المتصلة بالشبكة المحلية.");
    }

    void showSettings(String user) {
        simple("الإعدادات","معلومات الموظف\n\nإعدادات التطبيق\n\nالشبكة المحلية\nمتصل داخل شبكة المديرية\n\nالإشعارات\n\nحول التطبيق\nالإصدار 1.0.0");
    }
}
