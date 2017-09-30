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
     * 品牌
     * www.newexpress.com/v1/common/data/getBranch
     *
     * @return
     */
    public String getBrand() {
        return getBaseUrl() + "common/data/getBranch";
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

    /**
     * 企业查询
     * www.newexpress.com/v1/governor/company/search
     *
     * @return
     */
    public String query_com() {
        return getBaseUrl() + "governor/company/search";
    }

    /**
     * www.newexpress.com/v1/governor/company/:id
     *
     * @return
     */
    public String detail_com() {
        return getBaseUrl() + "governor/company/";
    }

    /**
     * www.newexpress.com/v1/governor/staff/search
     * 人员查询
     *
     * @return
     */
    public String query_per() {
        return getBaseUrl() + "governor/staff/search";
    }

    /**
     * www.newexpress.com/v1/governor/staff/:id
     *
     * @return
     */
    public String detail_per() {
        return getBaseUrl() + "governor/staff/";
    }
}
