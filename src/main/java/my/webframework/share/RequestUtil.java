package my.webframework.share;

/**
 * Controller接收请求的工具类。
 */
public class RequestUtil {
    /**
     * 将传入的以逗号分开的int值字符串，转为数组，通常为ID
     *
     * @param idsStr 以逗号分开的int值字符串
     * @return int值数组
     */
    public static int[] convertStrToIntArray(String idsStr) {
        String[] temp = idsStr.split(",");
        int[] ids = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            ids[i] = Integer.parseInt(temp[i]);
        }
        return ids;
    }
}
