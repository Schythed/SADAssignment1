package sadassignment1;

/**
 *
 * @author josh
 */
public abstract class Filter implements Runnable
{
    Pipe input, output;
    
    private boolean started;
    
    /**
    * Default constructor.
    * @param input Input pipe
    * @param output Output pipe
    */
    public Filter(Pipe input, Pipe output)
    {
        this.input = input;
        this.output = output;
    }
    
    /**
    * Starts the filter processing.
    * Changes the started boolean to true and starts the thread.
    * Does not do anything if the thread is already started.
    */
    public void start()
    {
        if(!started)
        {
            started = true;
            Thread thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
    * Stops the filter processing.
    * Changes the started boolean to false.
    */
    public void stop()
    {
        started = false;
    }
    
    
    /**
    * Runnable Interface.
    * Calls the transform method which is to be overwritten by child classes.
    */
    @Override
    public void run()
    {
        transform();
    }
    
    /**
    * Filter Processing.
    * Called automatically by the run method from the Runnable interface.
    * Responsibility is designated to the child classes on how to transform the data.
    * Data should be read from the input Pipe.
    * Data should be written to the output Pipe.
    * Only one character can be read at a time at a time.
    * Only one character can be written at a time at a time.
    */
    abstract protected void transform();
}
