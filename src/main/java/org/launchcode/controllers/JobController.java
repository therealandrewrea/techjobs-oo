package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {
        String title = "test";

        Job jobPull = jobData.findById(id);
        model.addAttribute("jobPull", jobPull);

        // TODO #1 STILL NEED - get the Job with the given ID and pass it into the view
        // job-detail.html displaying properly, still unclear as to where this is accessed - redirect from new job creation or direct URL entry

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        //error message not appearing on reload of new-job template//
        if (errors.hasErrors()) {
            model.addAttribute(new JobForm());
            return "new-job";
        }
        // TODO #7- ask about todo 6, how to use jobform to create a new job and add to jobData, then redirect to job-detail with the ID //
//        model.addAttribute("name", name);
//        model.addAttribute("employer", employer.value);
//        model.addAttribute("location", location.value);
//        model.addAttribute("coreCompetency", coreCompetency.value);
//        model.addAttribute("positionType", positionType.value);

        Job newJob = new Job();
        jobData.add(newJob);
        // TODO #6 - STILL NEED - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        // annotations - in prep work - annotate individual fields in models
        // errors object directly after thing being validated in controller - and validate annotation
        // re-render form and provide error message as needed
        // build in a place for error messages to be displayed - prep work - span tags

        return "job-detail";

    }
}
