package com.fitness.courses.global.utils.hls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fitness.courses.http.attachment.service.AttachmentServiceImpl;

@Component
public class HLSGenerator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(HLSGenerator.class);

    public void generateHLSPlaylist(Path videoPath, Path outputDir)
    {
        List<String> commands = new ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(videoPath.toString());
        commands.add("-hls_time 10");
        commands.add("-hls_list_size 0");
        commands.add("-hls_segment_filename");
        commands.add(outputDir.toString() + "/output%03d.ts ");
        commands.add(outputDir + "/output.m3u8");

        Process process = null;
        try
        {
            process = new ProcessBuilder()
                    .command(commands)
                    .directory(outputDir.toFile())
                    .start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Process finalProcess = process;
        new Thread(() ->
        {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(finalProcess.getInputStream())))
            {
                String line = null;
                while ((line = bufferedReader.readLine()) != null)
                {
                    LOG.info(line);
                }
            }
            catch (IOException e)
            {
            }
        }).start();

        Process finalProcess1 = process;
        new Thread(() ->
        {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(finalProcess1.getErrorStream())))
            {
                String line = null;
                while ((line = bufferedReader.readLine()) != null)
                {
                    LOG.info(line);
                }
            }
            catch (IOException e)
            {
            }
        }).start();

        try
        {
            if (process.waitFor() != 0)
            {
                throw new RuntimeException("Error while convert video to hls");
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }
}
