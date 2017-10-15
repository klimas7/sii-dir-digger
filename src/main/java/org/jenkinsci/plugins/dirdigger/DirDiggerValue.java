package org.jenkinsci.plugins.dirdigger;


import hudson.model.StringParameterValue;
import org.kohsuke.stapler.DataBoundConstructor;

public class DirDiggerValue extends StringParameterValue {

    @DataBoundConstructor
    public DirDiggerValue(String name, String value) {
        super(name, value);
    }
}
