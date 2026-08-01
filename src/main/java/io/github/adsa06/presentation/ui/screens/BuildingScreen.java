package io.github.adsa06.presentation.ui.screens;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

import io.github.adsa06.domain.model.Building;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.presentation.viewmodel.BuildingViewModel;
import io.github.adsa06.utilities.Utilities;

public class BuildingScreen {
    private Panel panel;
    private BuildingViewModel buildingViewModel;
    private TranslationManager translationManager;

    public BuildingScreen(
        BuildingViewModel buildingViewModel,
        TranslationManager translationManager
    ) {
        this.buildingViewModel = buildingViewModel;
        this.translationManager = translationManager;

        initialize();
    }

    public Panel getPanel() {
        return panel;
    }

    private void initialize() {
        panel = new Panel();

        Panel root = new Panel(new LinearLayout(Direction.VERTICAL));

        Panel buildingsPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        List<Panel> buildingstPanels = new ArrayList<>();
        List<Building> buildings = new ArrayList<>(buildingViewModel.getBuildings());

        for (Building building : buildings) {
            Panel buildingPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            Label title = new Label(translationManager.getString(building.getTitleId()));
            Label descripcion = new Label(translationManager.getString(building.getDescripcionId()));
            Label level = new Label("x" + building.getLevel());
            Label cost = new Label(Utilities.formatNum(building.getCost()) + " pts");
            Label production = new Label(Utilities.formatNum(building.getBaseProduction()) + "/s");

            Button buy = new Button(translationManager.getString("buy"), () -> {
                if(buildingViewModel.buyBuilding(building)) {
                    level.setText("x" + building.getLevel());
                    cost.setText(Utilities.formatNum(building.getCost()) + " pts");
                }
            });
            buildingPanel.addComponent(title);
            buildingPanel.addComponent(descripcion);
            buildingPanel.addComponent(level);
            buildingPanel.addComponent(cost);
            buildingPanel.addComponent(production);
            buildingPanel.addComponent(buy);
            buildingstPanels.add(buildingPanel);

            translationManager.addListener(() -> {
                title.setText(translationManager.getString(building.getTitleId()));
                descripcion.setText(translationManager.getString(building.getDescripcionId()));
                buy.setLabel(translationManager.getString("buy"));
            });
        }

        buildingsPanel.addComponent(buildingstPanels.get(0));
        buildingsPanel.addComponent(buildingstPanels.get(1));

        Button up = new Button("\u25B2");
        Button down = new Button("\u25BC");

        root.addComponent(up);
        root.addComponent(buildingsPanel);
        root.addComponent(down);
        
        panel.addComponent(root);
    }
}