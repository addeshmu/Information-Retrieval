import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class Mappy
        extends Mapper<LongWritable, Text, Text, Text>
{
    public void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
            throws IOException, InterruptedException
    {
        FileSplit myfilesplit = (FileSplit)context.getInputSplit();
        String fname = myfilesplit.getPath().getName();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        //ArrayList<String> WordIndex = new ArrayList<String>(Arrays.asList("magnifiers","livelihood","thrilling","fuel","warning","mars","sawyer","guaranty"));

        while ((fname.charAt(i) != '.') && (fname.charAt(i) != 0))
        {
            sb.append(fname.charAt(i));
            i++;
        }
        Text docId = new Text(sb.toString());
        String singleLine = value.toString();
        StringTokenizer st = new StringTokenizer(singleLine);
        while (st.hasMoreTokens())
        {
            String singleToken = st.nextToken();
            if (singleToken!= "" /*&& WordIndex.contains(singleToken)*/) {
                context.write(new Text(singleToken), new Text(singleToken + "~" + docId.toString()));
            }
        }
    }
}
