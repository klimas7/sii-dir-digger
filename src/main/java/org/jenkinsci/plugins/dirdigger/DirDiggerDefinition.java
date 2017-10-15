package org.jenkinsci.plugins.dirdigger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.model.StringParameterValue;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class DirDiggerDefinition extends ParameterDefinition {
    private final String root;
    private final Integer depth;
    private TreeNode<String> fileTree;

    @DataBoundConstructor
    public DirDiggerDefinition(String name, String description, String root, Integer depth) {
        super(name, description);
        this.root = root;
        this.depth = depth;
        this.fileTree = new TreeNode<>(root);
    }

    public String getRoot() {
        return root;
    }

    public Integer getDepth() {
        return depth;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        StringBuilder strValue = new StringBuilder();
        strValue.append(root).append(File.separator);
        Object value = jo.get("value");
        if (value instanceof String) {
            strValue.append(value).append(File.separator);
        } else if (value instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) value;
            for (Object element : JSONArray.toCollection(jsonArray, String.class) ) {
                strValue.append(element).append(File.separator);
            }
        }
        strValue.setLength(strValue.length() -1 );
        DirDiggerValue dirDiggerValue = new DirDiggerValue(jo.getString("name"), strValue.toString());
        return dirDiggerValue;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req) {
        return null;
    }

    public List<String> getFiles(Integer level) {
        FileTreeBuilder.build(fileTree, depth);
        List<String> files = new ArrayList<>();
        files.add("test_A_" + level);
        files.add("test_B_" + level);
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
