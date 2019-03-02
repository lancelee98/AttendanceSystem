package cn.stylefeng.guns.util;

import cn.stylefeng.guns.facesupport.facerecognition.AFR_FSDK_FACEMODEL;
import cn.stylefeng.guns.facesupport.generalsupport.FaceInfo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.*;

import static org.junit.Assert.*;
import static org.opencv.imgproc.Imgproc.equalizeHist;

public class FaceToolTest {
    @BeforeClass
    public static void setUpBeforeClass()  throws Exception {
        long result= FaceTool.init();
    }

    @AfterClass
    public static void tearDown() throws Exception {

    }
    @Test
    public void doTest()
    {

        FaceInfo[] f= FaceTool.doFaceDetection("D:\\images\\imageroot\\reference.jpg");
    }

    @Test
    public void doFaceDetection() {

        List<String> files= PathTool.getAllFile("C:\\DetectFaces",false);
        int numberOfUndected=0;
        int numberofUnExtracted=0;
        int numberofMultiFace=0;
        int numberofExtracted=0;
        for(int i=0;i<files.size();i++){
            FaceInfo[] f= FaceTool.doFaceDetection(files.get(i));
            if(f.length>1){
                numberofMultiFace++;
                System.out.println("多张面孔: "+files.get(i));
            }
            if(f.length==0){
                System.out.println("未检测到面孔:"+ files.get(i));
                numberOfUndected++;
            }
            if(f.length==1){
                AFR_FSDK_FACEMODEL af= FaceTool.extractFRFeature(files.get(i),f[0]);
                if(af==null){
                    numberofUnExtracted++;
                    System.out.println("未提取面孔: "+files.get(i));
                }else{
                    numberofExtracted++;
                }
            }
        }
        System.out.println("未检测到的面孔"+numberOfUndected);
        System.out.println("多张面孔："+numberofMultiFace);
        System.out.println("未提取面孔："+numberofUnExtracted);
        System.out.println("已提取面孔："+numberofExtracted);
    }

    @Test
    public void extractFRFeature() {

    }

    @Test
    public void TestRandom() {
        Random rand=new Random();
        for(int i=0;i<10;i++){
            System.out.println(rand.nextInt(21) + 20);
        }
    }


    @Test
    public void compareSimilarity() {
        String face1="C:\\test1.jpg";
        String face2="C:\\test2.jpg";
        AFR_FSDK_FACEMODEL faceFeature1=FaceTool.extractFRFeature(face1,FaceTool.doFaceDetection(face1)[0]);
        AFR_FSDK_FACEMODEL faceFeature2=FaceTool.extractFRFeature(face2,FaceTool.doFaceDetection(face2)[0]);
        float similartiy=FaceTool.compareFaceSimilarity(faceFeature1,faceFeature2);
        System.out.println("similartiy: "+similartiy);
    }
    // @Test
    public void compareFaceSimilarity() {
        String referenceFace="C:\\Users\\songm\\Desktop\\test\\facelist\\compare\\reference3.jpg";
        List<String> files= PathTool.getAllFile("C:\\Users\\songm\\Desktop\\test\\facelist",false);

        for (int j = 0; j < files.size(); j++) {
            float similartiy = FaceTool.compareFaceSimilarity(referenceFace, files.get(j));
            System.out.println(files.get(j) + ": " + similartiy+": "+referenceFace);
        }

    }

    @Test
    public void SortSimilarity() {
        List<String> files= PathTool.getAllFile("C:\\Users\\songm\\Desktop\\faceList\\陈素卿",false);
        SortedMap<Double,String> sortedMap = new TreeMap<Double,String>();
        for(int i=0;i<files.size();i++) {
            String reference=files.get(i);
            double sum=0;
            for (int j = 0; j < files.size(); j++) {
                float similartiy = FaceTool.compareFaceSimilarity(reference, files.get(j));
                sum+=similartiy;
            }
            Double average = sum/files.size();
            sortedMap.put(average,reference);
        }
        Set s = sortedMap.entrySet();
        Iterator i = s.iterator();

        while (i.hasNext())
        {
            Map.Entry m = (Map.Entry)i.next();

            double key = (Double)m.getKey();
            String value = (String)m.getValue();

            System.out.println("Key : " + key +   "  value : " + value);
        }

    }
    @Test
    public void   faceDetection()
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//加载opencv_java310.dll库
        System.out.println("\nRunning FaceDetector");
        CascadeClassifier faceDetector = new CascadeClassifier();//新建分类器
        faceDetector.load(
                "C:\\OpenCV\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");//加载人脸识别分类器haarcascade_frontalface_alt.xml

        Mat image = Imgcodecs.imread("C:\\timg.jpg");//需要识别的照片

        Mat face_gray=new Mat(image.rows(),image.cols(), CvType.CV_8UC1);//新建灰度图
        Imgproc.cvtColor( image, face_gray, Imgproc.COLOR_BGR2GRAY );//将原图转化为灰度图 提高识别效率
        equalizeHist( face_gray, face_gray );   //直方图均衡化，增强对比度

//        String filename = "C:\\gray2.jpg";
//        Imgcodecs.imwrite(filename, face_gray);

        MatOfRect faceDetections = new MatOfRect();//存放人脸位置信息
        faceDetector.detectMultiScale(face_gray, faceDetections,1.04, 1);//进行人脸检测
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        int count=0;
        for (Rect rect : faceDetections.toArray())
        {
            count++;
//            Imgproc.circle(image,new Point(rect.x, rect.y),10,new Scalar(0, 255, 255));
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));//画出人脸位置
//            System.out.println("x:"+rect.x + "  width:"+rect.width+"  y:"+rect.y + "  height:"+rect.height);

            Mat personMat=new Mat(image,rect);//切割单个人脸
            String personFileName = "C:\\DetectFaces\\person"+count+".jpg";
            Imgcodecs.imwrite(personFileName, personMat);//将单个人脸存储
        }

        String filename = "C:\\ouput.jpg";
        Imgcodecs.imwrite(filename, image);//存储标记过人脸的图片
    }
}