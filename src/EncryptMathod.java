
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

class KeyErrorException extends Exception { }

public class EncryptMathod {

    public static byte[] DES(byte[] contain,String key,boolean encryptMode) throws Exception {
        if(key.length()==0||key.length()%8!=0) {
            throw new KeyErrorException();
        }
        else {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            System.out.println(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(encryptMode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, securekey, random);
            contain=cipher.doFinal(contain);
            return contain;

        }
    }

    public static byte[] XOR(byte[] contain,String key) throws KeyErrorException{
        if(key.length()==0)
            throw new KeyErrorException();
        byte[] keys=key.getBytes();
        for(int c=0;c<contain.length;c++)
            contain[c]=(byte)(keys[c%keys.length]^contain[c]);
        return contain;
    }

    public static byte[] Caesar(byte[] contain, int key, boolean encryptMode) throws KeyErrorException{
        if(key>255||key<-255)
            throw new KeyErrorException();
        if(!encryptMode)
            key=-key;
        for(int c=0;c<contain.length;c++){
            contain[c]=(byte)(((int)contain[c]+key)%256);
        }
        return contain;
    }
    public static String byte2Hex(byte[] arg_bteArray) {
        int intStringLength = arg_bteArray.length;
        StringBuilder objSb = new StringBuilder(intStringLength * 2);
        for (byte anArg_bteArray : arg_bteArray) {
            int intTemp = (int) anArg_bteArray;
            //負數需要轉成正數
            if (intTemp < 0) {
                intTemp = intTemp + 256;
            }
            // 小於0F需要補0
            if (intTemp < 16) {
                objSb.append("0");
            }
            objSb.append(Integer.toString(intTemp, 16));
        }
        return objSb.toString();
    }

    public static byte[] hex2Byte(String arg_strHexString) {
        byte[] arrByteDAta = arg_strHexString.getBytes();
        int intStringLength = arrByteDAta.length;
        byte[] aryRetuenData = new byte[intStringLength / 2];
        for (int i = 0; i < intStringLength; i = i + 2)
            aryRetuenData[i / 2] =  (byte)Integer.parseInt(new String(arrByteDAta, i, 2), 16);
        return aryRetuenData;
    }
}
