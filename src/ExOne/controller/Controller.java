package ExOne.controller;

import ExOne.view.ClassTreeView;
import ExOne.view.FileExplorerFx;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML private TextArea area;
    @FXML private TextArea areaForExpression;

    @FXML private TreeView<String> treeview;

    @FXML private Label count;
    @FXML private Label currentCount;
    @FXML private Label countEntrance;
    @FXML private Label currentEntrance;
    @FXML private Label nameFoundFile;

    @FXML private TextField path;
    @FXML private TextField fieldForType;

    private static ClassTreeView Fx1;
    private List<File> listOfAllFile;
    private List<Integer> allIndexes;
    private int currentStep;
    private int currentStepInFilesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Fx1 = new ClassTreeView();
        Fx1.CreateTreeView(treeview);
    }

    @FXML
    public void foundFilesWithExpression() throws IOException {
        Platform.runLater(() ->  {
        List<File> fileWithFormat = FileExplorerFx.allFileWithFormat(new ArrayList<>(), path.getText(), fieldForType.getText());
        listOfAllFile = Fx1.allFilesWithExpression(fileWithFormat, areaForExpression.getText(), new ArrayList<>());
        currentStepInFilesList = 0;
        area.clear();
        count.setText(String.valueOf(listOfAllFile.size()));
        currentCount.setText("");
        countEntrance.setText("");
        currentEntrance.setText("");
        nameFoundFile.setText("");
    });
        }

    @FXML
    private void nextFileWithExpression() {
        try {
            String textForArea = Fx1.readFromFileAndSetOnArea(listOfAllFile.get(currentStepInFilesList));
        String expression = areaForExpression.getText();
        allIndexes = Fx1.countOfExpression(new ArrayList<>(), textForArea, expression);
        area.setText(textForArea);
        currentCount.setText(currentStepInFilesList+1 + "/" + count.getText());

        Fx1.TreeCreate(treeview, Paths.get(listOfAllFile.get(currentStepInFilesList).getPath()));

        currentStep = 0;

        countEntrance.setText(String.valueOf(allIndexes.size()));
        currentEntrance.setText("");
        nameFoundFile.setText(listOfAllFile.get(currentStepInFilesList).getName());
        currentStepInFilesList++;
        } catch (Exception ignored) {}
    }

    @FXML
    public void setFocusOnNextExpression() {
        try {
            area.positionCaret(allIndexes.get(currentStep) + areaForExpression.getText().length());
            area.selectRange(allIndexes.get(currentStep), allIndexes.get(currentStep) + areaForExpression.getText().length());
            currentStep++;
            currentEntrance.setText(String.valueOf(currentStep) + "/" + allIndexes.size());
        } catch (RuntimeException e) {
            currentStep = 0;
        }
    }

    @FXML
    public void setFocusOnPrevExpression() {
        try {
            currentStep = currentStep - 1;
            area.positionCaret(allIndexes.get(currentStep-1) + areaForExpression.getText().length());
            area.selectRange(allIndexes.get(currentStep-1), allIndexes.get(currentStep-1) + areaForExpression.getText().length());
            currentEntrance.setText(String.valueOf(currentStep) + "/" + allIndexes.size());
        } catch (RuntimeException e) {
            currentStep = allIndexes.size()+1;
        }
    }

    @FXML
    private void handleMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            try {
                TreeItem<String> item = treeview.getSelectionModel().getSelectedItem();
            } catch (Exception ignored) {}
        }
    }
}
