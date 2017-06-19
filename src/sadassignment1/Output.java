package sadassignment1;

import java.io.IOException;
import javax.swing.JTextArea;

/**
 *
 * @author josh
 */
public class Output extends Filter
{
    
    private final JTextArea outputBox;
    
    /**
    * Default constructor.
    * Passes the parameters directly to the super class's constructor.
    * 
    * @param input Input Pipe
    * @param outputArea Visible Output Area
    */
    public Output(Pipe input, JTextArea outputArea)
    {
        super(input, null);
        outputBox = outputArea;
    }

    /**
    * Data Sink.
    * Reads data and writes it to the outputBox.
    */

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
