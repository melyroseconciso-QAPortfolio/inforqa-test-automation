package com.inforqa.core;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

/**
 * Records the computer screen while a scenario runs (optional, off by default).
 * Videos are saved in target/videos as .avi files.
 * It records the real screen, so it only works when the browser is visible
 * (-Dheadless=false) and only makes sense with one test at a time.
 */
public final class VideoRecorder {

    private static final ThreadLocal<ScreenRecorder> RECORDER = new ThreadLocal<>();
    private static final Path VIDEO_FOLDER = Path.of("target", "videos");

    private VideoRecorder() {
    }

    public static void start() {
        if (!Config.record()) {
            return;
        }
        if (Config.headless() || GraphicsEnvironment.isHeadless()) {
            System.out.println("Video recording skipped: run with -Dheadless=false to record.");
            return;
        }
        try {
            Files.createDirectories(VIDEO_FOLDER);
            GraphicsConfiguration screen = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration();

            ScreenRecorder recorder = new ScreenRecorder(
                screen,
                screen.getBounds(),
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO,
                    EncodingKey, ENCODING_AVI_MJPG,
                    CompressorNameKey, ENCODING_AVI_MJPG,
                    DepthKey, 24,
                    FrameRateKey, Rational.valueOf(15),
                    QualityKey, 1.0f,
                    KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO,
                    EncodingKey, "black",
                    FrameRateKey, Rational.valueOf(30)),
                null,
                VIDEO_FOLDER.toFile());
            recorder.start();
            RECORDER.set(recorder);
        } catch (Exception e) {
            System.out.println("Could not start video recording: " + e.getMessage());
        }
    }

    /** Stops recording and renames the video after the scenario. */
    public static void stop(String videoName) {
        ScreenRecorder recorder = RECORDER.get();
        if (recorder == null) {
            return;
        }
        try {
            recorder.stop();
            List<File> files = recorder.getCreatedMovieFiles();
            if (!files.isEmpty()) {
                Path target = VIDEO_FOLDER.resolve(videoName.replaceAll("[^a-zA-Z0-9_-]", "_") + ".avi");
                Files.move(files.get(0).toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Video saved: " + target);
            }
        } catch (Exception e) {
            System.out.println("Could not save video: " + e.getMessage());
        } finally {
            RECORDER.remove();
        }
    }
}
