package cn.stylefeng.guns.facesupport.ageestimation;

import cn.stylefeng.guns.facesupport.generalsupport.CLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

public class ASAE_FSDK_AGERESULT extends Structure {
    public IntByReference pAgeResultArray;
    public int lFaceNumber;
    protected boolean bAllocByMalloc;

    public ASAE_FSDK_AGERESULT() {

    }

    public ASAE_FSDK_AGERESULT(Pointer p) {
        super(p);
        read();
    }


    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[] {
                "pAgeResultArray", "lFaceNumber"
        });
    }

    public ASAE_FSDK_AGERESULT deepCopy() throws Exception {

        if(!isValid()){
            throw new Exception("invalid age");
        }

        ASAE_FSDK_AGERESULT feature = new ASAE_FSDK_AGERESULT();
        feature.bAllocByMalloc = true;
        feature.lFaceNumber = lFaceNumber;
        feature.pAgeResultArray = new IntByReference();
        feature.pAgeResultArray.setPointer(CLibrary.INSTANCE.malloc(feature.lFaceNumber));
        CLibrary.INSTANCE.memcpy(feature.pAgeResultArray.getPointer(),pAgeResultArray.getPointer(),feature.lFaceNumber);
        return feature;
    }

    public synchronized void freeUnmanaged(){
        if(bAllocByMalloc&&isValid()){
            CLibrary.INSTANCE.free(pAgeResultArray.getPointer());
            pAgeResultArray = null;
            //System.out.println("gc feature freeUnmanaged");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        freeUnmanaged();
    }

    private boolean isValid() {
        return ((pAgeResultArray != null)&&(Pointer.nativeValue(pAgeResultArray.getPointer())!= 0));
    }
}
