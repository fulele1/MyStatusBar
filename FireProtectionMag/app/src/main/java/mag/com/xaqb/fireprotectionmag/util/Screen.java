package mag.com.xaqb.fireprotectionmag.util;


import mag.com.xaqb.fireprotectionmag.BaseActivity;

/**
 * /屏幕的相关特性
 * */
public class Screen {
    //获取屏幕宽度
     public static int getScreenWidth(BaseActivity activity) {
         return activity.getWindowManager().getDefaultDisplay().getWidth();
     }
    //获取屏幕高度
     public static int getScreenHeight(BaseActivity activity){
        return  activity.getWindowManager().getDefaultDisplay().getHeight();
     }
}
