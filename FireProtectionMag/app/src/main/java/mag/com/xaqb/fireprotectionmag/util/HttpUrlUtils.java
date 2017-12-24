package mag.com.xaqb.fireprotectionmag.util;

/**
 * 接口地址
 */
public class HttpUrlUtils {
    private static HttpUrlUtils httpUrl = new HttpUrlUtils();

    public static HttpUrlUtils getHttpUrl() {
        return httpUrl;
    }

    private String getBaseUrl() {
        return "http://192.168.0.112:5200/v1/";
    }

    /**
     * 登录
     * www.firecontrol.com/v1/mfirestation/index/login
     * @return
     */
    public String userLogin() {
        return getBaseUrl() + "mfirestation/index/login";
    }

    /**
     * 退出
     * www.firecontrol.com/v1/mfirestation/index/logout
     * @return
     */
    public String userLogout(){
        return getBaseUrl() + "mfirestation/index/logout";
    }




    /**
     * 管辖机构
     * www.newexpress.com/v1/common/data/securityorg
     *
     * @return
     */
    public String getOrg() {
        return getBaseUrl() + "common/data/securityorg";
    }



}
