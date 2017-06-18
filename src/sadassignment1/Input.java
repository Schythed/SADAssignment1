/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sadassignment1;

import java.io.IOException;

/**
 *
 * @author josh
 */
public class Input extends Filter
{

    private final String inputString;   
    
    /**
    * Default constructor.
    * Passes the parameters directly to the super class's constructor.
    * 
    * @param in Input String to be processed
    * @param output Output pipe
    */
    public Input(String in, Pipe output)
    {
        super(null, output);
        inputString = in;
    }
    
    /**
    * Data Pump.
    * Processes the inputString and writes to the output Pipe.
    */
    @Override
    protected void transform()
    {
        try
        {
            boolean new_line = false;       //Detect the beginning of a new line
            boolean new_word = false;       //Detect the beginning of a new word
            boolean line_started = false;   //So we don't write an extra space at the beginning of a new line

            for(int i = 0; i < inputString.length(); i++)
            {
                char c = inputString.charAt(i);
                switch(c)
                {
                case '\n':          
                    new_line = true;
                    break;
                case ' ':
                    new_word = true;
                    break;
                case '\t':
                    new_word = true;
                    break;
                case '\r':
                    break;
                default:
                    if(new_line)
                    {
                        output.write('\n');
                        new_line = false;
                        line_started = false;
                    }
                    if(new_word)
                    {
                        if(line_started)
                            output.write(' ');
                        new_word = false;
                    }
                    output.write(c);
                    line_started = true;
                    break;
                }      
            }

        output.write('\n');
        output.closeWriter();
        }
        catch(IOException exc)
        {
            exc.printStackTrace();
            System.err.println("KWIC Error: Could not read the input file.");
            System.exit(1);
        }
    } 
}
