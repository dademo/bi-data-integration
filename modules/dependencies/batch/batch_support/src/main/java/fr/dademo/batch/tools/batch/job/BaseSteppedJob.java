/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import fr.dademo.batch.tools.batch.job.listeners.DefaultJobExecutionListener;
import fr.dademo.batch.tools.batch.job.listeners.DefaultStepExecutionListener;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@Getter(AccessLevel.PROTECTED)
public abstract class BaseSteppedJob implements JobProvider {

    private final JobBuilderFactory jobBuilderFactory;

    protected BaseSteppedJob(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    protected abstract List<Step> getJobSteps();

    @Nonnull
    protected List<StepExecutionListener> getStepExecutionListeners() {
        return Collections.singletonList(new DefaultStepExecutionListener());
    }

    @Nonnull
    protected List<JobExecutionListener> getJobExecutionListeners() {
        return Collections.emptyList();
    }

    private Stream<JobExecutionListener> getAllJobExecutionListeners() {
        return Stream.concat(
            Stream.of(new DefaultJobExecutionListener()),
            getJobExecutionListeners().stream()
        );
    }

    @Nonnull
    @Override
    public Job getJob() {

        final var jobName = getJobName();

        final var jobBuilder = jobBuilderFactory
            .get(jobName)
            .incrementer(new RunIdIncrementer())
            .preventRestart();

        final var jobSteps = getJobSteps();

        final var stepJobBuilder = jobBuilder
            .start(jobSteps.get(0));

        IntStream.range(1, jobSteps.size())
            .mapToObj(jobSteps::get)
            .forEach(stepJobBuilder::next);

        getAllJobExecutionListeners()
            .forEach(stepJobBuilder::listener);

        return stepJobBuilder.build();
    }
}