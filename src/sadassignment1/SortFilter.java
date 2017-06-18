/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sadassignment1;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author josh
 */
public class SortFilter extends Filter
{   

    /**
    * Default constructor.
    * Passes the parameters directly to the super class's constructor.
    * 
    * @param input Input pipe
    * @param output Output pipe
    */
    public SortFilter(Pipe input, Pipe output)
    {
        super(input, output);
    }

    /**
    * Sort Filter Processing.
    * Each line read is sorted using a heap sort.
    * The sort uses a special kind of comparison.
    * 
    * @see #compare(java.lang.String, java.lang.String)
    */
    @Override
    public void transform()
    {
        try
        {
            int c = input.read();
            
            ArrayList<String> lines = new ArrayList(); //Arraylist for each line to sort
            String line = new String();
            
            while(c != -1)
            {
                line += (char)(c);
                if((char) c == '\n')
                {
                    lines.add(line);
                    line = new String();
                }
                c = input.read();
            }
            
            sort(lines);  //Sorts all of the lines
            
            //Send the sorted lines down the pipe
            for(String l : lines)
                for(char ch : l.toCharArray())
                    output.write(ch);
            
            output.closeWriter();
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.err.println("KWIC Error: Could not make circular shifts.");
            System.exit(1);
        }
    }
    
    /**
    * Heapsort for ArrayLists.
    * Sorts the Arraylist using a heapsort.
    * Utilizes the siftDown method.
    * 
    * @param lines the lines of strings to be sorted
    * @see #siftDown(java.util.ArrayList, int, int)
    */
    private void sort(ArrayList lines)
    {
        int size = lines.size();

        for(int i = (size / 2 - 1); i >= 0; i--)
            siftDown(lines, i, size);

        for(int i = (size - 1); i >= 1; i--)
        {
            Object tmp = lines.get(0);
            lines.set(0, lines.get(i));
            lines.set(i, tmp);
            siftDown(lines, 0, i);      
        }
    }
    
    /**
    * Sifting method for Arraylists.
    * Sorts the Arraylist using a heapsort.
    * Utilizes the compare method to compare strings.
    * 
    * @param lines the lines of strings to be sorted
    * @param root the start of the sift
    * @param bottom the bottom of the sift
    * @see #compare(java.lang.String, java.lang.String) 
    */
    private void siftDown(ArrayList lines, int root, int bottom)
    {    
        int max_child = root * 2 + 1;

        while(max_child < bottom)
        {
            if((max_child + 1) < bottom)
                if(compare(((String) lines.get(max_child + 1)), ((String) lines.get(max_child))) > 0)
                    max_child++;

            if(compare(((String) lines.get(root)), (String) lines.get(max_child)) < 0)
            {
                Object tmp = lines.get(root);
                lines.set(root, lines.get(max_child));
                lines.set(max_child, tmp);
                root = max_child;
                max_child = root * 2 + 1;
            }else
                break;
        }    
    }
    
    /**
    * Custom compare for Strings.
    * Compares the strings in the following manner where “a<A<b<B< … <y<Y<z<Z”
    * 
    * @param lines the lines of strings to be sorted
    * @param root the start of the sift
    * @param bottom the bottom of the sift
    * @return -1 if s1 < s2
    * @return 1 if s1 > s2
    * @return 0 if s1 == s2
    */
    private int compare(String s1, String s2)
    {
        int l1 = s1.length();
        int l2 = s2.length();
        int shortest = (l2 > l1) ? l1 : l2;
        for(int i = 0; i < shortest; i++)
        {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if(c1 != c2)
            {
                if(Character.toLowerCase(c1) == Character.toLowerCase(c2))
                {
                    if(c1 > c2)
                        return -1;
                    return 1;
                }
                if(Character.toLowerCase(c1) < Character.toLowerCase(c2))
                    return -1;
                return 1;
            }
        }
        
        if(l1 == l2)
            return 0;
        
        return (l1 < l2) ? l1 : l2;
    }
}
