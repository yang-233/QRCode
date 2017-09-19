import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;
import java.awt.*;

public class CreateQR{
    Map<EncodeHintType, Object> hints;
    QRCodeWriter writer;
    public CreateQR() {
        hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        writer=new QRCodeWriter();
    }
    public Image toImage(String str,int size) throws WriterException {
        return (Image)MatrixToImageWriter.toBufferedImage(writer.encode(str, BarcodeFormat.QR_CODE,size,size,hints));
    }
}