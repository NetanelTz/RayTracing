package renderer;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Test for imageWriter class
 *
 * @author Noam and Netanel
 */
public class ImageWriterTest {

    @Test
    public void getWidth() {
    }

    @Test
    public void getHeight() {
    }

    @Test
    public void getNy() {
    }

    @Test
    public void getNx() {
    }

    @Test
    public void writeToImage() {
    }

    /**
     * Test Method for
     * {@link renderer.ImageWriter#writePixel(int, int, Color)}  }
     * the test create simple image of 500*800 pixels and 1000*1600 W*H and 10*16 squares
     */
    @Test
    public void writePixel() {
        ImageWriter imageWriter = new ImageWriter("First Image", 1600, 100, 800, 500);
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(j, i, Color.YELLOW);
                } else {
                    imageWriter.writePixel(j, i, Color.CYAN);
                }
            }
        }
        imageWriter.writeToImage();
    }
}