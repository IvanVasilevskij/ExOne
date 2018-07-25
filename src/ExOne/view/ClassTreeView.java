package ExOne.view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ClassTreeView extends FileExplorerFx {
    public ClassTreeView() {
    }

    private void getAllChildren(TreeItem<String> item, Path path) {
        String stringPath = path.toString();
        if (stringPath.contains("\\")) {
            String nameOfPackage = stringPath.substring(0, stringPath.indexOf("\\"));
            if (nameOfPackage.equals("D:") || nameOfPackage.equals("C:")) {
                item.getChildren().add(new TreeItem<>(nameOfPackage,
                        new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/thumb_Hard_Drive.png")))));
                item.setExpanded(true);
            } else {
                item.getChildren().add(new TreeItem<>(nameOfPackage,
                        new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/folderOpen.png")))));
                item.setExpanded(true);
            }
            getAllChildren(item.getChildren().get(0), Paths.get(stringPath.substring(stringPath.indexOf("\\") + 1)));
        } else {
            item.getChildren().add(new TreeItem<>(stringPath));
            item.setExpanded(true);
        }
    }

    public void TreeCreate(TreeView<String> treeview, Path path) {
        TreeItem<String> ss = new TreeItem<>("This PC",
                new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/pc.png"))));
        getAllChildren(ss,path);
        treeview.setRoot(ss);
    }

    public void CreateTreeView(TreeView<String> treeview) {
        TreeItem<String> ss = new TreeItem<>("This PC",
                new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/pc.png"))));
        treeview.setRoot(ss);
    }
}