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
    private int index = 0;

    public BuildingScreen(
            BuildingViewModel buildingViewModel,
            TranslationManager translationManager) {
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
        List<Panel> buildingsPanels = new ArrayList<>();
        List<Building> buildings = new ArrayList<>(buildingViewModel.getBuildings());

        for (Building building : buildings) {
            Panel buildingPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            Label title = new Label(translationManager.getString(building.getTitleId()));
            Label descripcion = new Label(translationManager.getString(building.getDescripcionId()));
            Label level = new Label(translationManager.getString("level", building.getLevel()));
            Label cost = new Label(translationManager.getString("cost", Utilities.formatNum(building.getCost())));
            Label production = new Label(translationManager.getString("gain", Utilities.formatNum(building.getBaseProduction())));

            Button buy = new Button(translationManager.getString("buy"), () -> {
                if (buildingViewModel.buyBuilding(building)) {
                    level.setText(translationManager.getString("level", building.getLevel()));
                    cost.setText(translationManager.getString("cost", Utilities.formatNum(building.getCost())));
                }
            });
            buildingPanel.addComponent(title);
            buildingPanel.addComponent(descripcion);
            buildingPanel.addComponent(level);
            buildingPanel.addComponent(cost);
            buildingPanel.addComponent(production);
            buildingPanel.addComponent(buy);
            buildingsPanels.add(buildingPanel);

            translationManager.addListener(() -> {
                title.setText(translationManager.getString(building.getTitleId()));
                descripcion.setText(translationManager.getString(building.getDescripcionId()));
                buy.setLabel(translationManager.getString("buy"));
            });
        }

        buildingsPanel.addComponent(buildingsPanels.get(index));
        buildingsPanel.addComponent(buildingsPanels.get(index + 1));

        Button up = new Button("\u25B2", () -> {
            buildingsPanel.removeAllComponents();
            if ((index - 2) >= 0) { // 0 1 2 -> 0+2 > 3?
                index -= 2;
                buildingsPanel.addComponent(buildingsPanels.get(index));
                if ((index + 1) < buildingsPanels.size())
                    buildingsPanel.addComponent(buildingsPanels.get(index + 1));

            } else {
                index = buildingsPanels.size()-1; // 0 1 2 3 4 5 6 // 1 2 3 4 5 6 7
                index -= (index % 2 == 0) ? 0 : 1;
                buildingsPanel.addComponent(buildingsPanels.get(index));
                if ((index + 1) < buildingsPanels.size())
                    buildingsPanel.addComponent(buildingsPanels.get(index + 1));

            }
        });
        Button down = new Button("\u25BC", () -> {
            buildingsPanel.removeAllComponents();
            if ((index + 2) < buildingsPanels.size()) { // 0 1 2 -> 0+2 > 3?
                index += 2;
                buildingsPanel.addComponent(buildingsPanels.get(index));
                if ((index + 1) < buildingsPanels.size())
                    buildingsPanel.addComponent(buildingsPanels.get(index + 1));

            } else {
                index = 0;
                buildingsPanel.addComponent(buildingsPanels.get(index));
                if ((index + 1) < buildingsPanels.size())
                    buildingsPanel.addComponent(buildingsPanels.get(index + 1));

            }
        });

        root.addComponent(up);
        root.addComponent(buildingsPanel);
        root.addComponent(down);

        panel.addComponent(root);
    }
}