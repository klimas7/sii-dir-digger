package org.jenkinsci.plugins.dirdigger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileTreeBuilder {
    public synchronized static void build(TreeNode<String> fileTree, Integer depth) {
        if (isTreeInitialize(fileTree)) {
            return;
        }
        File root = new File(fileTree.getData());
        deeper(root, fileTree, depth);
    }

    private static void deeper(File root, TreeNode rootTree, int depth) {
        if (depth == 0) {
            return;
        }
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                TreeNode child = rootTree.addChild(file.getName());
                deeper(file, child, depth -1);
            } else {
                rootTree.addChild(file.getName());
            }
        }
    }


    private static boolean isTreeInitialize(TreeNode<String> fileTree)
    {
        return fileTree.getChildren() != null && fileTree.getChildren().size() > 0;
    }

    public static List<String> getFileFromLevel(TreeNode<String> fileTree, Integer level) {
        List<TreeNode<String>> children = getChildren(fileTree, level);
        List<String> files = new ArrayList<>(children.size());
        for (TreeNode<String> node : children) {
            files.add(node.data);
        }
        return files;
    }

    private static List<TreeNode<String>> getChildren(TreeNode<String> fileTree, Integer level) {
        if (level == 1) {
            return fileTree.getChildren();
        }
        else if (fileTree.getChildren().size() > 0) {
            return getChildren(fileTree.getChildren().get(0), level - 1);
        }
        else {
            return Collections.emptyList();
        }
    }
}
