package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        // TODO #1 template needs refining/loop - get the Job with the given ID and pass it into the view
        // job-detail.html displaying properly, still unclear as to where this is accessed - redirect from new job creation or direct URL entry

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        //error message not appearing on reload of new-job template//
        if (errors.hasErrors()) {
            model.addAttribute(new JobForm());
            model.addAttribute("errors", errors);
            return "new-job";
        }

        // Complete! //

        String n = jobForm.getName();
        Employer e = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location l = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType p = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency c = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

        Job newJob = new Job(n,e,l,p,c);

        jobData.add(newJob);
        int newId = newJob.getId();

        model.addAttribute("newId", newId);
        model.addAttribute("jobPull", newJob); //update name to newJob if needed//

        return "job-detail";

    }
}
