import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Combiner
        extends Reducer<Text, Text, Text, Text>
{
    public void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
            throws IOException, InterruptedException
    {
        String fin = "";
        Integer m = Integer.valueOf(0);
        String file = "";
        for (Text t : values)
        {
            String n = t.toString();
            file = t.toString().split("~")[1];
            fin = fin + n;
            m = Integer.valueOf(m.intValue() + 1);
        }
        context.write(key, new Text(file + ":" + m.toString() + " "));
    }
}
