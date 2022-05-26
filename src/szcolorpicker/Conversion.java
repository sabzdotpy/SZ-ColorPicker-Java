package szcolorpicker;

import java.awt.Color;

class Conversion {

    private static float[] hsbStore = new float[3];
    private static StringBuilder builder = new StringBuilder();

    static String rgbToHex(int r, int g, int b) {
        return (Integer.toHexString(new Color(r, g, b).getRGB()));
    }

    static String rgbToHsb(int r, int g, int b) {
        hsbStore = Color.RGBtoHSB( r, g, b, null );

        return (int)(hsbStore[0]*360) + "," + (int)(hsbStore[1]*100) + "," + (int)(hsbStore[2]*100);
    }

    static void hsbToRGB(int h, int s, int b) {
        int rgb = Color.HSBtoRGB(h, s, b);

        System.out.println(Integer.toHexString(rgb));
    }

    static String hexToRGB(String hex) {
        builder.setLength(0);

        for (int i = 0; i < 5; i+=2 ) {
            builder.append(Integer.parseInt(hex.substring(i, i + 2), 16)).append(",");
        }

        return ( builder.toString().substring(0, builder.toString().length() - 1) );
    }

    static String hexToHSB(String hex) {
        String[] rgbStore = hexToRGB(hex).split(",");
        return rgbToHsb( Integer.parseInt(rgbStore[0]), Integer.parseInt(rgbStore[1]), Integer.parseInt(rgbStore[2]) );
    }

}
