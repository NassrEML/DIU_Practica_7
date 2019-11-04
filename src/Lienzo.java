import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author Nassr Eddine Moussati Lamhamdi
 * @author Yousuf Boutahar El Maachi
 */

public class Lienzo extends JPanel{
    
    private BufferedImage buffImage = null;
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(buffImage, 0, 0, getWidth(), getHeight(), this);
                
    }
    
    public void setImage(BufferedImage imagen){
        try {
            this.buffImage = imagen;
            repaint();
        } catch (Exception e) {
            System.out.println("Hubo un error: " + e);
        }   
    }
    
    public boolean storeImg(File imageName, String extension) {
        if (Arrays.asList("jpeg", "jpg", "png").contains(extension)){
            try {
                ImageIO.write(buffImage, extension, imageName);
                return true;
            } catch (IOException ex) {
                System.out.println("IOException: " + ex);
                return false;
            }
        }
        return false;
    }

    public boolean isEmpty(){
        return buffImage == null;
    }
    
    void clearBoard() {
        this.buffImage = null;
        repaint();
    }
    
}
