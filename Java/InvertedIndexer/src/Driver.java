import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver
        extends Configured
        implements Tool
{
    public static void main(String[] args)
            throws Exception
    {
        System.exit(ToolRunner.run(new Driver(), args));
    }

    public int run(String[] strings)
            throws Exception
    {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "InvertedIndexer");

        job.setJarByClass(Driver.class);
        job.setMapperClass(Mappy.class);
        job.setCombinerClass(Combiner.class);
        job.setReducerClass(Reducchi.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        Path outputPath = new Path(strings[1]);
        FileInputFormat.addInputPath(job, new Path(strings[0]));
        FileOutputFormat.setOutputPath(job, outputPath);

        outputPath.getFileSystem(conf).delete(outputPath, true);

        return job.waitForCompletion(true) ? 0 : 1;
    }
}
