package cn.stylefeng.guns.facesupport.genderestimation;



import cn.stylefeng.guns.facesupport.generalsupport.ASVLOFFSCREEN;
import cn.stylefeng.guns.facesupport.generalsupport.LoadUtils;
import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface ASGE_FSDKLibrary extends Library {
    ASGE_FSDKLibrary INSTANCE = (ASGE_FSDKLibrary) LoadUtils.loadLibrary(Platform.isWindows()?"libarcsoft_fsdk_gender_estimation.dll":"libarcsoft_fsdk_gender_estimation.so", ASGE_FSDKLibrary.class);

    NativeLong ASGE_FSDK_InitGenderEngine(String appid, String sdkkey, Pointer pMem, int lMemSize, PointerByReference phEngine);

    NativeLong ASGE_FSDK_GenderEstimation_StaticImage(Pointer hEngine, ASVLOFFSCREEN pImginfo, ASGE_FSDK_GENDERFACEINPUT pFaceRes, ASGE_FSDK_GENDERRESULT pGenderRes);

    NativeLong ASGE_FSDK_GenderEstimation_Preview(Pointer hEngine, ASVLOFFSCREEN pImginfo, PointerByReference pFaceRes, PointerByReference pGenderRes);

    NativeLong ASGE_FSDK_UninitGenderEngine(Pointer hEngine);

    ASGE_FSDK_Version ASGE_FSDK_GetVersion(Pointer hEngine);
}
