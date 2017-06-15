/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sadassignment1;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

class KWICWindow extends JFrame
{
    private JPanel programPanel, itemsPanel;
    private JTabbedPane tabbedPane;
    private JList searchList, resultsList;
    private JTextArea inputBox, outputBox;
    private JButton runButton, clearInputButton, clearOutputButton;
    private JButton switchButton;
    private JLabel imgLabel;
    private boolean optionBool;
    
    private BufferedImage option1, option2;
    
    private StringFilter shift, sorter, firstFilter;
    
    public KWICWindow()
    {   
        loadImages();
        
        setSize(400, 350);
        
        programPanel = new JPanel();
        programPanel.setLayout(new FlowLayout());
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Program", programPanel);
        tabbedPane.addTab("Filters", itemsPanel);
        
        add(tabbedPane);
        
        inputBox = new JTextArea(10, 16);
        JScrollPane inputScroll = new JScrollPane(inputBox);
        TitledBorder inputTitle= new TitledBorder("Input");
        inputScroll.setBorder(inputTitle);
        outputBox = new JTextArea(10, 16);
        outputBox.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputBox);
        TitledBorder outputTitle = new TitledBorder("Output");
        outputScroll.setBorder(outputTitle);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        
        
        runButton = new JButton("Run");
        runButton.addActionListener(new RunButtonActionListener());
        clearInputButton = new JButton("Clear Input");
        clearInputButton.addActionListener(new ClearInputButtonActionListener());
        clearOutputButton = new JButton("Clear Output");
        clearOutputButton.addActionListener(new ClearOutputButtonActionListener());
        
        bottomPanel.add(inputScroll);
        bottomPanel.add(outputScroll);
        programPanel.add(runButton);
        programPanel.add(clearInputButton);
        programPanel.add(clearOutputButton);
        
        programPanel.add(bottomPanel);
        
        imgLabel = new JLabel(new ImageIcon(option1));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        itemsPanel.add(imgLabel);
        
        switchButton = new JButton("Switch Filter Order");
        switchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchButton.addActionListener(new SwitchButtonActionListener());
        itemsPanel.add(switchButton);
        
        optionBool = true;
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        shift = new CircularShift();
        sorter = new SortFilter();
        shift.setNextFilter(sorter);
        firstFilter = shift;
    }

    private void loadImages()
    {
        try
        {                
          option1 = ImageIO.read(new File("src/images/Option1.png"));
          option2 = ImageIO.read(new File("src/images/Option2.png"));
       } catch (IOException ex)
       {
            String workingDir = System.getProperty("user.dir");
            System.out.println("Current working directory : " + workingDir);
            ex.printStackTrace();
       }
    }
    
    private class ClearInputButtonActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            inputBox.setText("");
        }
        
    }
    
    private class ClearOutputButtonActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            outputBox.setText("");
        }
        
    }
    
    private class RunButtonActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            outputBox.setText(firstFilter.processString(inputBox.getText()));
        }
        
    }
    
    private class SwitchButtonActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            optionBool = !optionBool;
            if(optionBool)
            {
                imgLabel.setIcon(new ImageIcon(option1));
                shift.setNextFilter(sorter);
                sorter.setNextFilter(null);
                firstFilter = shift;
            }
            else
            {
                imgLabel.setIcon(new ImageIcon(option2));
                sorter.setNextFilter(shift);
                shift.setNextFilter(null);
                firstFilter = sorter;
            }
        }
    }
}
