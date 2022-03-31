package unittests.renderer;

import org.junit.jupiter.api.Test;
import renderer.ImageWriter;
import primitives.*;

/**
 * Test writing a basic image
 *
 * @author Rivka Sheiner
 */
public class ImageWriterTest {

    /**
     * Test method for
     * {@link renderer.ImageWriter#writeToImage()}
     */
    @Test
    void testWriteToImage(){
        ImageWriter imageWriter = new ImageWriter("yellowRed", 800, 500);
        Color yellow = new Color(255, 255,0);
        Color red = new Color(255, 0, 0);
        for (int i = 0; i < imageWriter.getNy(); i++){
            for(int j = 0; j < imageWriter.getNx(); j++){
                if(i%50 == 0 || j%50 == 0)
                    imageWriter.writePixel(j, i, red);
                else
                    imageWriter.writePixel(j, i, yellow);
            }
        }
        imageWriter.writeToImage();
    }
}
