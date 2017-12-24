package unlock.xaqb.com.dxfsdf;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }else if (Build.VERSION.SDK_INT >= 21){
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);//设置底下导航栏的背景色，当前设置透明色
//            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置顶部状态栏颜色，当前设置透明色
        }

        setupViews();
    }


    private FrameLayout mContentLayout;
    private LinearLayout llRoot;
    private FrameLayout layout_titlebar;
    private void setupViews() {
        super.setContentView(R.layout.ac_title);
        llRoot = (LinearLayout) findViewById(R.id.llRoot);
        layout_titlebar = (FrameLayout) findViewById(R.id.layout_titlebar);
        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
    }


    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }

    /* (non-Javadoc)
* @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
*/
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }


}
