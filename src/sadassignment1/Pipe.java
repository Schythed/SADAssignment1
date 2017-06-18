/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sadassignment1;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 *
 * @author josh
 */
public class Pipe
{
    private final PipedReader reader;
    private final PipedWriter writer;
    
    /**
    * Default constructor.
    * Creates a new PipedReader and a new PipedWriter for data flow.
    * Connects the newly created PipedReader and PipedWriter.
    */
    public Pipe() throws IOException
    {
        reader = new PipedReader();
        writer = new PipedWriter();
        writer.connect(reader);
    }
    
    /**
    * Read from the PipedReader.
    * Reads the next character in the pipe.
    * 
    * @return value as an int for the next character in the pipe.
    * @return -1 if there is nothing in the pipe
    * @throws IOException
    */
    public int read() throws IOException
    {
        return reader.read();
    }
    
    /**
    * Write to the PipedWriter.
    * Writes a character to send down the pipe.
    * 
    * @param c int value for data to be written
    * @throws IOException
    */
    public void write(int c) throws IOException
    {
        writer.write(c);
    }
    
    /**
    * Closes the PipedReader.
    * 
    * @throws IOException
    */
    public void closeReader() throws IOException
    {
        reader.close();
    }
    
    /**
    * Closes the PipedWriter.
    * 
    * @throws IOException
    */
    public void closeWriter() throws IOException
    {
        writer.flush();
        writer.close();
    }
    
    /**
    * Closes the PipedWriter and PipedWriter.
    * 
    * @throws IOException
    */
    public void close() throws IOException
    {
        closeReader();
        closeWriter();
    }
}
