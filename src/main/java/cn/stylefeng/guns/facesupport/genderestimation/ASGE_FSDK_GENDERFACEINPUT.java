package cn.stylefeng.guns.facesupport.genderestimation;

import cn.stylefeng.guns.facesupport.generalsupport.MRECT;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

public class ASGE_FSDK_GENDERFACEINPUT extends Structure {
    public static class ByReference extends ASGE_FSDK_GENDERFACEINPUT implements Structure.ByReference {
        public ByReference() {
        }
        public ByReference(Pointer p) {
            super(p);
        }
    };
    public MRECT.ByReference pFaceRectArray;
    public IntByReference pFaceOrientArray;
    public int lFaceNumber;

    public ASGE_FSDK_GENDERFACEINPUT() {

    }

    public ASGE_FSDK_GENDERFACEINPUT(Pointer p) {
        super(p);
        read();
    }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[] { "pFaceRectArray", "pFaceOrientArray", "lFaceNumber" });
    }
}