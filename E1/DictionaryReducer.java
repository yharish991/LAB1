package Dictionary;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.*;

public class DictionaryReducer extends Reducer<Text,Text,Text,Text> {
   public void reduce(Text word, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
     
        String reduceOutputValue = "";
        Iterator<Text> ite = values.iterator();
        TreeMap<String,String> translation = new TreeMap<String,String>();
        while(ite.hasNext()){
                String val = ite.next().toString();
                if(val.indexOf(':')>0){
                        String lang = val.substring(0, val.indexOf(':'));
                                if(translation.get(lang)==null){
                                        translation.put(lang,val.substring(val.indexOf(':')+1));
                                }
                                else{

                                translation.put(lang,translation.get(lang)+","+val.substring(val.indexOf(':')+1));
                                }
                        }
        }
        Set<String> keys = translation.keySet();

        if(!keys.contains("german")){
                translation.put("german","N/A");
        }

        if(!keys.contains("italian")){
                translation.put("italian","N/A");
        }

        if(!keys.contains("spanish")){
                translation.put("spanish","N/A");
        }

        if(!keys.contains("portuguese")){
                translation.put("portuguese","N/A");
        }

        if(!keys.contains("french")){
                translation.put("french","N/A");
        }

        for(Map.Entry<String, String> entry :translation.entrySet()){
              reduceOutputValue = reduceOutputValue + entry.getKey()+":"+entry.getValue()+"|";
        }
        reduceOutputValue = reduceOutputValue.substring(0,reduceOutputValue.length()-1);

        context.write(word,new Text(reduceOutputValue));
   }
}
