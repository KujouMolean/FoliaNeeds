package com.molean;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ApplyAccessWidenerTask extends DefaultTask {

    @TaskAction
    public void remapAccessWidenerTask() throws IOException {
        File asFile = getProject().getLayout().getProjectDirectory().file("src/main/resources/ignite.mod.json").getAsFile();
        JsonObject jsonObject = new Gson().fromJson(Files.readString(asFile.toPath()), JsonObject.class);
        if (!jsonObject.has("wideners") || jsonObject.get("wideners").getAsJsonArray().isEmpty()) {
            return;
        }
        String awPath = jsonObject.get("wideners").getAsJsonArray().get(0).getAsString();
        ApplyAccessWidener applyAccessWidener = new ApplyAccessWidener(new File(asFile.getParent(), awPath));

        // 直接找到mappedServerJar的位置

        Project project = getProject();
        while (true) {
            Project parent = project.getParent();
            if (parent == null) {
                break;
            } else {
                project = parent;
            }
        }

        apply(project, applyAccessWidener);
    }

    public void apply(Project project, ApplyAccessWidener applyAccessWidener) {
        File mappedServerJar = new File(project.getProjectDir(), ".gradle/caches/paperweight/taskCache/mappedServerJar.jar");
        if (!mappedServerJar.exists()) {
            return;
        }
        applyAccessWidener.apply(mappedServerJar);

        for (Project subproject : project.getSubprojects()) {
            apply(subproject, applyAccessWidener);
        }

    }
}
