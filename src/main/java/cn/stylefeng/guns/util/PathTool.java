package cn.stylefeng.guns.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathTool {
    //从全文件名中获取文件名,Window平台适用
    public static String getFileName(String filePath){
        String[] directories=filePath.split("\\\\");
        return directories[directories.length-1];
    }

    //去除路径的前后部分，得到表示摄像头的路径部分,该部分同时也是摄像头的标识号
    public static String getCameraDirectory(String original){
        String[] directories=original.split("\\\\");
        return directories[directories.length-2];
    }

    //去除路径字符串的路径前缀，original是原始字符串，如c:\test\data\Mydata, prefix表示需要去除的前缀，比如c:\test
    public static String removePrefix(String original, String prefix ){
        String result=original;
        //去除前缀
        if(prefix !=null) {
            if(!prefix.endsWith("\\")){
                prefix=prefix+"\\";
            }
            if(original.indexOf(prefix)>=0){
                int startIndex = original.indexOf(prefix) + prefix.length();
                result = result.toString().substring(startIndex);
            }
        }
        return result;
    }

    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }
}
