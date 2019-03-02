package cn.stylefeng.guns.facesupport.genderestimation;


import cn.stylefeng.guns.facesupport.generalsupport.CLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

public class ASGE_FSDK_GENDERRESULT extends Structure {
    public IntByReference pGenderResultArray;
    public int lFaceNumber;
    protected boolean bAllocByMalloc;

    public ASGE_FSDK_GENDERRESULT() {

    }

    public ASGE_FSDK_GENDERRESULT(Pointer p) {
        super(p);
        read();
    }


    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[] {
                "pGenderResultArray", "lFaceNumber"
        });
    }

    public ASGE_FSDK_GENDERRESULT deepCopy() throws Exception {

        if(!isValid()){
            throw new Exception("invalid gender");
        }

        ASGE_FSDK_GENDERRESULT result = new ASGE_FSDK_GENDERRESULT();
        result.bAllocByMalloc = true;
        result.lFaceNumber = lFaceNumber;
        result.pGenderResultArray = new IntByReference();
        result.pGenderResultArray.setPointer(CLibrary.INSTANCE.malloc(result.lFaceNumber));
        CLibrary.INSTANCE.memcpy(result.pGenderResultArray.getPointer(),pGenderResultArray.getPointer(),result.lFaceNumber);
        return result;
    }

    public synchronized void freeUnmanaged(){
        if(bAllocByMalloc&&isValid()){
            CLibrary.INSTANCE.free(pGenderResultArray.getPointer());
            pGenderResultArray = null;
            //System.out.println("gc feature freeUnmanaged");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        freeUnmanaged();
    }

    private boolean isValid() {
        return ((pGenderResultArray != null)&&(Pointer.nativeValue(pGenderResultArray.getPointer())!= 0));
    }
}