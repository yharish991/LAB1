
package Dictionary;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class DictionaryMapper  extends Mapper<Text, Text, Text, Text> {
        String language="";

      public void setup(Context context) {
        FileSplit fileSplit = (FileSplit)context.getInputSplit();
        language  = fileSplit.getPath().getName().replace(".txt","");
      }

      public void map(Text key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        int length = line.length();
        if((line.indexOf("]")==length-1) && (line.indexOf("[") > 0)){
            String pOfs = line.substring(line.indexOf("[")+1,line.indexOf("]"));
            String subLine = line.substring(0, line.indexOf("["));
            StringTokenizer parser = new StringTokenizer(subLine,"\t");
            String mapOutputKey = key.toString()+":"+" "+"["+pOfs+"]";
            String mapOutputValue = "";
                while(parser.hasMoreTokens()){
                      mapOutputValue = mapOutputValue+parser.nextToken();
                }
            mapOutputValue = language+":"+mapOutputValue;
            context.write(new Text(mapOutputKey), new Text(mapOutputValue));
    }
      }
}
