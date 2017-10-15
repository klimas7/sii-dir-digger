package org.jenkinsci.plugins.dirdigger;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class DirDiggerDefinition extends ParameterDefinition {
    private final String root;

    @DataBoundConstructor
    public DirDiggerDefinition(String name, String description, String root) {
        super(name, description);
        this.root = root;
    }

    public String getRoot() {
        return root;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        return null;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req) {
        return null;
    }

    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        @Override
        public String getDisplayName() {
            return "Dir Digger";
        }
    }
}
