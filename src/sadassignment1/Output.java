/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sadassignment1;

import java.io.IOException;
import javax.swing.JTextArea;

/**
 *
 * @author josh
 */
public class Output extends Filter
{
    
    JTextArea outputBox;
    
    public Output(Pipe input, JTextArea outputArea)
    {
        super(input, null);
        outputBox = outputArea;
    }

    @Override
    protected void transform()
    {
        try
        {
            int c = input.read();
            while(c != -1)
            {
                outputBox.append((char)(c) + "");
                c = input.read();
            }
        }
        catch(IOException exc)
        {
            exc.printStackTrace();
            System.err.println("KWIC Error: Broken pipe");
            System.exit(1);      
        }
    }
    
}
