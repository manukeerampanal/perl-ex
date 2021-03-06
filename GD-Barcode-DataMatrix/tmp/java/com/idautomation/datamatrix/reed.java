// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 18.01.2007 8:29:20
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   reed.java

package com.idautomation.datamatrix;

public class reed
{

    public reed()
    {
        N = 255;
    }

    static int mod(int i)
    {
        for(i = ((i -= 255) >> 8) + (i & 0xff); i >= 255; i = ((i -= 255) >> 8) + (i & 0xff));
        return i;
    }

    private int mult(int i, int j)
    {
        int k = 0;
        if(i == 0 || j == 0)
            return 0;
        k = gflog[i] + gflog[j];
        if(k >= N)
            k -= N;
        return gfi[k];
    }
    
    public String join(String d, int a[]){
    	String r = "";
    	for (int i=0; i < a.length; i++){
    		r += ( r != "" ? ", " : "" ) + a[i];
    	}
    	return r;
    }

    public void calcRS(int ai[], int i, int j)
    {
    	//System.out.println("calcRS");
        setpolynomial(j);
        //System.out.println("Get poly: " + join(",",p));
        for(int k = i; k < i + j; k++)
            ai[k] = 0;

        for(int l = 0; l < i; l++)
        {
            short word0 = (short)(ai[i] ^ ai[l]);
            for(int i1 = 0; i1 < j; i1++)
                ai[i + i1] = (short)(ai[i + i1 + 1] ^ mult(word0, p[i1]));

            ai[(i + j) - 1] = mult(word0, p[j - 1]);
        }

    }

    private int gfadd(int i, int j)
    {
        int k = i ^ j;
        return k;
    }

    public void setpolynomial(int i)
    {
        p = p68;
        if(i == 5)
            p = p5;
        if(i == 7)
            p = p7;
        if(i == 10)
            p = p10;
        if(i == 11)
            p = p11;
        if(i == 12)
            p = p12;
        if(i == 14)
            p = p14;
        if(i == 18)
            p = p18;
        if(i == 20)
            p = p20;
        if(i == 24)
            p = p24;
        if(i == 28)
            p = p28;
        if(i == 36)
            p = p36;
        if(i == 42)
            p = p42;
        if(i == 48)
            p = p48;
        if(i == 56)
            p = p56;
        if(i == 62)
            p = p62;
    }

    public static final short gfi[] = {
        1, 2, 4, 8, 16, 32, 64, 128, 45, 90, 
        180, 69, 138, 57, 114, 228, 229, 231, 227, 235, 
        251, 219, 155, 27, 54, 108, 216, 157, 23, 46, 
        92, 184, 93, 186, 89, 178, 73, 146, 9, 18, 
        36, 72, 144, 13, 26, 52, 104, 208, 141, 55, 
        110, 220, 149, 7, 14, 28, 56, 112, 224, 237, 
        247, 195, 171, 123, 246, 193, 175, 115, 230, 225, 
        239, 243, 203, 187, 91, 182, 65, 130, 41, 82, 
        164, 101, 202, 185, 95, 190, 81, 162, 105, 210, 
        137, 63, 126, 252, 213, 135, 35, 70, 140, 53, 
        106, 212, 133, 39, 78, 156, 21, 42, 84, 168, 
        125, 250, 217, 159, 19, 38, 76, 152, 29, 58, 
        116, 232, 253, 215, 131, 43, 86, 172, 117, 234, 
        249, 223, 147, 11, 22, 44, 88, 176, 77, 154, 
        25, 50, 100, 200, 189, 87, 174, 113, 226, 233, 
        255, 211, 139, 59, 118, 236, 245, 199, 163, 107, 
        214, 129, 47, 94, 188, 85, 170, 121, 242, 201, 
        191, 83, 166, 97, 194, 169, 127, 254, 209, 143, 
        51, 102, 204, 181, 71, 142, 49, 98, 196, 165, 
        103, 206, 177, 79, 158, 17, 34, 68, 136, 61, 
        122, 244, 197, 167, 99, 198, 161, 111, 222, 145, 
        15, 30, 60, 120, 240, 205, 183, 67, 134, 33, 
        66, 132, 37, 74, 148, 5, 10, 20, 40, 80, 
        160, 109, 218, 153, 31, 62, 124, 248, 221, 151, 
        3, 6, 12, 24, 48, 96, 192, 173, 119, 238, 
        241, 207, 179, 75, 150
    };
    public static final short gflog[] = {
        0, 0, 1, 240, 2, 225, 241, 53, 3, 38, 
        226, 133, 242, 43, 54, 210, 4, 195, 39, 114, 
        227, 106, 134, 28, 243, 140, 44, 23, 55, 118, 
        211, 234, 5, 219, 196, 96, 40, 222, 115, 103, 
        228, 78, 107, 125, 135, 8, 29, 162, 244, 186, 
        141, 180, 45, 99, 24, 49, 56, 13, 119, 153, 
        212, 199, 235, 91, 6, 76, 220, 217, 197, 11, 
        97, 184, 41, 36, 223, 253, 116, 138, 104, 193, 
        229, 86, 79, 171, 108, 165, 126, 145, 136, 34, 
        9, 74, 30, 32, 163, 84, 245, 173, 187, 204, 
        142, 81, 181, 190, 46, 88, 100, 159, 25, 231, 
        50, 207, 57, 147, 14, 67, 120, 128, 154, 248, 
        213, 167, 200, 63, 236, 110, 92, 176, 7, 161, 
        77, 124, 221, 102, 218, 95, 198, 90, 12, 152, 
        98, 48, 185, 179, 42, 209, 37, 132, 224, 52, 
        254, 239, 117, 233, 139, 22, 105, 27, 194, 113, 
        230, 206, 87, 158, 80, 189, 172, 203, 109, 175, 
        166, 62, 127, 247, 146, 66, 137, 192, 35, 252, 
        10, 183, 75, 216, 31, 83, 33, 73, 164, 144, 
        85, 170, 246, 65, 174, 61, 188, 202, 205, 157, 
        143, 169, 82, 72, 182, 215, 191, 251, 47, 178, 
        89, 151, 101, 94, 160, 123, 26, 112, 232, 21, 
        51, 238, 208, 131, 58, 69, 148, 18, 15, 16, 
        68, 17, 121, 149, 129, 19, 155, 59, 249, 70, 
        214, 250, 168, 71, 201, 156, 64, 60, 237, 130, 
        111, 20, 93, 122, 177, 150
    };
    static int K;
    public int N;
    static int p[];
    static final int p5[] = {
        62, 111, 15, 48, 228
    };
    static final int p7[] = {
        254, 92, 240, 134, 144, 68, 23
    };
    static final int p10[] = {
        61, 110, 255, 116, 248, 223, 166, 185, 24, 28
    };
    static final int p11[] = {
        120, 97, 60, 245, 39, 168, 194, 12, 205, 138, 
        175
    };
    static final int p12[] = {
        242, 100, 178, 97, 213, 142, 42, 61, 91, 158, 
        153, 41
    };
    static final int p14[] = {
        185, 83, 186, 18, 45, 138, 119, 157, 9, 95, 
        252, 192, 97, 156
    };
    static final int p18[] = {
        188, 90, 48, 225, 254, 94, 129, 109, 213, 241, 
        61, 66, 75, 188, 39, 100, 195, 83
    };
    static final int p20[] = {
        172, 186, 174, 27, 82, 108, 79, 253, 145, 153, 
        160, 188, 2, 168, 71, 233, 9, 244, 195, 15
    };
    static final int p24[] = {
        193, 50, 96, 184, 181, 12, 124, 254, 172, 5, 
        21, 155, 223, 251, 197, 155, 21, 176, 39, 109, 
        205, 88, 190, 52
    };
    static final int p28[] = {
        255, 93, 168, 233, 151, 120, 136, 141, 213, 110, 
        138, 17, 121, 249, 34, 75, 53, 170, 151, 37, 
        174, 103, 96, 71, 97, 43, 231, 211
    };
    static final int p36[] = {
        112, 81, 98, 225, 25, 59, 184, 175, 44, 115, 
        119, 95, 137, 101, 33, 68, 4, 2, 18, 229, 
        182, 80, 251, 220, 179, 84, 120, 102, 181, 162, 
        250, 130, 218, 242, 127, 245
    };
    static final int p42[] = {
        5, 9, 5, 226, 177, 150, 50, 69, 202, 248, 
        101, 54, 57, 253, 1, 21, 121, 57, 111, 214, 
        105, 167, 9, 100, 95, 175, 8, 242, 133, 245, 
        2, 122, 105, 247, 153, 22, 38, 19, 31, 137, 
        193, 77
    };
    static final int p48[] = {
        19, 225, 253, 92, 213, 69, 175, 160, 147, 187, 
        87, 176, 44, 82, 240, 186, 138, 66, 100, 120, 
        88, 131, 205, 170, 90, 37, 23, 118, 147, 16, 
        106, 191, 87, 237, 188, 205, 231, 238, 133, 238, 
        22, 117, 32, 96, 223, 172, 132, 245
    };
    static final int p56[] = {
        46, 143, 53, 233, 107, 203, 43, 155, 28, 247, 
        67, 127, 245, 137, 13, 164, 207, 62, 117, 201, 
        150, 22, 238, 144, 232, 29, 203, 117, 234, 218, 
        146, 228, 54, 132, 200, 38, 223, 36, 159, 150, 
        235, 215, 192, 230, 170, 175, 29, 100, 208, 220, 
        17, 12, 238, 223, 9, 175
    };
    static final int p62[] = {
        204, 11, 47, 86, 124, 224, 166, 94, 7, 232, 
        107, 4, 170, 176, 31, 163, 17, 188, 130, 40, 
        10, 87, 63, 51, 218, 27, 6, 147, 44, 161, 
        71, 114, 64, 175, 221, 185, 106, 250, 190, 197, 
        63, 245, 230, 134, 112, 185, 37, 196, 108, 143, 
        189, 201, 188, 202, 118, 39, 210, 144, 50, 169, 
        93, 242
    };
    static final int p68[] = {
        186, 82, 103, 96, 63, 132, 153, 108, 54, 64, 
        189, 211, 232, 49, 25, 172, 52, 59, 241, 181, 
        239, 223, 136, 231, 210, 96, 232, 220, 25, 179, 
        167, 202, 185, 153, 139, 66, 236, 227, 160, 15, 
        213, 93, 122, 68, 177, 158, 197, 234, 180, 248, 
        136, 213, 127, 73, 36, 154, 244, 147, 33, 89, 
        56, 159, 149, 251, 89, 173, 228, 220
    };

}