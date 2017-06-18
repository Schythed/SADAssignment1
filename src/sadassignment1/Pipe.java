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
    
    public Pipe() throws IOException
    {
        reader = new PipedReader();
        writer = new PipedWriter();
        writer.connect(reader);
    }
    
    public int read() throws IOException
    {
        return reader.read();
    }
    
    public void write(int c) throws IOException
    {
        writer.write(c);
    }
    
    public void closeReader() throws IOException
    {
        reader.close();
    }
    
    public void closeWriter() throws IOException
    {
        writer.flush();
        writer.close();
    }
    
    public void close() throws IOException
    {
        closeReader();
        closeWriter();
    }
}
