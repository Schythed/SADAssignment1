/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sadassignment1;

/**
 *
 * @author josh
 */
public abstract class Filter implements Runnable
{
    Pipe input, output;
    
    private boolean started;
    
    //protected String result;
    //protected Filter nextFilter;
    
    public Filter(Pipe input, Pipe output)
    {
        this.input = input;
        this.output = output;
    }
    
    public void start()
    {
        if(!started)
        {
            started = true;
            Thread thread = new Thread(this);
            thread.start();
        }
    }
    
    public void stop()
    {
        started = false;
    }
    
    public void run()
    {
        transform();
    }
    
    abstract protected void transform();
}
