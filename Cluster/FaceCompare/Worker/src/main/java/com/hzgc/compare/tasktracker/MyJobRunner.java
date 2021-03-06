package com.hzgc.compare.tasktracker;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;

public class MyJobRunner  implements JobRunner{
//    private static final Logger logger = LoggerFactory.getLogger(MyJobRunner.class);
    private static Logger log = Logger.getLogger(MyJobRunner.class);

    public Result run(JobContext jobContext) throws Throwable {
        Job job = jobContext.getJob();
        String workId = job.getParam("workerId");
        String port = job.getParam("port");
        String nodeGroup = jobContext.getJob().getTaskTrackerNodeGroup();
        Map<Job, Long> jobs = JobManager.getInstance().getJobs();
        for(Map.Entry<Job, Long> entry : jobs.entrySet()){
            if(entry.getKey().getTaskId().equals(job.getTaskId())){
                Long time = System.currentTimeMillis() - entry.getValue();
                if(time < 1000L * 60 * 2){
                    log.info("This job is run not long ago.");
                    return null;
                }
            }
        }
        ProcessBuilder builder = new ProcessBuilder();

        String jarPath = MyJobRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File temp = new File(jarPath);
        String parentPath = temp.getParentFile().getParentFile().getPath();
        log.info("---------------------------------start a worker---------------------------------");
        log.info("sh " + parentPath + "/bin/start-worker.sh " + workId + " " + nodeGroup + " " + port + " " + job.getTaskId());
        builder.command("sh", parentPath + "/bin/start-worker.sh", workId, nodeGroup, port, job.getTaskId());
        builder.start();
        log.info("--------------------------------------------------------------------------------");
        jobs.put(job, System.currentTimeMillis());
        return null;
    }

    public static void main(String args[]){
//        String pathTemp = MyJobRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        System.out.println(pathTemp);
    }
}
