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
public class SortFilter extends StringFilter
{   
    @Override
    public String processString(String s)
    {
        result = new String();
        String[] strParts = s.split("\\r?\\n|\\r");
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
        
        return super.processString(result);
    }
    
    public int compare(String s1, String s2)
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
