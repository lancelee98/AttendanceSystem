package cn.stylefeng.guns.facesupport.ageestimation;

import cn.stylefeng.guns.facesupport.generalsupport.MRECT;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

public class ASAE_FSDK_AGEFACEINPUT extends Structure {
    public static class ByReference extends ASAE_FSDK_AGEFACEINPUT implements Structure.ByReference {
        public ByReference() {
        }
        public ByReference(Pointer p) {
            super(p);
        }
    };
    public MRECT.ByReference pFaceRectArray;
    public IntByReference pFaceOrientArray;
    public int lFaceNumber;

    public ASAE_FSDK_AGEFACEINPUT() {

    }

    public ASAE_FSDK_AGEFACEINPUT(Pointer p) {
        super(p);
        read();
    }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[] { "pFaceRectArray", "pFaceOrientArray", "lFaceNumber" });
    }
}
