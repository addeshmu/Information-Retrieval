
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reducchi
        extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
            throws IOException, InterruptedException {
        String fin = "";
        Integer m = Integer.valueOf(0);
        for (Text t : values) {
            String n = t.toString();
            fin = fin + n;
            m = Integer.valueOf(m.intValue() + 1);
        }
        context.write(key, new Text(fin));
    }
}