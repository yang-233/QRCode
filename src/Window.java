import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Window extends JFrame {
    private static int width=1000,height=400;
    private static int fontSize=25;
    private static int heightDivation=30,widthDivation=8;
    private static int buttonWidth=(width-height)/3,buttonHeight=buttonWidth/3;
    private JTextArea textArea;
    private JButton translateButton,copyButton,saveButton;
    private MyPanel panel;
    private JScrollPane jsp;
    private Image img;
    private CreateQR cQR;
    private JFileChooser chooser;
    public Window() {
        super("Translate");
        textArea=new JTextArea();
        translateButton=new JButton("Translate");
        copyButton=new JButton("Copy");
        saveButton=new JButton("Save");
        panel=new MyPanel();
        jsp=new JScrollPane(textArea);
        img=null;
        cQR=new CreateQR();
        chooser=new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("黑体",Font.PLAIN,fontSize));
        textArea.setSelectedTextColor(Color.RED);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        setLayout(null);
        setBackground(Color.red);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width+widthDivation,height+heightDivation);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        add(jsp);
        add(translateButton);
        add(copyButton);
        add(saveButton);
        add(panel);
        jsp.setBounds(0,0,width-height,height-buttonHeight);
        translateButton.setBounds(0,height-buttonHeight,buttonWidth,buttonHeight);
        copyButton.setBounds(buttonWidth,height-buttonHeight,buttonWidth,buttonHeight);
        saveButton.setBounds(2*buttonWidth,height-buttonHeight,buttonWidth,buttonHeight);
        panel.setBounds(width-height,0,height,height);

        copyButton.setEnabled(false);
        saveButton.setEnabled(false);

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String str=textArea.getText();
                    if(str.equals("")){
                        textArea.setText("Pelease input string.");
                        return;
                    }
                    img=cQR.toImage(str,height);
                    if(img!=null){
                        copyButton.setEnabled(true);
                        saveButton.setEnabled(true);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                panel.repaint();
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetImage.setImage(img);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               chooser.showDialog(new JPanel(),"Save the QR code");
                File file=chooser.getSelectedFile();
                try{
                    textArea.setText("The path:"+file.getPath());
                    ImageIO.write((BufferedImage)img,"png",file);
                    file.renameTo(new File(file.getPath()+".png"));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    public class MyPanel extends JPanel {
        public void paint(Graphics g) {
            super.paint(g);
            if(img!=null)
                g.drawImage(img,0,0,height,height,null);
        }
    }
}
