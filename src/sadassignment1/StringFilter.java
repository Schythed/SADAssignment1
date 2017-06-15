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
public abstract class StringFilter
{
    protected String result;
    protected StringFilter nextFilter;
    
    public String processString(String s)
    {
        if(nextFilter != null)
            return nextFilter.processString(s);
        
        return result;
    }
    
    public String outputString()
    {
        return result;
    }
    
    public void setNextFilter(StringFilter next)
    {
        nextFilter = next;
    }
}
