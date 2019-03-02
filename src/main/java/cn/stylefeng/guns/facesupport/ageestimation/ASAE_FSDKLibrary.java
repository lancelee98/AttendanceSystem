package cn.stylefeng.guns.facesupport.ageestimation;


import cn.stylefeng.guns.facesupport.facedetection.AFD_FSDK_Version;
import cn.stylefeng.guns.facesupport.generalsupport.ASVLOFFSCREEN;
import cn.stylefeng.guns.facesupport.generalsupport.LoadUtils;
import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


public interface ASAE_FSDKLibrary extends Library {

    ASAE_FSDKLibrary INSTANCE = (ASAE_FSDKLibrary) LoadUtils.loadLibrary(Platform.isWindows()?"libarcsoft_fsdk_age_estimation.dll":"libarcsoft_fsdk_age_estimation.so", ASAE_FSDKLibrary.class);

    NativeLong ASAE_FSDK_InitAgeEngine(String appid, String sdkid, Pointer pMem, int lMemSize, PointerByReference phEngine);

    NativeLong ASAE_FSDK_AgeEstimation_StaticImage(Pointer hEngine, ASVLOFFSCREEN pImginfo, ASAE_FSDK_AGEFACEINPUT pFaceRes, ASAE_FSDK_AGERESULT pAgeRes);

    NativeLong ASAE_FSDK_AgeEstimation_Preview(Pointer hEngine, ASVLOFFSCREEN pImginfo, PointerByReference pFaceRes, PointerByReference pAgeRes);

    NativeLong ASAE_FSDK_UninitAgeEngine(Pointer hEngine);

    AFD_FSDK_Version AFD_FSDK_GetVersion(Pointer hEngine);
}
