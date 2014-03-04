package my.webframework.share;

import java.io.File;

/**
 * 文件操作工具类.
 * User: 陈瑞军
 */
public class FileUtil {

    //参数说明---------path:要删除的文件的文件夹的路径---------header:要匹配的文件的头
    public static boolean delFilesByNameStartWith(String path, String prefix) {
        boolean b = false;
        File file = new File(path);
        File[] tempFile = file.listFiles();
        for (int i = 0; i < tempFile.length; i++) {
            if (tempFile[i].getName().startsWith(prefix)) {
                tempFile[i].delete();
                b = true;
            }
        }
        return b;
    }
}
