package org.jenkinsci.plugins.dirdigger;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> getFiles() {
        Map<String, String> files = new HashMap<>();
        files.put("/opt/test_1", "test_1");
        files.put("/opt/test_2", "test_2");
        return files;
    }


    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        @Override
        public String getDisplayName() {
            return "Dir Digger";
        }
    }
}
