package org.jenkinsci.plugins.mib3flash;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Map;
import java.util.TreeMap;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.model.StringParameterValue;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class Mib3Flash extends ParameterDefinition {
    private String login = null;
    private String hostname = null;
    private String command = null;

    @DataBoundConstructor
    public Mib3Flash(String name, String description, String login, String hostname, String command) {
        super(name, description);

        this.login = login;
        this.hostname = hostname;
        this.command = command;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        StringParameterValue value = req.bindJSON(StringParameterValue.class, jo);
        value.setDescription(getDescription());
        return value;
    }

    public String getLogin() {
        return login;
    }

    public String getHostname() {
        return hostname;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req) {
        return null;
    }

    public Map<String, String> getFiles() {

        Map<String, String> files = new TreeMap<>();

        try {
            // Build ssh command to run
            String ssh_command = "ssh " + login + "@" + hostname + " \"" + command + "\"";
            System.out.println("Mib3FlashBoard: run command: " + ssh_command);

            // Run command in shell subprocess
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("sh", "-c", ssh_command);
            Process ssh = builder.start();

            // Read output
            StringBuilder out = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(ssh.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] key_value = line.split(":");
                String key = key_value[0];
                String value = key_value[1];
                files.put(value, key);
            }

            // Check result
            int exitcode;
            if ((exitcode = ssh.waitFor()) == 0) {
                System.out.println("Mib3FlashBoard: run command success");
            }
            else {
                System.out.println("Mib3FlashBoard: run command failure: " + exitcode);
            }

        // Handle possible exceptions
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return files;
    }


    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        @Override
        public String getDisplayName() {
            return "Mib3 board flash";
        }
    }
}
