package org.jenkinsci.plugins.dirdigger;

import java.io.File;

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
}
