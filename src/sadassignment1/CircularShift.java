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

    public CircularShift(Pipe input, Pipe output)
    {
        super(input, output);
    }
    
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
                    String[] words = line.split("\\s+");
                    for(int i = 0; i < words.length; i++)
                    {
                        for(int j = i; j < words.length; j++)
                        {
                            for(int k = 0; k < words[j].length(); k++)
                                output.write(words[j].charAt(k));
                            output.write(' ');
                        }
                        for(int j = 0; j < i; j++)
                        {
                            for(int k = 0; k < words[j].length(); k++)
                                output.write(words[j].charAt(k));
                            output.write(' ');
                        }
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
