package Tools;

import org.junit.Test;
import renderer.Render;
import Tools.*;

/**
 * test class for xml reading
 *
 * @author Noam and Netanel
 */
public class XmlTest {

    /**
     * test method for
     * {@link Tools.Xml#openRender(String)}
     */
    @Test
    public void openRenderTest() {

        Render check = Xml.openRender("basicRenderTestTwoColors.xml");
        check.renderImage();
        check.printGrid(50, java.awt.Color.YELLOW);
        check.writeToImage();


    }
}