package org.flimwip.design.utility.terminal;

public enum BMP {
    _1_B_BINARY, //Blau
    _5_B_BINARY,
    _8_B,
    _15_B_ASCII,
    _6_B_BCD,
    _2_B_BCD, // PINK
    _3_B_BCD, //Orange
    _4_B_BCD,
    _TLV_ENCODED,
    _LL_VAR, //Grün
    _LLL_VAR, // Gelb
    _LL_VAR_BCD;

    public static BMP get_type(String bmp_code){

        switch (bmp_code) {
            case "1", "2", "3", "5", "01", "02", "03", "05", "19", "27", "8A", "8C", "A0", "D0", "D2", "D3", "E0", "E9", "EA", "F0", "F9", "FA", "FB", "FC", "FD":
                return _1_B_BINARY;
            case "BA":
                return _5_B_BINARY;
            case "3B":
                return _8_B;
            case "2A":
                return _15_B_ASCII;
            case "04", "4":
                return _6_B_BCD;
            case "D", "0D", "0E", "17", "3A", "49", "87":
                return _2_B_BCD;
            case "0B", "B", "0C", "C", "37", "3D", "88", "AA":
                return _3_B_BCD;
            case "29":
                return _4_B_BCD;
            case "06", "6":
                return _TLV_ENCODED;
            case "23", "2D", "8B", "A7", "D1", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8":
                return _LL_VAR;
            case "24", "2E", "3C", "60", "9A", "AF":
                return _LLL_VAR;
            case "22":
                return _LL_VAR_BCD;
            default:
                return null;

        }
    }
}


