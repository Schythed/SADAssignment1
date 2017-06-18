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

    public SortFilter(Pipe input, Pipe output)
    {
        super(input, output);
    }
    
    @Override
    public void transform()
    {
        /*
        String result = new String();
        String[] strParts = result.split("\\r?\\n|\\r");
        int n = strParts.length;
        for (int i = 0; i < n; i++)
            for (int j = 1; j < (n - i); j++)
                if(compare(strParts[j - 1], strParts[j]) > 0)
                {
                    String temp = strParts[j - 1];
                    strParts[j - 1] = strParts[j];
                    strParts[j] = temp;
                }
        
        for(String st : strParts)
            result += st + '\n';
        */
        try
        {
            int c = input.read();
            
            ArrayList<String> lines = new ArrayList();
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
            
            sort(lines);
            
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
