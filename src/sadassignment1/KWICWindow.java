/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sadassignment1;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

class KWICWindow extends JFrame
{
    private final JTextArea inputBox, outputBox;
    private final JButton runButton, clearInputButton, clearOutputButton;
    
    Pipe inputToShift, shiftToSorter, sorterToOutput;
    Filter input, shift, sort, output;
    
    public KWICWindow()
    {   
        JPanel programPanel = new JPanel();
        programPanel.setLayout(new FlowLayout());
        
        
        add(programPanel);
        
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
            outputBox.setText("");
            try
            {
                inputToShift = new Pipe();
                shiftToSorter = new Pipe();
                sorterToOutput = new Pipe();
                
                input = new Input(inputBox.getText(), inputToShift);
                shift = new CircularShift(inputToShift, shiftToSorter);
                sort = new SortFilter(shiftToSorter, sorterToOutput);
                output = new Output(sorterToOutput, outputBox);
                
                input.start();
                shift.start();
                sort.start();
                output.start();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }  
    }
    
    @Override
    public void processWindowEvent(WindowEvent e)
    {
        super.processWindowEvent(e);
        if(e.getID() == WindowEvent.WINDOW_CLOSING)
        {

            if(input != null)
                input.stop();
            if(shift != null)
                shift.stop();
            if(sort != null)
                sort.stop();
            if(output != null)
                output.stop();
            
            try
            {
                if(inputToShift != null)
                    inputToShift.close();
                if(shiftToSorter != null)
                    shiftToSorter.close();
                if(sorterToOutput != null)
                    sorterToOutput.close();
            }
            catch(IOException ex)
            {

            }
        }
    }
    
    public static void main(String[] args)
    {
        KWICWindow f = new KWICWindow();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(400, 350);
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("KWIC Sorter");
        f.setVisible(true);
    }
}
