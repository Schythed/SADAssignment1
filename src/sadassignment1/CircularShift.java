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
public class CircularShift extends Filter
{   

    /**
    * Default constructor.
    * Passes the parameters directly to the super class's constructor.
    * 
    * @param input Input pipe
    * @param output Output pipe
    */
    public CircularShift(Pipe input, Pipe output)
    {
        super(input, output);
    }
    
    /**
    * CircularShift Filter Processing.
    * For each line passed, a circular shift is performed
    * by repeatedly removing the first word and appending it at the end of the line.
    * These lines are then written to the output.
    */
    @Override
    public void transform()
    { 
        try
        {
            int c = input.read();
            
            String line = new String();
            
            while(c != -1)
            {
                if(((char) c) == '\n')
                {
                    String result = new String();
                    String[] words = line.split("\\s+"); //Split each line by spaces
                    for(int i = 0; i < words.length; i++)
                    {
                        //Writes each word starting at a different spot
                        for(int j = i; j < words.length; j++)
                        {
                            for(int k = 0; k < words[j].length(); k++)
                                output.write(words[j].charAt(k));
                            output.write(' ');
                        }
                        //Writes the rest of the words from the beginning of the line
                        for(int j = 0; j < i; j++)
                        {
                            for(int k = 0; k < words[j].length(); k++)
                                output.write(words[j].charAt(k));
                            output.write(' ');
                        }
                        //End each line with a new line character
                        output.write('\n');
                    }
                    line = new String();
                }
                else
                    line += (char)(c);
                c = input.read();
            }
            output.closeWriter();
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.err.println("KWIC Error: Could not make circular shifts.");
            System.exit(1);
        }
    }
}
