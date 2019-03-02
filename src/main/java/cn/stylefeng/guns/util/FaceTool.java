package cn.stylefeng.guns.util;

import cn.stylefeng.guns.facesupport.ageestimation.ASAE_FSDKLibrary;
import cn.stylefeng.guns.facesupport.facedetection.AFD_FSDKLibrary;
import cn.stylefeng.guns.facesupport.facedetection.AFD_FSDK_FACERES;
import cn.stylefeng.guns.facesupport.facedetection._AFD_FSDK_OrientPriority;
import cn.stylefeng.guns.facesupport.facerecognition.AFR_FSDKLibrary;
import cn.stylefeng.guns.facesupport.facerecognition.AFR_FSDK_FACEINPUT;
import cn.stylefeng.guns.facesupport.facerecognition.AFR_FSDK_FACEMODEL;
import cn.stylefeng.guns.facesupport.genderestimation.ASGE_FSDKLibrary;
import cn.stylefeng.guns.facesupport.generalsupport.*;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.PointerByReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FaceTool {


    public static String APPID="HHkoh5YKkzdDz8V6N4PMsyd6P4qwwBh3ugMSHY2MT2eA";

    public static String FD_SDKKEY="4hHuBrWWSWKsCFZVhVnVEeupQuXAvgqMChFRsWCgVfTS" ;

    public static String FR_SDKKEY ="4hHuBrWWSWKsCFZVhVnVEevK4WZriC82G94obdYDBAVB";

    public static String FA_SDKKEY ="4hHuBrWWSWKsCFZVhVnVEevZPK6Dg8Bmed2djXmqoCid";

    public static String FG_SDKKEY ="4hHuBrWWSWKsCFZVhVnVEevgYiMP91c6KFDjXCtmGAzc";


    public static final int FD_WORKBUF_SIZE = 20 * 1024 * 1024;
    public static final int FR_WORKBUF_SIZE = 40 * 1024 * 1024;
    public static final int FA_WORKBUF_SIZE = 30 * 1024 * 1024;
    public static final int FG_WORKBUF_SIZE = 40 * 2014 * 1024;

    public static final int MAX_FACE_NUM = 5;

    public static final boolean bUseRAWFile = false;
    public static final boolean bUseBGRToEngine = true;

    public static Pointer pFDWorkMem = CLibrary.INSTANCE.malloc(FD_WORKBUF_SIZE);
    public static Pointer pFRWorkMem = CLibrary.INSTANCE.malloc(FR_WORKBUF_SIZE);
    public static Pointer pFAWorkMem = CLibrary.INSTANCE.malloc(FA_WORKBUF_SIZE);
    public static Pointer pFGWorkMem = CLibrary.INSTANCE.malloc(FG_WORKBUF_SIZE);

    //面孔检测, 和识别的初始化引擎
    public static PointerByReference phFDEngine = new PointerByReference();
    public static PointerByReference phFREngine = new PointerByReference();
    public static PointerByReference phFAEngine = new PointerByReference();
    public static PointerByReference phFGEngine = new PointerByReference();

    public static boolean hasINIT=false;

    //面孔识别类的初始化引擎,返回0表示正确的初始化，非0值表示初始化出错。
    public static long init() {

        Pointer hFDEngine = phFDEngine.getValue();
        NativeLong ret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_InitialFaceEngine(APPID, FD_SDKKEY, pFDWorkMem, FD_WORKBUF_SIZE, phFDEngine, _AFD_FSDK_OrientPriority.AFD_FSDK_OPF_0_HIGHER_EXT, 8, MAX_FACE_NUM);
        if (ret.longValue() != 0) {
            freeMemory();
            return ret.longValue();
        }

        Pointer hFREngine = phFREngine.getValue();
        ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_InitialEngine(APPID, FR_SDKKEY, pFRWorkMem, FR_WORKBUF_SIZE, phFREngine);
        if (ret.longValue() != 0) {
            AFD_FSDKLibrary.INSTANCE.AFD_FSDK_UninitialFaceEngine(hFREngine);
            freeMemory();
            return ret.longValue();
        }

        Pointer hFAEngine = phFAEngine.getValue();
        ret = ASAE_FSDKLibrary.INSTANCE.ASAE_FSDK_InitAgeEngine( APPID, FA_SDKKEY, pFAWorkMem,FA_WORKBUF_SIZE,phFAEngine);
        if (ret.longValue() != 0) {
            AFD_FSDKLibrary.INSTANCE.AFD_FSDK_UninitialFaceEngine(hFREngine);
            freeMemory();
            return ret.longValue();
        }

        Pointer hFGEngine = phFGEngine.getValue();
        ret = ASGE_FSDKLibrary.INSTANCE.ASGE_FSDK_InitGenderEngine(APPID, FG_SDKKEY, pFGWorkMem,FG_WORKBUF_SIZE,phFGEngine);
        if (ret.longValue() != 0) {
            AFD_FSDKLibrary.INSTANCE.AFD_FSDK_UninitialFaceEngine(hFDEngine);
            freeMemory();
            return ret.longValue();
        }
        hasINIT=true;
        return 0;
    }

    //从图片中检测面孔，其中检测到的每一个面孔位置信息被放入FaceInfo数据结构中。
    public static FaceInfo[] doFaceDetection(String filePathImg) {

        Pointer hFDEngine = phFDEngine.getValue();
        ASVLOFFSCREEN inputImg = loadImage(filePathImg);
        FaceInfo[] faceInfo = new FaceInfo[0];

        PointerByReference ppFaceRes = new PointerByReference();
        NativeLong ret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_StillImageFaceDetection(hFDEngine,inputImg, ppFaceRes);
        if (ret.longValue() != 0) {
            System.out.println(String.format("AFD_FSDK_StillImageFaceDetection ret 0x%x" , ret.longValue()));
            return faceInfo;
        }

        AFD_FSDK_FACERES faceRes = new AFD_FSDK_FACERES(ppFaceRes.getValue());
        if (faceRes.nFace > 0) {
            faceInfo = new FaceInfo[faceRes.nFace];
            for (int i = 0; i < faceRes.nFace; i++) {
                MRECT rect = new MRECT(new Pointer(Pointer.nativeValue(faceRes.rcFace.getPointer()) + faceRes.rcFace.size() * i));
                int orient = faceRes.lfaceOrient.getPointer().getInt(i * 4);
                faceInfo[i] = new FaceInfo();
                faceInfo[i].left = rect.left;
                faceInfo[i].top = rect.top;
                faceInfo[i].right = rect.right;
                faceInfo[i].bottom = rect.bottom;
                faceInfo[i].orient = orient;
//                System.out.println(String.format("%d (%d %d %d %d) orient %d", i, rect.left, rect.top, rect.right, rect.bottom, orient));
            }
        }
        return faceInfo;
    }


    /*从面孔图片中提取检测特征，形成一个AFR_FSDK_FACEMODEL的特征向量, 如果提取失败，放回null对象；在提取特征之前，应先获取面孔图片位置
    1) 从现有AFR_FSDK_FACEMODEL中获取数据
     byte[] featureInByteArray = faceFeatureA.toByteArray();
    2) 从byte[]中生成AFR_FSDK_FACEMODEL
     AFR_FSDK_FACEMODEL faceFeatureX = AFR_FSDK_FACEMODEL.fromByteArray(featureInByteArray);*/
    public static AFR_FSDK_FACEMODEL extractFRFeature(String filePath, FaceInfo faceInfo) {

        Pointer hFREngine = phFREngine.getValue();//获取人脸识别引擎 FaceRecognitionEngine
        AFR_FSDK_FACEINPUT faceinput = new AFR_FSDK_FACEINPUT();//初始化人脸位置信息
        faceinput.lOrient = faceInfo.orient;
        faceinput.rcFace.left = faceInfo.left;
        faceinput.rcFace.top = faceInfo.top;
        faceinput.rcFace.right = faceInfo.right;
        faceinput.rcFace.bottom = faceInfo.bottom;

        AFR_FSDK_FACEMODEL faceFeature = new AFR_FSDK_FACEMODEL();//分配特征向量
        ASVLOFFSCREEN inputImg = loadImage(filePath);//导入人脸照片
        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_ExtractFRFeature(hFREngine, inputImg, faceinput, faceFeature);//提取特征向量到faceFeature
        if (ret.longValue() != 0) {
            return null;
        }
        try {
            return faceFeature.deepCopy();//返回特征向量
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*通过两张图片来比较面孔相似度，如果其中任何一张没有包含面孔，返回0；如果包含面孔，但是提取特征出错，返回-1，；*/
    public static float compareFaceSimilarity(String filePathImgA, String filePathImgB) {

        Pointer hFDEngine=phFDEngine.getValue();
        Pointer hFREngine=phFREngine.getValue();
        ASVLOFFSCREEN inputImgA=loadImage(filePathImgA);
        ASVLOFFSCREEN inputImgB=loadImage(filePathImgB);

        // Do Face Detect
        FaceInfo[] faceInfosA = doFaceDetection(filePathImgA);
        if (faceInfosA.length < 1) {
            return 0.0f;
        }

        FaceInfo[] faceInfosB = doFaceDetection(filePathImgB);
        if (faceInfosB.length < 1) {
            return 0.0f;
        }

        // Extract Face Feature
        AFR_FSDK_FACEMODEL faceFeatureA = extractFRFeature(filePathImgA, faceInfosA[0]);
        if (faceFeatureA == null) {
            return -1f;
        }

        AFR_FSDK_FACEMODEL faceFeatureB = extractFRFeature(filePathImgB, faceInfosB[0]);
        if (faceFeatureB == null) {
            //faceFeatureB.freeUnmanaged();
            return -1f;
        }

        // calc similarity between faceA and faceB
        FloatByReference fSimilScore = new FloatByReference(0.0f);
        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_FacePairMatching(hFREngine, faceFeatureA, faceFeatureB, fSimilScore);
        faceFeatureA.freeUnmanaged();
        faceFeatureB.freeUnmanaged();
        if (ret.longValue() != 0) {
            System.out.println(String.format("AFR_FSDK_FacePairMatching failed:ret 0x%x" ,ret.longValue()));
            return 0.0f;
        }
        return fSimilScore.getValue();
    }



    /*通过两组面孔特征检查两张面孔图片的相似度，如果其中任何一张没有包含面孔，返回0；如果包含面孔，但是提取特征出错，返回-1，；*/
    public static float compareFaceSimilarity( AFR_FSDK_FACEMODEL modelA, AFR_FSDK_FACEMODEL modelB) {

        Pointer hFDEngine=phFDEngine.getValue();
        Pointer hFREngine=phFREngine.getValue();//初始化识别引擎

        // calc similarity between faceA and faceB
        FloatByReference fSimilScore = new FloatByReference(0.0f);//声明相似度
        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_FacePairMatching(hFREngine, modelA, modelB, fSimilScore);//比较两个特征向量的相似度

        if (ret.longValue() != 0) {
            System.out.println(String.format("AFR_FSDK_FacePairMatching failed:ret 0x%x" ,ret.longValue()));
            return 0.0f;
        }
        return fSimilScore.getValue();//返回两个特征向量的相似度
    }

     // 性别估计暂时还有问题，不能使用

//    public static int estimageGenderFromFace(String filePath, FaceInfo[] faceInfo) {
//        Pointer hFDEngine = phFDEngine.getValue();
//        Pointer hFGEngine = phFGEngine.getValue();
//
//        ASVLOFFSCREEN inputImg = loadImage(filePath);
//
//        //输入参数
//        PointerByReference ppFaceRes = new PointerByReference();
//        NativeLong ret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_StillImageFaceDetection(hFDEngine, inputImg, ppFaceRes);
//        AFD_FSDK_FACERES faceRes = new AFD_FSDK_FACERES(ppFaceRes.getValue());
//
//
//        ASGE_FSDK_GENDERFACEINPUT inputFaces = new ASGE_FSDK_GENDERFACEINPUT();
//        /*
//        inputFaces.lFaceNumber=1;
//        inputFaces.pFaceOrientArray= new IntByReference(faceRes.lfaceOrient.getValue());
//        MRECT.ByReference rect=new MRECT.ByReference();
//        rect.bottom=faceRes.rcFace.bottom;
//        rect.right=faceRes.rcFace.right;
//        rect.top=faceRes.rcFace.top;
//        rect.left=faceRes.rcFace.top;
//        rect.write();
//        inputFaces.pFaceRectArray=rect;
//        */
//        inputFaces.lFaceNumber = faceRes.nFace;
//        inputFaces.pFaceOrientArray = faceRes.lfaceOrient;
//        inputFaces.pFaceRectArray = faceRes.rcFace;
//
//
//        ASGE_FSDK_GENDERRESULT result=new ASGE_FSDK_GENDERRESULT( );
//        ret = ASGE_FSDKLibrary.INSTANCE.ASGE_FSDK_GenderEstimation_StaticImage(hFGEngine, inputImg, inputFaces, result);
//
//
//        return result.pGenderResultArray.getValue();
//    }

//    //通过输入图片，获得面孔的年龄
//    public static int estimageAgeFromFace(String filePath, FaceInfo[] faceInfo) {
//        Pointer hFDEngine = phFDEngine.getValue();
//        Pointer hFAEngine = phFAEngine.getValue();
//
//        ASVLOFFSCREEN inputImg = loadImage(filePath);
//        ASAE_FSDK_AGEFACEINPUT faceInput=new ASAE_FSDK_AGEFACEINPUT();
//
//        //输入参数
//        PointerByReference ppFaceRes=new PointerByReference();
//        NativeLong ret=AFD_FSDKLibrary.INSTANCE.AFD_FSDK_StillImageFaceDetection(hFDEngine,inputImg,ppFaceRes);
//
//        //传递到
//        AFD_FSDK_FACERES faceRes = new AFD_FSDK_FACERES(ppFaceRes.getValue());
//        ASAE_FSDK_AGEFACEINPUT inputFaces=new ASAE_FSDK_AGEFACEINPUT();
//
//
//        inputFaces.lFaceNumber= faceRes.nFace;
//        inputFaces.pFaceOrientArray= faceRes.lfaceOrient;
//        inputFaces.pFaceRectArray=faceRes.rcFace;
//
//        ASAE_FSDK_AGERESULT pAgeResult=new ASAE_FSDK_AGERESULT( );
//        ret=ASAE_FSDKLibrary.INSTANCE.ASAE_FSDK_AgeEstimation_StaticImage(hFAEngine,inputImg,inputFaces,pAgeResult);
//
//        return pAgeResult.pAgeResultArray.getValue();

        /*
        inputFaces.lFaceNumber=1;
        inputFaces.pFaceOrientArray= new IntByReference(faceRes.lfaceOrient.getValue());
        MRECT.ByReference rect=new MRECT.ByReference();
        rect.bottom=faceRes.rcFace.bottom;
        rect.right=faceRes.rcFace.right;
        rect.top=faceRes.rcFace.top;
        rect.left=faceRes.rcFace.top;
        rect.write();
        inputFaces.pFaceRectArray=rect;

        if (faceRes.nFace > 0) {

             inputFaces.lFaceNumber=faceRes.nFace;
             System.out.println("face number:"+faceRes.nFace);

            faceInfo = new FaceInfo[faceRes.nFace];
            for (int i = 0; i < faceRes.nFace; i++) {
                MRECT rect = new MRECT(new Pointer(Pointer.nativeValue(faceRes.rcFace.getPointer()) + faceRes.rcFace.size() * i));
                int orient = faceRes.lfaceOrient.getPointer().getInt(i * 4);
                faceInfo[i] = new FaceInfo();
                faceInfo[i].left = rect.left;
                faceInfo[i].top = rect.top;
                faceInfo[i].right = rect.right;
                faceInfo[i].bottom = rect.bottom;
                faceInfo[i].orient = orient;
                //System.out.println(String.format("%d (%d %d %d %d) orient %d", i, rect.left, rect.top, rect.right, rect.bottom, orient));
            }
        }
        */


        /*
        if (faceInfo.length> 0) {
            /*faceInput.lFaceNumber=faceInfo.length;
            //建立数组
            int[] faceOrientArray=new int[faceInfo.length];
            //创建连续内存的数组
            MRECT rect=new MRECT();
            MRECT[] rects=(MRECT[])rect.toArray(faceInfo.length);
            for (int i = 0; i < faceInfo.length; i++) {
                faceOrientArray[i]=faceInfo[i].orient;
                rects[i].left=faceInfo[i].left;
                rects[i].top=faceInfo[i].top;
                rects[i].right=faceInfo[i].right;
                rects[i].bottom=faceInfo[i].bottom;
                /*
                MRECT rect = new MRECT(new Pointer(Pointer.nativeValue(faceRes.rcFace.getPointer()) + faceRes.rcFace.size() * i));
                int orient = faceRes.lfaceOrient.getPointer().getInt(i * 4);
                faceInfo[i] = new FaceInfo();
                faceInfo[i].left = rect.left;
                faceInfo[i].top = rect.top;
                faceInfo[i].right = rect.right;
                faceInfo[i].bottom = rect.bottom;
                faceInfo[i].orient = orient;
                //System.out.println(String.format("%d (%d %d %d %d) orient %d", i, rect.left, rect.top, rect.right, rect.bottom, orient));

            }
            Memory orientMemory=new Memory(4*faceInfo.length);
            orientMemory.write();
            faceInput.pFaceOrientArray.setPointer(orientMemory);
            */




//    }


    //导入YUV格式的图片文件
    public static ASVLOFFSCREEN loadRAWImage(String yuv_filePath, int yuv_width, int yuv_height, int yuv_format) {
        int yuv_rawdata_size = 0;

        ASVLOFFSCREEN inputImg = new ASVLOFFSCREEN();
        inputImg.u32PixelArrayFormat = yuv_format;
        inputImg.i32Width = yuv_width;
        inputImg.i32Height = yuv_height;
        if (ASVL_COLOR_FORMAT.ASVL_PAF_I420 == inputImg.u32PixelArrayFormat) {
            inputImg.pi32Pitch[0] = inputImg.i32Width;
            inputImg.pi32Pitch[1] = inputImg.i32Width / 2;
            inputImg.pi32Pitch[2] = inputImg.i32Width / 2;
            yuv_rawdata_size = inputImg.i32Width * inputImg.i32Height * 3 / 2;
        } else if (ASVL_COLOR_FORMAT.ASVL_PAF_NV12 == inputImg.u32PixelArrayFormat) {
            inputImg.pi32Pitch[0] = inputImg.i32Width;
            inputImg.pi32Pitch[1] = inputImg.i32Width;
            yuv_rawdata_size = inputImg.i32Width * inputImg.i32Height * 3 / 2;
        } else if (ASVL_COLOR_FORMAT.ASVL_PAF_NV21 == inputImg.u32PixelArrayFormat) {
            inputImg.pi32Pitch[0] = inputImg.i32Width;
            inputImg.pi32Pitch[1] = inputImg.i32Width;
            yuv_rawdata_size = inputImg.i32Width * inputImg.i32Height * 3 / 2;
        } else if (ASVL_COLOR_FORMAT.ASVL_PAF_YUYV == inputImg.u32PixelArrayFormat) {
            inputImg.pi32Pitch[0] = inputImg.i32Width * 2;
            yuv_rawdata_size = inputImg.i32Width * inputImg.i32Height * 2;
        } else if (ASVL_COLOR_FORMAT.ASVL_PAF_RGB24_B8G8R8 == inputImg.u32PixelArrayFormat) {
            inputImg.pi32Pitch[0] = inputImg.i32Width * 3;
            yuv_rawdata_size = inputImg.i32Width * inputImg.i32Height * 3;
        } else {
            System.out.println("unsupported  yuv format");
            System.exit(0);
        }

        // load YUV Image Data from File
        byte[] imagedata = new byte[yuv_rawdata_size];
        File f = new File(yuv_filePath);
        InputStream ios = null;
        try {
            ios = new FileInputStream(f);
            ios.read(imagedata,0,yuv_rawdata_size);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in loading yuv file");
            System.exit(0);
        } finally {
            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {
            }
        }

        if (ASVL_COLOR_FORMAT.ASVL_PAF_I420 == inputImg.u32PixelArrayFormat) {
            inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[0].write(0, imagedata, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[1] = new Memory(inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[1].write(0, imagedata, inputImg.pi32Pitch[0] * inputImg.i32Height, inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[2] = new Memory(inputImg.pi32Pitch[2] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[2].write(0, imagedata, inputImg.pi32Pitch[0] * inputImg.i32Height + inputImg.pi32Pitch[1] * inputImg.i32Height / 2, inputImg.pi32Pitch[2] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[3] = Pointer.NULL;
        } else if (ASVL_COLOR_FORMAT.ASVL_PAF_NV12 == inputImg.u32PixelArrayFormat) {
            inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[0].write(0, imagedata, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[1] = new Memory(inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[1].write(0, imagedata, inputImg.pi32Pitch[0] * inputImg.i32Height, inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[2] = Pointer.NULL;
            inputImg.ppu8Plane[3] = Pointer.NULL;
        } else if (ASVL_COLOR_FORMAT.ASVL_PAF_NV21 == inputImg.u32PixelArrayFormat) {
            inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[0].write(0, imagedata, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[1] = new Memory(inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[1].write(0, imagedata, inputImg.pi32Pitch[0] * inputImg.i32Height, inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[2] = Pointer.NULL;
            inputImg.ppu8Plane[3] = Pointer.NULL;
        } else if (ASVL_COLOR_FORMAT.ASVL_PAF_YUYV == inputImg.u32PixelArrayFormat) {
            inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[0].write(0, imagedata, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[1] = Pointer.NULL;
            inputImg.ppu8Plane[2] = Pointer.NULL;
            inputImg.ppu8Plane[3] = Pointer.NULL;
        } else if (ASVL_COLOR_FORMAT.ASVL_PAF_RGB24_B8G8R8 == inputImg.u32PixelArrayFormat) {
            inputImg.ppu8Plane[0] = new Memory(imagedata.length);
            inputImg.ppu8Plane[0].write(0, imagedata, 0, imagedata.length);
            inputImg.ppu8Plane[1] = Pointer.NULL;
            inputImg.ppu8Plane[2] = Pointer.NULL;
            inputImg.ppu8Plane[3] = Pointer.NULL;
        } else {
            System.out.println("unsupported yuv format");
            System.exit(0);
        }

        inputImg.setAutoRead(false);
        return inputImg;
    }

    //导入BMP，JPG，PNG格式的图片文件
    public static ASVLOFFSCREEN loadImage(String filePath) {
        ASVLOFFSCREEN inputImg = new ASVLOFFSCREEN();

        if (bUseBGRToEngine) {
            BufferInfo bufferInfo = ImageLoader.getBGRFromFile(filePath);
            inputImg.u32PixelArrayFormat = ASVL_COLOR_FORMAT.ASVL_PAF_RGB24_B8G8R8;
            inputImg.i32Width = bufferInfo.width;
            inputImg.i32Height = bufferInfo.height;
            inputImg.pi32Pitch[0] = inputImg.i32Width * 3;
            inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[0].write(0, bufferInfo.buffer, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[1] = Pointer.NULL;
            inputImg.ppu8Plane[2] = Pointer.NULL;
            inputImg.ppu8Plane[3] = Pointer.NULL;
        } else {
            BufferInfo bufferInfo = ImageLoader.getI420FromFile(filePath);
            inputImg.u32PixelArrayFormat = ASVL_COLOR_FORMAT.ASVL_PAF_I420;
            inputImg.i32Width = bufferInfo.width;
            inputImg.i32Height = bufferInfo.height;
            inputImg.pi32Pitch[0] = inputImg.i32Width;
            inputImg.pi32Pitch[1] = inputImg.i32Width / 2;
            inputImg.pi32Pitch[2] = inputImg.i32Width / 2;
            inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[0].write(0, bufferInfo.buffer, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
            inputImg.ppu8Plane[1] = new Memory(inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[1].write(0, bufferInfo.buffer, inputImg.pi32Pitch[0] * inputImg.i32Height, inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[2] = new Memory(inputImg.pi32Pitch[2] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[2].write(0, bufferInfo.buffer, inputImg.pi32Pitch[0] * inputImg.i32Height + inputImg.pi32Pitch[1] * inputImg.i32Height / 2, inputImg.pi32Pitch[2] * inputImg.i32Height / 2);
            inputImg.ppu8Plane[3] = Pointer.NULL;
        }

        inputImg.setAutoRead(false);
        return inputImg;
    }

    public static void freeMemory() {
        CLibrary.INSTANCE.free(pFDWorkMem);
        CLibrary.INSTANCE.free(pFRWorkMem);
        CLibrary.INSTANCE.free(pFAWorkMem);

    }
    //再不使用FaceTool的情况下，应该释放引擎;释放引擎后，再次使用，需要重新调用init函数。
    public static void destroy(){
        hasINIT=false;
        AFD_FSDKLibrary.INSTANCE.AFD_FSDK_UninitialFaceEngine(phFDEngine.getValue());
        AFR_FSDKLibrary.INSTANCE.AFR_FSDK_UninitialEngine(phFREngine.getValue());
        ASAE_FSDKLibrary.INSTANCE.ASAE_FSDK_UninitAgeEngine(phFAEngine.getValue());
    }
    public static boolean hasINIT()
    {
        return hasINIT;
    }
}
