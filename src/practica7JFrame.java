
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Nassr Eddine Moussati Lamhamdi
 * @author Yousuf Boutahar El Maachi
 */
public class practica7JFrame extends javax.swing.JFrame {

    //private DemoInternalFrame ifd;
    private final JFileChooser fileChooser = new JFileChooser();
    private final FileFilter jpgImg = new FileNameExtensionFilter("Imágenes *.jpg *.jpeg", "jpeg", "jpg");
    private final FileFilter pngImg = new FileNameExtensionFilter("Imágenes *.png", "png");
    private int threshold;
    private String fileName;
    private int nFrame = 0;
    private boolean mainWindow;

    private Mat MatOriginalImage;
    private BufferedImage originalImage;
    private BufferedImage UmbralImage;

    //static boolean ImagenNueva = false;// cuando abrimos una imagen nueva
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Creates new form NewJFrame
     */
    public practica7JFrame() {
        initComponents();
        setBounds(100, 0, 1300, 700);

        disableUnnecessaryMenuItems(false);
        setMinimumSize(getPreferredSize());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

    }

    /**
     * Metodo que solo es invocado una vez y es el de la imagen principal
     */
    private void muestraVentana(BufferedImage img) {

        nFrame++;
        DemoInternalFrame ifd = new DemoInternalFrame(nFrame, img, mainWindow);
        ifd.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        ifd.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                // preguntar si al cerrar la ventana quiere guardarla
                if (ifd.isPrincipal() == false) {
                    System.out.println("La imagen " + ifd.getTitle() + " es " + ifd.isPrincipal());
                    saveImage(ifd);
                    desktop.repaint();

                } else {
                    saveAll();
                }
            }
        });
        ifd.setSize(500, 300);
        desktop.add(ifd);
    }

    private boolean saveAll() {
        for (JInternalFrame frame : desktop.getAllFrames()) {
            DemoInternalFrame ifd = (DemoInternalFrame) frame;
            if (ifd.isPrincipal() == false) {
                if (saveImage(ifd) == false) {
                    return false;
                }
                desktop.repaint();
            }
        }

        limpiarEscritorio();
        disableUnnecessaryMenuItems(false);
        return true;
    }

    private boolean saveImage(DemoInternalFrame ifd) {
        Object[] buttons = {"Guardar", "No guardar", "Cancelar cierre"};
        if (ifd.isSaved() == false) {
            try {
                ifd.setSelected(true);
            } catch (PropertyVetoException ex) {
            }
            int buttonSelected = JOptionPane.showOptionDialog(null,
                    "Seleccione si quiere guardar la imagen",
                    "Guardar imagen", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, buttons, buttons[0]);
            if (buttonSelected == 0) {
                if (saveImageOfDIF(ifd)) {
                    desktop.remove(ifd);
                    nFrame--;
                    return true;
                } else {
                    return false;
                }
            }
            if (buttonSelected == 1) {
                desktop.remove(ifd);
                nFrame--;
                return true;
            }
            if (buttonSelected == 2) {
                return false;
            }

        } else {
            desktop.remove(ifd);
            nFrame--;
        }
        return true;
    }

    private boolean saveImageOfDIF(DemoInternalFrame dif) {
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File imageName = fileChooser.getSelectedFile();
                if (!ImageIO.write(dif.getImage(), "jpg", imageName)) {
                    JOptionPane.showMessageDialog(null, "No se ha podido guardar la imagen.",
                            "Error al guardar la imagen", JOptionPane.ERROR_MESSAGE);
                }
                dif.setSaved(true);
                dif.setTitleOfFrame();
            } catch (IOException ex) {
            }
        }

        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        myDialog = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        desktop = new javax.swing.JDesktopPane();
        menu = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        openFile = new javax.swing.JMenuItem();
        saveImage = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        threshholdMenuItem = new javax.swing.JMenuItem();
        help = new javax.swing.JMenu();
        about = new javax.swing.JMenuItem();

        jLabel1.setText("jLabel1");

        myDialog.setTitle("Mi ventana");
        myDialog.setBounds(new java.awt.Rectangle(0, 23, 300, 200));
        myDialog.setModal(true);
        myDialog.setResizable(false);
        myDialog.setType(java.awt.Window.Type.POPUP);

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout myDialogLayout = new javax.swing.GroupLayout(myDialog.getContentPane());
        myDialog.getContentPane().setLayout(myDialogLayout);
        myDialogLayout.setHorizontalGroup(
            myDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myDialogLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel2)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        myDialogLayout.setVerticalGroup(
            myDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myDialogLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel2)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(700, 500));

        file.setText("Archivo");

        openFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openFile.setText("Cargar imagen...");
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });
        file.add(openFile);

        saveImage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveImage.setText("Guardar...");
        saveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageActionPerformed(evt);
            }
        });
        file.add(saveImage);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exit.setText("Salir");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        file.add(exit);

        menu.add(file);

        edit.setText("Editar");

        threshholdMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        threshholdMenuItem.setText("Umbralizar...");
        threshholdMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threshholdMenuItemActionPerformed(evt);
            }
        });
        edit.add(threshholdMenuItem);

        menu.add(edit);

        help.setText("Ayuda");

        about.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        about.setText("Acerca de...");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        help.add(about);

        menu.add(help);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed
        if (originalImage != null && nFrame > 1) {
            int desicion = JOptionPane.showConfirmDialog(null,
                    "¿Quiere guardar el trabajo realizado?", "Salir",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            switch (desicion) {
                case JOptionPane.YES_OPTION:
                    saveAll();
                    break;
                case JOptionPane.NO_OPTION:
                    limpiarEscritorio();
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return;
                default:
                    break;
            }
        }

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(pngImg);
        fileChooser.addChoosableFileFilter(jpgImg);

        try {
            int status = fileChooser.showOpenDialog(null);
            limpiarEscritorio();
            if (status == JFileChooser.APPROVE_OPTION) {
                File imagen = fileChooser.getSelectedFile();
                this.fileName = imagen.getAbsolutePath();
                Mat img = Imgcodecs.imread(imagen.getAbsolutePath());
                this.MatOriginalImage = img;
                this.originalImage = (BufferedImage) HighGui.toBufferedImage(img);

                // Se crea la ventana con la imagen original
                this.mainWindow = true;
                this.muestraVentana(this.originalImage);
                this.mainWindow = false;
                disableUnnecessaryMenuItems(true);
            }
        } catch (HeadlessException e) {
            System.out.println("No se pudo cargar la imagen: " + e);
        }

    }//GEN-LAST:event_openFileActionPerformed

    private void threshholdMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threshholdMenuItemActionPerformed

        try {
            if (MatOriginalImage == null) {
                JOptionPane.showMessageDialog(null, "Introduzca primero una imagen.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String res = JOptionPane.showInputDialog(null, "Valor del umbral", "Aplicar", JOptionPane.PLAIN_MESSAGE);
            if (res == null) {
                return;
            }
            threshold = Math.max(0, Integer.parseInt(res));
            if (threshold < 0 || threshold > 255) {
                throw new NumberFormatException();
            }
            System.out.println("El threshold es de: " + threshold);
            if (MatOriginalImage != null) {
                this.UmbralImage = (BufferedImage) HighGui.toBufferedImage(thresholdImage(MatOriginalImage, threshold));
                this.muestraVentana(this.UmbralImage);// se crea la ventana en el escritorio
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Sólo se aceptan números enteros y se estén entre 0 y 255", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.out.println("Sólo se aceptan números enteros y se estén entre 0 y 255.");
            System.out.println("Exception: " + e);
        }
    }//GEN-LAST:event_threshholdMenuItemActionPerformed

    private void saveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImageActionPerformed

        DemoInternalFrame dif = null;
        if (originalImage != null) {
            dif = (DemoInternalFrame) desktop.getSelectedFrame();
        }

        if (dif != null && dif.isPrincipal() == true) {
            JOptionPane.showMessageDialog(null, "No se puede guardar la imagen original. \nSeleccione una umbralizada.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dif != null) {
            if (!dif.isPrincipal()) {
                if (dif.isSaved() == true) {
                    int desicion = JOptionPane.showConfirmDialog(null,
                            "La imagen ya esta guardada. \n¿Quiere volver a guardarla?", "Guardar imagen",
                            JOptionPane.YES_NO_OPTION);
                    if (desicion == JOptionPane.YES_OPTION) {
                        saveImageOfDIF(dif);
                        return;
                    } else {
                        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        return;
                    }
                }
                saveImageOfDIF(dif);
            } else {
                JOptionPane.showMessageDialog(null, "No se puede guardar la imagen seleccionada.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar la imagen a guardar.",
                    "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveImageActionPerformed

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        JOptionPane.showMessageDialog(null,
                "\nAplicación "
                + "desarrollada por:\n   Nassr Eddine Moussati Lamhamdi\n   "
                + "Yousuf Boutahar El Maachi", "Acerca de ...", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_aboutActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        closeWindow();
    }//GEN-LAST:event_exitActionPerformed

    private void limpiarEscritorio() {
        MatOriginalImage = null;
        originalImage = null;
        UmbralImage = null;
        fileName = "";
        nFrame = 0;
        desktop.removeAll();
        desktop.repaint();
    }

    private String getExtension(String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }
        return extension;
    }

    private Mat thresholdImage(Mat originalImage, int threshold) {
        Mat grayImage = new Mat(originalImage.rows(), originalImage.cols(), CvType.CV_8U);
        Mat thresholdImage = new Mat(originalImage.rows(), originalImage.cols(), CvType.CV_8U);
        Imgproc.cvtColor(originalImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(grayImage, thresholdImage, threshold, 255, Imgproc.THRESH_BINARY);
        return thresholdImage;
    }

    private void closeWindow() {
        int exitValue = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir de la aplicación?.", "Salir",
                JOptionPane.YES_NO_OPTION);
        if (exitValue == JOptionPane.YES_OPTION) {
            if (saveAll()) System.exit(0);
        } else {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(practica7JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(practica7JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(practica7JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(practica7JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new practica7JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JMenu edit;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu file;
    private javax.swing.JMenu help;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar menu;
    private javax.swing.JDialog myDialog;
    private javax.swing.JMenuItem openFile;
    private javax.swing.JMenuItem saveImage;
    private javax.swing.JMenuItem threshholdMenuItem;
    // End of variables declaration//GEN-END:variables

    private void disableUnnecessaryMenuItems(boolean state) {
        saveImage.setEnabled(state);
        threshholdMenuItem.setEnabled(state);
    }

    /**
     * Clase sencilla que se encarga de externder una ventana interna.
     */
    private class DemoInternalFrame extends JInternalFrame {

        private String title = "";
        private final BufferedImage image;
        //Contador que aumenta cada vez que instanciamos una ventana.
        private final int openFrameCount;
        private final boolean principal;
        private boolean saved;
        //Posición de la ventana interna.
        private final int xOffset = 50, yOffset = 50;
        private int posX = 0, posY = 0;

        public DemoInternalFrame(int numberFrame, BufferedImage img, boolean principal) {
            super("",
                    true, //Resizable
                    true, //Closable
                    true, //Maximizable
                    true);//Iconifiable

            this.openFrameCount = numberFrame;
            this.image = img;
            this.principal = principal;
            this.saved = principal;
            // PROBAR CON +10
            this.posX = (this.xOffset * numberFrame) % desktop.getWidth();
            this.posY = (this.yOffset * numberFrame) % desktop.getHeight();

            setVisible(true);
            setLocation(posX, posY);
            setTitleOfFrame();

            try {
                Lienzo panel = new Lienzo();
                panel.setImage(img);
                add(panel);
                pack();
            } catch (Exception e) {
                System.out.print("ERROR");
            }
        }

        public void setTitleOfFrame() {
            String title = fileName;
            if (principal == false) {
                title = "[" + this.openFrameCount + "] " + title;
            }
            if (saved == false) {
                setTitle(title + " *");
                return;
            }
            this.title = title;
            setTitle(title);
        }

        public String getTitleOfFrame() {
            return this.title;
        }

        public void setSaved(boolean state) {
            this.saved = state;
        }

        public boolean isPrincipal() {
            return principal;
        }

        public boolean isSaved() {
            return saved;
        }

        public BufferedImage getImage() {
            return image;
        }

    }
}
