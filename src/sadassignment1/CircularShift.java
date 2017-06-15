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
public class CircularShift extends StringFilter
{   
    @Override
    public String processString(String s)
    {
        result = new String();
        String[] strParts = s.split("\\r?\\n|\\r");
        for(String st : strParts)
        {
            String[] strParts2 = st.split("\\s+");
            for(int i = 0; i < strParts2.length; i++)
            {
                for(int j = i; j < strParts2.length; j++)
                    result += strParts2[j] + ' ';
                for(int j = 0; j < i; j++)
                    result += strParts2[j] + ' ';
                result += '\n';
            }
        }
        
        return super.processString(result);
    }
}
